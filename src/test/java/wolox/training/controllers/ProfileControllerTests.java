package wolox.training.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.factories.UserFactory;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTests {

    private final UserFactory userFactory = new UserFactory();

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "test", password = "test")
    public void whenUserIsLoggedIn_thenReturnUserInformation() throws Exception {
        User user = userFactory.build();
        String json = objectMapper.writeValueAsString(user);
        Mockito.when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        mvc.perform(get("/api/profile").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(json))
        ;
    }
}
