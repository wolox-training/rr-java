package wolox.training.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.factories.BookFactory;
import wolox.training.factories.UserFactory;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private final UserFactory userFactory = new UserFactory();
    private final BookFactory bookFactory = new BookFactory();

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BookRepository bookRepository;

    @Test
    public void givenUsers_whenListingAllUsers_thenAllUsersAreReturned() throws Exception {
        int count = userFactory.faker().number().numberBetween(3, 5);
        List<User> users = userFactory.buildList(count);
        String json = objectMapper.writeValueAsString(users);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        mvc.perform(get("/api/users").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(count)))
            .andExpect(content().json(json))
        ;
    }

    @Test
    public void givenAnExistingUser_whenShowingAnUser_thenReturnsTheBook() throws Exception {
        User user = userFactory.build();
        String json = objectMapper.writeValueAsString(user);
        Mockito.when(userRepository.findById(5L)).thenReturn(Optional.of(user));
        mvc.perform(get("/api/users/5").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(json))
        ;
    }

    @Test
    public void givenAnInvalidUser_whenShowingTheUser_thenReturnsNotFound() throws Exception {
        Mockito.when(userRepository.findById(3L)).thenReturn(Optional.empty());
        mvc.perform(get("/api/users/3").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void givenABookAndUser_whenAddingABook_itSucceeds() throws Exception {
        User user = userFactory.build();
        Book book = bookFactory.build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        mvc.perform(put("/api/users/1/books").contentType(MediaType.APPLICATION_JSON).content("1"))
            .andExpect(status().isOk())
        ;
    }

    @Test
    public void givenABookAndUser_whenAddingABookAgain_itFails() throws Exception {
        User user = userFactory.build();
        Book book = bookFactory.build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        mvc.perform(put("/api/users/1/books").contentType(MediaType.APPLICATION_JSON).content("1"))
            .andExpect(status().isOk())
        ;
        mvc.perform(put("/api/users/1/books").contentType(MediaType.APPLICATION_JSON).content("1"))
            .andExpect(status().isUnprocessableEntity())
        ;
    }

    @Test
    public void givenABookAndUser_whenAddingAnInvalidBook_itFails() throws Exception {
        User user = userFactory.build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        mvc.perform(put("/api/users/1/books").contentType(MediaType.APPLICATION_JSON).content("1"))
            .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void givenABookAndUser_whenTheUserIsInvalid_itFails() throws Exception {
        Book book = bookFactory.build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        mvc.perform(put("/api/users/1/books").contentType(MediaType.APPLICATION_JSON).content("1"))
            .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void givenBookAndUser_whenTheUserHasTheBookAndItsRemoved_thenSucceeds()
        throws Exception {
        User user = userFactory.build();
        Book book = bookFactory.build();
        user.addBook(book);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        mvc.perform(delete("/api/users/1/books/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
        ;
    }

    @Test
    public void givenUser_whenTheUserRemovesABookTheyDoNotHave_thenFails() throws Exception {
        User user = userFactory.build();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        mvc.perform(delete("/api/users/1/books/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
        ;
    }
}
