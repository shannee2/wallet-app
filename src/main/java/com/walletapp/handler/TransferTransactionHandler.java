package com.walletapp.handler;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionResponse;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.model.transaction.TransactionType;
import com.walletapp.service.TransactionService;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;



import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionResponse;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.model.transaction.TransactionType;
import com.walletapp.service.TransactionService;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
public class TransferTransactionHandler implements TransactionHandler {

    private final TransactionService transactionService;

    public TransferTransactionHandler(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public TransactionResponse handle(TransactionRequest request, Long userId, String username, Long walletId) throws UserNotFoundException, AccessDeniedException {
        return transactionService.createTransaction(request, userId, username, walletId);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.DEPOSIT;
    }
}
