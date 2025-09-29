package edu.dosw.controller;

import edu.dosw.dto.UserDTO;
import edu.dosw.model.MemberUser;
import edu.dosw.model.User;
import edu.dosw.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserService userService;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    void test_createUser_should_return_badRequest_when_user_is_null() {
        // Act
        ResponseEntity<?> response = userController.createUser(null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("User is null"));
        verify(userService, never()).createUser(any());
    }

    @Test
    void test_createUser_should_return_ok_when_user_is_valid() {
        // Arrange
        UserDTO dto = new UserDTO("1", "john", "MEMBER");
        User mockUser = new MemberUser("john");
        mockUser.setId("1");

        when(userService.createUser(dto)).thenReturn(mockUser);

        // Act
        ResponseEntity<?> response = userController.createUser(dto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("User created"));
        verify(userService).createUser(dto);
    }

    @Test
    void test_createUser_should_propagate_exception_from_service() {
        // Arrange
        UserDTO dto = new UserDTO("2", "errorUser", "MEMBER");
        when(userService.createUser(dto)).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userController.createUser(dto));
        assertEquals("Service error", ex.getMessage());
        verify(userService).createUser(dto);
    }
}
