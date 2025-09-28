package edu.dosw.services;

import edu.dosw.dto.UserDTO;
import edu.dosw.model.User;
import edu.dosw.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserFactory userFactory;
    private final UserRepository userRepository;

   @Autowired
    public UserService(UserFactory userFactory, UserRepository userRepository) {
        this.userFactory = userFactory;
        this.userRepository = userRepository;
    }

    public User createUser(UserDTO userDTO) {
        User user = userFactory.generateUser(userDTO);
        if(user == null){
            throw new RuntimeException("User is null");
        }
        return userRepository.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }
}
