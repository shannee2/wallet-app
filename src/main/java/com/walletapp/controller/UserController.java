package com.walletapp.controller;

import com.walletapp.dto.TransactionRequest;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.model.User;
import com.walletapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/register")
    public void registerUser(@RequestBody User user) {
        userService.registerUser(user);
    }

    @PutMapping("/depositMoney")
    public void depositMoney(@RequestBody TransactionRequest transactionRequest) throws UserNotFoundException {
        userService.depositMoneyToWallet(transactionRequest);
    }

    @PutMapping("/withdrawMoney")
    public void withdrawMoney(@RequestBody TransactionRequest transactionRequest) throws UserNotFoundException {
        userService.withdrawMoneyFromWallet(transactionRequest);
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