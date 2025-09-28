package edu.dosw.services;

import edu.dosw.model.User;
import edu.dosw.model.UserType;

public class UserFactory {

    public User generateUser(String id, String username, UserType type) {
        return new User(id,username,type);
    }
}
