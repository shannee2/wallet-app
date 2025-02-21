package com.walletapp.controller;

import com.walletapp.dto.user.UserResponse;
import com.walletapp.dto.user.UserRequest;
import com.walletapp.exceptions.users.UserNotFoundException;
import com.walletapp.model.user.User;
import com.walletapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        UserResponse response = userService.registerUser(user);
        System.out.println(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRequest userRequest) throws UserNotFoundException, AccessDeniedException {
        UserResponse response = userService.verify(userRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) throws UserNotFoundException {
        return userService.getUserByUsername(username);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}