package com.walletapp.handler;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionResponse;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.model.transaction.TransactionType;

import java.nio.file.AccessDeniedException;

public interface TransactionHandler {
    TransactionResponse handle(TransactionRequest request, Long userId, String username, Long walletId) throws UserNotFoundException, AccessDeniedException;
    TransactionType getType();
}
