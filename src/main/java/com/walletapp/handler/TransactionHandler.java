package com.walletapp.handler;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionType;
import com.walletapp.exceptions.UserNotFoundException;

public interface TransactionHandler {
    void handle(TransactionRequest request) throws UserNotFoundException;
    TransactionType getType();
}
