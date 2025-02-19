package com.walletapp.handler;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.model.transaction.TransactionWalletType;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionWallet;
import com.walletapp.model.transaction.TransactionType;
import com.walletapp.model.user.User;
import com.walletapp.model.wallet.Wallet;
import com.walletapp.service.UserService;
import com.walletapp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Component
public class WithdrawWalletHandler implements WalletHandler {

    private final WalletService walletService;

    @Autowired
    public WithdrawWalletHandler(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    public List<TransactionWallet> handle(TransactionRequest request, Long walletId, Transaction transaction) throws UserNotFoundException, AccessDeniedException {
        Wallet wallet = walletService.withdrawMoneyFromWallet(request, walletId);
        return List.of(new TransactionWallet(transaction, wallet, TransactionWalletType.SELF));
    }

    @Override
    public TransactionType getType() {
        return TransactionType.WITHDRAW;
    }
}
