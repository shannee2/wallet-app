package com.walletapp.handler;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.exceptions.users.UserNotFoundException;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionRecipient;
import com.walletapp.model.transaction.TransactionType;

import java.nio.file.AccessDeniedException;

public interface WalletHandler {
    TransactionRecipient handle(TransactionRequest request, Long userId, Long walletId, Transaction transaction) throws UserNotFoundException, AccessDeniedException;
    TransactionType getType();
}
