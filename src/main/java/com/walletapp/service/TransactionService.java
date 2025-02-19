package com.walletapp.service;

import com.walletapp.dto.transaction.TransactionWalletDTO;
import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionResponse;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.exceptions.WalletNotFoundException;
import com.walletapp.model.transaction.*;
import com.walletapp.registry.WalletHandlerRegistry;
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
    private TransactionWalletRepository transactionWalletRepository;

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
                currencyService.getCurrency(request.getCurrency())
        );

        List<TransactionWallet> transactionWallets = walletHandlerRegistry
                .getHandler(request.getType())
                .handle(request, walletId, transaction);

        transactionRepository.save(transaction);
        transactionWalletRepository.saveAll(transactionWallets);

        return mapToResponse(transaction, transactionWallets);
    }

    public List<TransactionResponse> getTransactions(Long userId, Long walletId) throws UserNotFoundException {
        List<Transaction> transactions = transactionRepository.findWalletTransactions(userId);

        return transactions.stream()
                .map(transaction -> mapToResponse(transaction, transaction.getTransactionRoles()))
                .toList();
    }


    private TransactionResponse mapToResponse(Transaction transaction, List<TransactionWallet> participants) {
        List<TransactionWalletDTO> participantResponses = participants.stream()
                .map(p -> new TransactionWalletDTO(p.getWallet().getId(), p.getTransactionWalletType()))
                .toList();
        return new TransactionResponse(transaction, participantResponses);
    }

}
