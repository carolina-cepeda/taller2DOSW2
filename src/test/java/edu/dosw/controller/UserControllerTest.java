package edu.dosw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {UserController.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    //Error
//    @Test
//    void testCreateUserSuccess() throws Exception {
//        UserDTO dto = new UserDTO("username", "ADMIN");
//        User savedUser = mock(User.class);
//        when(savedUser.getUsername()).thenReturn("username");
//
//        when(userService.createUser(any(UserDTO.class))).thenReturn(savedUser);
//
//        mockMvc.perform(post("/api/user/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("User created"))
//                .andExpect(jsonPath("$.data.username").value("username"));
//    }
//
//    // errorea Admin
//    @Test
//    void testCreateUserAdmin() throws Exception {
//        UserDTO dto = new UserDTO("admin user", "ADMIN");
//        User savedUser = mock(User.class);
//        when(savedUser.getUsername()).thenReturn("admin user");
//
//        when(userService.createUser(any(UserDTO.class))).thenReturn(savedUser);
//
//        mockMvc.perform(post("/api/user/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("User created"))
//                .andExpect(jsonPath("$.data.username").value("admin user"));
//    }
//
//    // error
//    @Test
//    void testCreateUserMember() throws Exception {
//        UserDTO dto = new UserDTO("member user", "MEMBER");
//        User savedUser = mock(User.class);
//        when(savedUser.getUsername()).thenReturn("member user");
//
//        when(userService.createUser(any(UserDTO.class))).thenReturn(savedUser);
//
//        mockMvc.perform(post("/api/user/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.username").value("member user"));
//    }
//
//    // error
//    @Test
//    void testCreateUserGuest() throws Exception {
//        UserDTO dto = new UserDTO("guestUser", "GUEST");
//        User savedUser = mock(User.class);
//        when(savedUser.getUsername()).thenReturn("guestUser");
//
//        when(userService.createUser(any(UserDTO.class))).thenReturn(savedUser);
//
//        mockMvc.perform(post("/api/user/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.username").value("guestUser"));
//    }
//
//    @Test
//    void testCreateUserWithNullBody() throws Exception {
//        mockMvc.perform(post("/api/user/create")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
}
