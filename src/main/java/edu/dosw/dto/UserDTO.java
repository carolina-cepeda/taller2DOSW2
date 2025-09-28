package edu.dosw.dto;

public record UserDTO (
        String id,
        String username,
        String type
) {
    public UserDTO(String username, String type) {
        this(null, username, type);
    }
}
