import org.example.controller.UserController;
import org.example.dto.UserDTO;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void shouldCreateUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John");
        userDTO.setEmail("john@example.com");
        userDTO.setAge(30);

        UserDTO savedUser = new UserDTO();
        savedUser.setId(1L);
        savedUser.setName("John");
        savedUser.setEmail("john@example.com");
        savedUser.setAge(30);
        savedUser.setCreatedAt(LocalDateTime.now());

        Mockito.when(userService.createUser(Mockito.any(UserDTO.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"email\":\"john@example.com\",\"age\":30}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    public void shouldGetUserById() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("John");

        Mockito.when(userService.getUserById(1L)).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }
}