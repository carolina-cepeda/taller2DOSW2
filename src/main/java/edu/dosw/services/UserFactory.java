package edu.dosw.services;

import edu.dosw.dto.UserDTO;
import edu.dosw.model.AdminUser;
import edu.dosw.model.GuestUser;
import edu.dosw.model.MemberUser;
import edu.dosw.model.User;
public class UserFactory {

    public User generateUser(UserDTO userDTO) {
        return switch (userDTO.type()) {
            case "ADMIN" -> new AdminUser(userDTO.username());
            case "MEMBER" -> new MemberUser(userDTO.username());
            case "GUEST" -> new GuestUser(userDTO.username());
            default -> throw new IllegalArgumentException("Invalid user type");
        };
    }
}
