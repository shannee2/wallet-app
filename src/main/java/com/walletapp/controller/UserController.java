package com.walletapp.controller;

import com.walletapp.dto.general.UserResponse;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.model.user.User;
import com.walletapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        String token = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(true, 201,"User registered successfully", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        String token = userService.verify(user);
        return ResponseEntity.ok(new UserResponse(true, 200,"Login success", token));
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) throws UserNotFoundException {
        return userService.getUser(username);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}