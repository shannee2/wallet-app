package com.walletapp.handler;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionType;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.service.WalletService;
import org.springframework.stereotype.Component;

@Component
public class WithdrawHandler implements TransactionHandler {

    private final WalletService walletService;

    public WithdrawHandler(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    public void handle(TransactionRequest request) throws UserNotFoundException {
        walletService.withdrawMoneyFromWallet(request);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.WITHDRAW;
    }
}
