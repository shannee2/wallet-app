package com.walletapp.service;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionResponse;
import com.walletapp.exceptions.users.UserNotFoundException;
import com.walletapp.exceptions.wallets.WalletNotFoundException;
import com.walletapp.model.transaction.*;
import com.walletapp.model.wallet.Wallet;
import com.walletapp.handler.WalletHandlerRegistry;
import com.walletapp.repository.*;

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
    private CurrencyRepository currencyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private WalletHandlerRegistry walletHandlerRegistry;


    public TransactionResponse createTransaction(TransactionRequest request, Long userId, Long walletId)
            throws UserNotFoundException, WalletNotFoundException, AccessDeniedException {

        Transaction transaction = new Transaction(
                request.getType(),
                request.getAmount(),
                currencyService.getCurrency(request.getCurrency()),
                walletService.verifyUserWallet(userId, walletId)
        );

        walletHandlerRegistry
                .getHandler(request.getType())
                .handle(request, userId, walletId, transaction);

        transactionRepository.save(transaction);

        TransactionRecipient recipient = null;

        if(request.getTransactionType() == TransactionType.TRANSFER){
            Wallet recipientWallet = walletService.findWalletById(request.getReceiverWalletId());
            recipient = new TransactionRecipient(transaction, recipientWallet);
            transactionRecipientRepository.save(recipient);
        }

        return new TransactionResponse(transaction, recipient);
    }

    public List<TransactionResponse> getTransactions(Long userId, Long walletId) throws UserNotFoundException {
        List<Transaction> transactions = transactionRepository.findByWalletId(walletId);

        return transactions.stream()
                .map(transaction -> new TransactionResponse(transaction, transaction.getRecipient())) // Updated mapping
                .toList();
    }
}
