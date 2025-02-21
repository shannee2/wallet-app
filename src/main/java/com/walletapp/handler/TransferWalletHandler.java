
package com.walletapp.handler;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.exceptions.users.UserNotFoundException;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionType;
//import com.walletapp.service.TransactionParticipantService;
import com.walletapp.service.WalletService;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
public class TransferWalletHandler implements WalletHandler {

    private final WalletService walletService;

    public TransferWalletHandler(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    public void handle(TransactionRequest request, Long userId, Long walletId, Transaction transaction) throws UserNotFoundException, AccessDeniedException {
        walletService.withdrawMoney(request, userId, walletId);
        walletService.depositMoney(request, request.getReceiverWalletId());
    }

    @Override
    public TransactionType getType() {
        return TransactionType.TRANSFER;
    }
}