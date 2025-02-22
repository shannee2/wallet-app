
package com.walletapp.handler;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.exceptions.users.UserNotFoundException;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionRecipient;
import com.walletapp.model.transaction.TransactionType;
//import com.walletapp.service.TransactionParticipantService;
import com.walletapp.model.wallet.Wallet;
import com.walletapp.repository.TransactionRecipientRepository;
import com.walletapp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
public class TransferWalletHandler implements WalletHandler {

    private final WalletService walletService;
    private final TransactionRecipientRepository transactionRecipientRepository;

    @Autowired
    public TransferWalletHandler(WalletService walletService, TransactionRecipientRepository transactionRecipientRepository) {
        this.walletService = walletService;
        this.transactionRecipientRepository = transactionRecipientRepository;
    }

    @Override
    public TransactionRecipient handle(TransactionRequest request, Long userId, Long walletId, Transaction transaction) throws UserNotFoundException, AccessDeniedException {
        walletService.withdrawMoney(request, userId, walletId);
        Wallet recipientWallet = walletService.depositMoney(request, request.getReceiverWalletId());
        System.out.println("Handling "+recipientWallet);
        TransactionRecipient recipient = new TransactionRecipient(transaction, recipientWallet);
        return transactionRecipientRepository.save(recipient);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.TRANSFER;
    }
}