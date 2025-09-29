package edu.dosw.controller;

import edu.dosw.dto.UserDTO;
import edu.dosw.exception.ResponseHandler;
import edu.dosw.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody(required = false) UserDTO user) {
        if(user == null){
            return ResponseHandler.generateErrorResponse("User is null", HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.generateResponse("User created", HttpStatus.OK, userService.createUser(user));
    }
}

