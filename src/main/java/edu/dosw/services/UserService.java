package edu.dosw.services;

import edu.dosw.model.User;
import edu.dosw.model.UserType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserFactory userFactory;
    private final Map<String, User> users = new HashMap<>();

    public UserService(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    public User createUser(String id, String username, UserType type) {
        User user = userFactory.generateUser(id, username, type);
        users.put(user.getId(), user);
        return user;
    }

    public Optional<User> getUserById(String id) {
        return Optional.ofNullable(users.get(id));
    }
}
