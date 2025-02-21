package com.walletapp.controller;

import com.walletapp.dto.general.ErrResponse;
import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionResponse;
import com.walletapp.exceptions.users.UserNotFoundException;
import com.walletapp.handler.WalletHandlerRegistry;
import com.walletapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("users/{userId}/wallets/{walletId}/transactions")
public class TransactionController {

    private final WalletHandlerRegistry walletHandlerRegistry;
    private final TransactionService transactionService;


    @Autowired
    public TransactionController(WalletHandlerRegistry walletHandlerRegistry, TransactionService transactionService) {
        this.walletHandlerRegistry = walletHandlerRegistry;
        this.transactionService = transactionService;
    }


    @PostMapping
    public ResponseEntity<?> createTransaction(
            @RequestBody TransactionRequest transactionRequest,
            @PathVariable Long walletId, @PathVariable Long userId) throws Exception {

        TransactionResponse response = transactionService.createTransaction(transactionRequest, userId, walletId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getTransactions(
            @PathVariable Long walletId, @PathVariable Long userId) throws Exception {

        List<TransactionResponse> response = transactionService.getTransactions(userId, walletId);
        return ResponseEntity.ok(response);
    }
}