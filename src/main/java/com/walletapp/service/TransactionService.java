package com.walletapp.service;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionResponse;
import com.walletapp.exceptions.users.UserNotFoundException;
import com.walletapp.exceptions.wallets.WalletNotFoundException;
import com.walletapp.model.transaction.*;
import com.walletapp.model.wallet.Wallet;
import com.walletapp.handler.WalletHandlerRegistry;
import com.walletapp.repository.*;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.*;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionRecipientRepository transactionRecipientRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private WalletHandlerRegistry walletHandlerRegistry;


    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request, Long userId, Long walletId)
            throws UserNotFoundException, WalletNotFoundException, AccessDeniedException {

        Transaction transaction = new Transaction(
                request.getType(),
                request.getAmount(),
                request.getCurrency(),
                walletService.verifyUserWallet(userId, walletId)
        );

        transactionRepository.save(transaction);

        TransactionRecipient recipient = walletHandlerRegistry
                .getHandler(request.getType())
                .handle(request, userId, walletId, transaction);

        return new TransactionResponse(transaction, recipient);
    }

    public List<TransactionResponse> getTransactions(Long userId, Long walletId) {
        List<Transaction> transactions = transactionRepository.findByWalletId(walletId);

        return transactions.stream()
                .map(transaction -> new TransactionResponse(transaction, transaction.getRecipient())) // Updated mapping
                .toList();
    }
}
