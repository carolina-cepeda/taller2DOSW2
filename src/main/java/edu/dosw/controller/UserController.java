package edu.dosw.controller;

import edu.dosw.model.User;
import edu.dosw.model.UserType;
import edu.dosw.services.UserService;

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
    public User createUser(@RequestParam String id,@RequestParam String username, @RequestParam UserType type) {
        return userService.createUser(id,username, type);
    }
}

