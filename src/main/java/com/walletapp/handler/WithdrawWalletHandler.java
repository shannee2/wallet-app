package com.walletapp.handler;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.model.transaction.ParticipantRole;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionParticipant;
import com.walletapp.model.transaction.TransactionType;
import com.walletapp.model.user.User;
import com.walletapp.model.wallet.Wallet;
import com.walletapp.service.WalletService;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Component
public class WithdrawWalletHandler implements WalletHandler {

    private final WalletService walletService;

    public WithdrawWalletHandler(WalletService transactionService) {
        this.walletService = transactionService;
    }

    @Override
    public List<TransactionParticipant> handle(TransactionRequest request, User user, Wallet wallet, Transaction transaction) throws UserNotFoundException, AccessDeniedException {
        walletService.withdrawMoneyFromWallet(request, user.getId(), wallet.getId());
        return List.of(new TransactionParticipant(transaction, user, wallet, ParticipantRole.SELF));
    }

    @Override
    public TransactionType getType() {
        return TransactionType.WITHDRAW;
    }
}
