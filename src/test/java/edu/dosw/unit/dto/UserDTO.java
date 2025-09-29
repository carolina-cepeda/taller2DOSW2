package edu.dosw.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void testUserDTO() {
        UserDTO dto = new UserDTO("hola", "ADMIN");

        assertEquals("hola", dto.username());
        assertEquals("ADMIN", dto.type());
    }
}
