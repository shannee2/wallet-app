package com.walletapp.handler;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionParticipant;
import com.walletapp.model.transaction.TransactionType;
import com.walletapp.model.user.User;
import com.walletapp.model.wallet.Wallet;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface WalletHandler {
    List<TransactionParticipant> handle(TransactionRequest request, User user, Wallet wallet, Transaction transaction) throws UserNotFoundException, AccessDeniedException;
    TransactionType getType();
}
