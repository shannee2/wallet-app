package com.walletapp.handler;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.exceptions.users.UserNotFoundException;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionType;
import com.walletapp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
public class WithdrawWalletHandler implements WalletHandler {

    private final WalletService walletService;

    @Autowired
    public WithdrawWalletHandler(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    public void handle(TransactionRequest request, Long userId, Long walletId, Transaction transaction) throws UserNotFoundException, AccessDeniedException {
        walletService.withdrawMoney(request, userId, walletId);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.WITHDRAW;
    }
}
