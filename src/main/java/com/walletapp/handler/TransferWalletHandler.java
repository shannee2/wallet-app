
package com.walletapp.handler;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.model.transaction.ParticipantRole;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionParticipant;
import com.walletapp.model.transaction.TransactionType;
import com.walletapp.model.user.User;
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
    private final UserService userService;

    public TransferWalletHandler(WalletService walletService, UserService userService) {
        this.walletService = walletService;
        this.userService = userService;
    }

    @Override
    public List<TransactionParticipant> handle(TransactionRequest request, User user, Wallet wallet, Transaction transaction) throws UserNotFoundException, AccessDeniedException {
        if(request.getTransactionParticipant().getRole() != ParticipantRole.RECEIVER){
            throw new IllegalArgumentException("TransactionParticipant role must be RECEIVER for this operation");
        }

        User receiverUser = userService.getUserById(request.getTransactionParticipant().getUserId());
        Wallet receiverWallet = walletService.findWalletById(request.getTransactionParticipant().getWalletId());

        walletService.withdrawMoneyFromWallet(request, user, wallet);
        walletService.depositMoneyToWallet(request, receiverUser, receiverWallet);


        TransactionParticipant sender = new TransactionParticipant(transaction, user, wallet, ParticipantRole.SENDER);
        TransactionParticipant receiver = new TransactionParticipant(transaction, receiverUser, receiverWallet, ParticipantRole.RECEIVER);
        return List.of(sender, receiver);
    }

//    @Override
//    public void handle(TransactionRequest request, String username, Long walletId, Transaction transaction) throws UserNotFoundException, AccessDeniedException {
//        User sender = userService.getUserByUsername(username);
//        Wallet senderWallet = walletService.verifyUserWallet(sender.getId(), walletId);
//
//        Transaction transaction = walletService.createTransaction(request, sender, senderWallet);
//
//        participantService.createParticipants(transaction, sender, senderWallet, request.getTargetUserId(), request.getTargetWalletId());
//    }

    @Override
    public TransactionType getType() {
        return TransactionType.TRANSFER;
    }
}

