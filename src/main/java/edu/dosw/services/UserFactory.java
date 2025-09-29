package edu.dosw.services;

import edu.dosw.dto.UserDTO;
import edu.dosw.model.AdminUser;
import edu.dosw.model.GuestUser;
import edu.dosw.model.MemberUser;
import edu.dosw.model.User;
import org.springframework.stereotype.Component;

/**
 * Factory class for creating User instances based on UserDTO.
 * Handles the creation of different user types (Admin, Member, Guest).
 */
@Component
public class UserFactory {

    /**
     * Creates a User instance based on the provided UserDTO.
     * 
     * @param userDTO The data transfer object containing user information
     * @return A new User instance of the appropriate type
     * @throws IllegalArgumentException if userDTO is null or contains an invalid user type
     */
    public static User generateUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User DTO cannot be null");
        }
        
        User user = switch (userDTO.type().toUpperCase()) {
            case "ADMIN" -> new AdminUser(userDTO.username());
            case "MEMBER" -> new MemberUser(userDTO.username());
            case "GUEST" -> new GuestUser(userDTO.username());
            default -> throw new IllegalArgumentException(
                String.format("Invalid user type: %s. Must be one of: ADMIN, MEMBER, GUEST", userDTO.type())
            );
        };
        
        if (userDTO.id() != null) {
            user.setId(userDTO.id());
        }
        
        return user;
    }
}
