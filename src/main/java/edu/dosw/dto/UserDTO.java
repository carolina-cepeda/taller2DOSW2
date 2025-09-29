package edu.dosw.dto;

/**
 * Data Transfer Object (DTO) for user-related data transfer between layers.
 * This class is used to transfer user data without exposing the domain model.
 *
 * @param id The unique identifier of the user (auto-generated if not provided)
 * @param username The display name of the user
 * @param type The type/role of the user (e.g., "admin", "member", "guest")
 */
public record UserDTO (
        String id,
        String username,
        String type
) {
    /**
     * Creates a new UserDTO with auto-generated ID.
     * This constructor is typically used when creating new users.
     *
     * @param username The display name of the user
     * @param type The type/role of the user
     * @throws IllegalArgumentException if username or type is null or empty
     */
    public UserDTO(String username, String type) {
        this(null, username, type);
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("User type cannot be null or empty");
        }
    }
}
