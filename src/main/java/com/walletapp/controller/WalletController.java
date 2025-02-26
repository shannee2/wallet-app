package com.walletapp.controller;

import com.walletapp.dto.user.UserRequest;
import com.walletapp.dto.wallet.WalletRequest;
import com.walletapp.dto.wallet.WalletResponse;
import com.walletapp.exceptions.users.UserNotFoundException;
import com.walletapp.model.user.User;
import com.walletapp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/wallets")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<?> createWallet(@RequestBody WalletRequest userRequest, @PathVariable Long userId) throws UserNotFoundException {
        WalletResponse response = walletService.createWallet(userRequest, userId);
        System.out.println(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}