package edu.dosw.services;

import edu.dosw.dto.UserDTO;
import edu.dosw.model.AdminUser;
import edu.dosw.model.MemberUser;
import edu.dosw.model.User;
import edu.dosw.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * Service class for user-related operations.
 * Handles user creation, retrieval, and type management.
 */
@Service
public class UserService {

    private final UserFactory userFactory;
    private final UserRepository userRepository;

    /**
     * Initializes the UserService with required dependencies.
     *
     * @param userFactory Factory for creating user instances
     * @param userRepository Repository for user data access
     */
    @Autowired
    public UserService(UserFactory userFactory, UserRepository userRepository) {
        this.userFactory = userFactory;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user with the provided user data.
     *
     * @param userDTO The data transfer object containing user information
     * @return The created user
     * @throws IllegalArgumentException if userDTO is null
     * @throws IllegalStateException if a user with the same username already exists
     * @throws RuntimeException for other unexpected errors during user creation
     */
    public User createUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User DTO cannot be null");
        }
        
        try {
            userRepository.findByUsername(userDTO.username()).ifPresent(u -> {
                throw new IllegalStateException("User already exists: " + userDTO.username());
            });

            if (userDTO.username() == null) {
                throw new RuntimeException("Failed to save user: username cannot be null");
            }

            User user = userFactory.generateUser(userDTO);
            userRepository.save(new UserDTO(
                    user.getId(),
                    user.getUserName(),
                    getUserType(user)));
            return user;

        } catch (IllegalArgumentException | IllegalStateException e) {
            throw e;
        } catch (DuplicateKeyException e) {
            throw new IllegalStateException("Error: Ya existe un usuario con ese identificador", e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al crear el usuario", e);
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve
     * @return The user with the specified ID
     * @throws IllegalArgumentException if the ID is null or empty
     * @throws RuntimeException if no user is found with the specified ID
     */
    public User getUserById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede estar vacío");
        }
        
        UserDTO userDTO = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el usuario con ID: " + id));
            
        return userFactory.generateUser(userDTO);
    }

    /**
     * Determines the type of a user.
     *
     * @param user The user to check
     * @return A string representing the user type ("ADMIN", "MEMBER", or "GUEST")
     * @throws IllegalArgumentException if user is null
     */
    private String getUserType(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user instanceof AdminUser) {
            return "ADMIN";
        } else if (user instanceof MemberUser) {
            return "MEMBER";
        }
        return "GUEST";
    }
}
