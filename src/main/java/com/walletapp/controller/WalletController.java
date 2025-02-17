package com.walletapp.controller;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.handler.TransactionHandler;
import com.walletapp.registry.TransactionHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final TransactionHandlerRegistry transactionHandlerRegistry;


    @Autowired
    public WalletController(TransactionHandlerRegistry transactionHandlerRegistry) {
        this.transactionHandlerRegistry = transactionHandlerRegistry;
    }

    @PostMapping
    public void createTransaction(@RequestBody TransactionRequest transactionRequest) throws UserNotFoundException {
        System.out.println("Type is this: "+transactionRequest.getType());
        System.out.println(transactionRequest);
        TransactionHandler handler = transactionHandlerRegistry.getHandler(transactionRequest.getType());

        if (handler == null) {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        handler.handle(transactionRequest);
    }
}