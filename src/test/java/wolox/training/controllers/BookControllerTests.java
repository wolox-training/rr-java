package wolox.training.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTests {

    private final BookFactory bookFactory = new BookFactory();

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void givenBooks_whenListingAllBooks_thenAListOfBooksIsReturned() throws Exception {
        int count = bookFactory.faker().number().numberBetween(3, 5);
        List<Book> books = bookFactory.buildList(count);
        String bookJson = objectMapper.writeValueAsString(books);
        Mockito.when(bookRepository.findAll()).thenReturn(books);
        mvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(count)))
            .andExpect(content().json(bookJson))
        ;
    }

    @Test
    public void givenAnExistingBook_whenShowingABook_thenReturnsTheBook() throws Exception {
        Book book = bookFactory.build();
        String bookJson = objectMapper.writeValueAsString(book);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        mvc.perform(get("/api/books/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(bookJson))
        ;
    }

    @Test
    public void givenAnInvalidBook_whenShowingABook_thenFails() throws Exception {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        mvc.perform(get("/api/books/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void givenValidParameters_whenCreatingABook_thenReturnsTheNewBook() throws Exception {
        Book book = bookFactory.build();
        String bookJson = objectMapper.writeValueAsString(book);
        mvc.perform(
            post("/api/books").contentType(MediaType.APPLICATION_JSON).content(bookJson)
        ).andExpect(status().isCreated());
    }

    @Test
    public void givenInvalidParameters_whenCreatingABook_thenFails() throws Exception {
        String badJson = "";
        mvc.perform(
            post("/api/books").contentType(MediaType.APPLICATION_JSON).content(badJson)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void givenAnExistingBook_whenUpdatingTheBook_itSuccess() throws Exception {
        Book book = bookFactory.build();
        // Just add different data for parameters
        String bookJson = objectMapper.writeValueAsString(bookFactory.build());
        Mockito.when(bookRepository.findById(0L)).thenReturn(Optional.of(book));
        mvc.perform(put("/api/books/0").contentType(MediaType.APPLICATION_JSON).content(bookJson))
            .andExpect(status().isOk());
    }

    @Test
    public void givenAnInvalidBook_whenUpdatingTheBook_itFailsWithNotFound() throws Exception {
        Mockito.when(bookRepository.findById(10L)).thenReturn(Optional.empty());
        mvc.perform(put("/api/books/10").contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void givenInvalidParameters_whenUpdatingTheBook_itFailsWithBadRequest()
        throws Exception {
        Book book = bookFactory.build();
        // Just add different data for parameters
        String badJson = "";
        Mockito.when(bookRepository.findById(0L)).thenReturn(Optional.of(book));
        mvc.perform(put("/api/books/0").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void givenAnExistingBook_whenDestroyingTheBook_thenDeletesTheBook() throws Exception {
        Book book = bookFactory.build();
        Mockito.when(bookRepository.findById(2L)).thenReturn(Optional.of(book));
        mvc.perform(delete("/api/books/2").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
        ;
    }

    @Test
    public void givenAnInvalidBook_whenDestroyingTheBook_thenReturnsNotFound() throws Exception {
        Mockito.when(bookRepository.findById(2L)).thenReturn(Optional.empty());
        mvc.perform(delete("/api/books/2").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
        ;
    }
}
