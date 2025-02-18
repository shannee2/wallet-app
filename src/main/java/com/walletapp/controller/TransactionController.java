package com.walletapp.controller;

import com.walletapp.dto.general.ErrorResponse;
import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionResponse;
import com.walletapp.handler.TransactionHandler;
import com.walletapp.registry.TransactionHandlerRegistry;
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

    private final TransactionHandlerRegistry transactionHandlerRegistry;
    private final TransactionService transactionService;


    @Autowired
    public TransactionController(TransactionHandlerRegistry transactionHandlerRegistry, TransactionService transactionService) {
        this.transactionHandlerRegistry = transactionHandlerRegistry;
        this.transactionService = transactionService;
    }


    @PostMapping("/{walletId}/transactions")
    public ResponseEntity<?> createTransaction(
            @RequestBody TransactionRequest transactionRequest,
            @PathVariable Long walletId, @PathVariable String userId) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            TransactionHandler handler = transactionHandlerRegistry.getHandler(transactionRequest.getType());

            if (handler == null) {
                return ResponseEntity.badRequest().body(null);
            }

            TransactionResponse response = handler.handle(transactionRequest, Long.valueOf(userId), username, walletId);
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
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            List<TransactionResponse> response = transactionService.getTransactions(userId, username, walletId);

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