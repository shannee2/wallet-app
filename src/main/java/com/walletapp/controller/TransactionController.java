package com.walletapp.controller;

import com.walletapp.dto.general.ErrorResponse;
import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionResponse;
import com.walletapp.registry.WalletHandlerRegistry;
import com.walletapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/{userId}/wallets")
public class TransactionController {

    private final WalletHandlerRegistry walletHandlerRegistry;
    private final TransactionService transactionService;


    @Autowired
    public TransactionController(WalletHandlerRegistry walletHandlerRegistry, TransactionService transactionService) {
        this.walletHandlerRegistry = walletHandlerRegistry;
        this.transactionService = transactionService;
    }


    @PostMapping("/{walletId}/transactions")
    public ResponseEntity<?> createTransaction(
            @RequestBody TransactionRequest transactionRequest,
            @PathVariable Long walletId, @PathVariable Long userId) {
        try {
//            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            TransactionResponse response = transactionService.createTransaction(transactionRequest, userId, walletId);
            return ResponseEntity.ok(
                    response
            );
        } catch (Exception e){
            System.out.println("Exception: "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500,"Internal server error, "+e.getMessage()));
        }
    }

    @GetMapping("/{walletId}/transactions")
    public ResponseEntity<?> getTransactions(
            @PathVariable Long walletId, @PathVariable Long userId) {
        try {
            List<TransactionResponse> response = transactionService.getTransactions(userId, walletId);
            return ResponseEntity.ok(
                    response
            );
        }catch (Exception e){
            System.out.println("Exception: "+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500,"Internal server error, "+e.getMessage()));
        }
    }
}