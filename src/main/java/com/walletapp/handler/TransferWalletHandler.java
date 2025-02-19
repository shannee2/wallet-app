
package com.walletapp.handler;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.model.transaction.TransactionWalletType;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionWallet;
import com.walletapp.model.transaction.TransactionType;
import com.walletapp.model.wallet.Wallet;
//import com.walletapp.service.TransactionParticipantService;
import com.walletapp.service.UserService;
import com.walletapp.service.WalletService;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Component
public class TransferWalletHandler implements WalletHandler {

    private final WalletService walletService;

    public TransferWalletHandler(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    public List<TransactionWallet> handle(TransactionRequest request, Long walletId, Transaction transaction) throws UserNotFoundException, AccessDeniedException {
        if(request.getTransactionWallet().getTransactionWalletType() != TransactionWalletType.RECEIVER){
            throw new IllegalArgumentException("TransactionParticipant role must be RECEIVER for this operation");
        }

        Wallet senderWallet = walletService.withdrawMoneyFromWallet(request, walletId);
        Wallet receiverWallet = walletService.depositMoneyToWallet(request, request.getTransactionWallet().getWalletId());

        TransactionWallet sender = new TransactionWallet(transaction, senderWallet, TransactionWalletType.SENDER);
        TransactionWallet receiver = new TransactionWallet(transaction, receiverWallet, TransactionWalletType.RECEIVER);

        return List.of(sender, receiver);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.TRANSFER;
    }
}