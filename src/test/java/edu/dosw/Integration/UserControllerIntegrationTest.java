package edu.dosw.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.controller.UserController;
import edu.dosw.dto.UserDTO;
import edu.dosw.model.AdminUser;
import edu.dosw.model.User;
import edu.dosw.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserController.class})
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO sampleUserDTO() {
        return new UserDTO("1", "johndoe", "ADMIN");
    }

    private User sampleUserEntity() {
        return new AdminUser("johndoe");
    }

    @Test
    void testCreateUser_success() throws Exception {
        UserDTO dto = sampleUserDTO();
        User userEntity = sampleUserEntity();

        when(userService.createUser(dto)).thenReturn(userEntity);

        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User created"))
                .andExpect(jsonPath("$.data.userName").value("johndoe"));
    }

    @Test
    void testCreateUser_nullBody_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User is null"))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void testGetUserById_success() throws Exception {
        String userId = "1";
        User userEntity = sampleUserEntity();

        when(userService.getUserById(userId)).thenReturn(userEntity);

        mockMvc.perform(get("/api/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User found"))
                .andExpect(jsonPath("$.data.userName").value("johndoe"));
    }

    @Test
    void testGetUserById_nullId_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/user/{userId}", " "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User id cannot be null or empty"))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void testGetUserById_notFound() throws Exception {
        String userId = "99";

        when(userService.getUserById(userId)).thenReturn(null);

        mockMvc.perform(get("/api/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User found"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}
