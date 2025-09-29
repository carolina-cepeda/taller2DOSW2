package edu.dosw.repositories;

import edu.dosw.dto.UserDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDTO, String> {
    @Query("{ 'username': ?0 }")
    Optional<UserDTO> findByUsername(String username);
}
