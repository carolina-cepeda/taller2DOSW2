package edu.dosw.services;

import edu.dosw.dto.UserDTO;
import edu.dosw.model.AdminUser;
import edu.dosw.model.GuestUser;
import edu.dosw.model.MemberUser;
import edu.dosw.model.User;

public class UserFactory {

    public static User generateUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User DTO cannot be null");
        }
        
        User user = switch (userDTO.type().toUpperCase()) {
            case "ADMIN" -> new AdminUser(userDTO.username());
            case "MEMBER" -> new MemberUser(userDTO.username());
            case "GUEST" -> new GuestUser(userDTO.username());
            default -> throw new IllegalArgumentException("Invalid user type: " + userDTO.type());
        };
        
        if (userDTO.id() != null) {
            user.setId(userDTO.id());
        }
        
        return user;
    }
}
