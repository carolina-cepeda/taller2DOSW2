package edu.dosw.services;

import edu.dosw.dto.UserDTO;
import edu.dosw.model.*;
import edu.dosw.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        // ⚠️ Aquí usamos directamente la implementación real de UserFactory
        userService = new UserService(new UserFactory(), userRepository);
    }

    @Test
    void test_UserService_should_throw_when_userDTO_is_null() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(null));
        assertEquals("User DTO cannot be null", ex.getMessage());
    }

    @Test
    void test_UserService_should_throw_when_user_already_exists() {
        UserDTO dto = new UserDTO("1", "john", "MEMBER");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(dto));

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> userService.createUser(dto));
        assertEquals("User already exists: john", ex.getMessage());
    }

    @Test
    void test_UserService_should_throw_when_username_is_null() {
        UserDTO dto = new UserDTO("1", null, "MEMBER");
        when(userRepository.findByUsername(null)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.createUser(dto));
        assertEquals("Error inesperado al crear el usuario", ex.getMessage());
    }

    @Test
    void test_UserService_should_create_member_user_successfully() {
        UserDTO dto = new UserDTO("1", "john", "MEMBER");
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());

        User result = userService.createUser(dto);

        assertTrue(result instanceof MemberUser);
        assertEquals("john", result.getUserName());
        verify(userRepository).save(any(UserDTO.class));
    }

    @Test
    void test_UserService_should_create_admin_user_successfully() {
        UserDTO dto = new UserDTO("2", "admin", "ADMIN");
        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());

        User result = userService.createUser(dto);

        assertTrue(result instanceof AdminUser);
        assertEquals("admin", result.getUserName());
        verify(userRepository).save(any(UserDTO.class));
    }

    @Test
    void test_UserService_should_create_guest_user_successfully() {
        UserDTO dto = new UserDTO("3", "guestUser", "GUEST");
        when(userRepository.findByUsername("guestUser")).thenReturn(Optional.empty());

        User result = userService.createUser(dto);

        assertTrue(result instanceof GuestUser);
        assertEquals("guestUser", result.getUserName());
        verify(userRepository).save(any(UserDTO.class));
    }

    @Test
    void test_UserFactory_should_throw_when_userDTO_is_null() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> UserFactory.generateUser(null));
        assertEquals("User DTO cannot be null", ex.getMessage());
    }

    @Test
    void test_UserFactory_should_throw_when_invalid_type() {
        UserDTO dto = new UserDTO("10", "invalidUser", "INVALID");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> UserFactory.generateUser(dto));
        assertTrue(ex.getMessage().contains("Invalid user type"));
    }

    @Test
    void test_UserFactory_should_assign_id_when_not_null() {
        UserDTO dto = new UserDTO("42", "withId", "MEMBER");

        User user = UserFactory.generateUser(dto);

        assertEquals("42", user.getId());
        assertEquals("withId", user.getUserName());
        assertTrue(user instanceof MemberUser);
    }

    @Test
    void test_UserService_should_throw_on_duplicateKeyException() {
        UserDTO dto = new UserDTO("1", "john", "MEMBER");
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
        doThrow(new DuplicateKeyException("duplicate")).when(userRepository).save(any(UserDTO.class));

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> userService.createUser(dto));
        assertTrue(ex.getMessage().contains("Ya existe un usuario"));
    }

    @Test
    void test_UserService_should_throw_on_unexpected_exception() {
        UserDTO dto = new UserDTO("1", "john", "MEMBER");
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());
        doThrow(new RuntimeException("boom")).when(userRepository).save(any(UserDTO.class));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.createUser(dto));
        assertTrue(ex.getMessage().contains("Error inesperado"));
    }

    @Test
    void test_UserService_should_return_user_by_id() {
        UserDTO dto = new UserDTO("1", "john", "MEMBER");
        when(userRepository.findById("1")).thenReturn(Optional.of(dto));

        User result = userService.getUserById("1");

        assertTrue(result instanceof MemberUser);
        assertEquals("john", result.getUserName());
    }

    @Test
    void test_UserService_should_throw_when_id_is_null() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.getUserById(null));
        assertEquals("El ID del usuario no puede estar vacío", ex.getMessage());
    }

    @Test
    void test_UserService_should_throw_when_id_is_empty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.getUserById("  "));
        assertEquals("El ID del usuario no puede estar vacío", ex.getMessage());
    }

    @Test
    void test_UserService_should_throw_when_user_not_found() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.getUserById("1"));
        assertTrue(ex.getMessage().contains("No se encontró el usuario"));
    }
}
