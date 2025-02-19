package com.walletapp.handler;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionWallet;
import com.walletapp.model.transaction.TransactionType;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface WalletHandler {
    List<TransactionWallet> handle(TransactionRequest request, Long walletId, Transaction transaction) throws UserNotFoundException, AccessDeniedException;
    TransactionType getType();
}
