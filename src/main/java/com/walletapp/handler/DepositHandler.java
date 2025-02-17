package com.walletapp.handler;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionType;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.service.WalletService;
import org.springframework.stereotype.Component;

@Component
public class DepositHandler implements TransactionHandler {

    private final WalletService walletService;

    public DepositHandler(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    public void handle(TransactionRequest request) throws UserNotFoundException {
        walletService.depositMoneyToWallet(request);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.DEPOSIT;
    }
}
