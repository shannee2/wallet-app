package com.walletapp.service;

import com.walletapp.dto.transaction.TransactionParticipantDTO;
import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionResponse;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.exceptions.WalletNotFoundException;
import com.walletapp.handler.WalletHandler;
import com.walletapp.model.currency.Currency;
import com.walletapp.model.transaction.*;
import com.walletapp.model.user.User;
import com.walletapp.model.wallet.Wallet;
import com.walletapp.registry.WalletHandlerRegistry;
import com.walletapp.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionParticipantRepository participantRepository;

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

        User user = userService.getUserById(userId);
        Wallet wallet = walletService.verifyUserWallet(userId, walletId);

//        if (user == null || wallet == null) {
//            throw new AccessDeniedException("Access Denied");
//        }

        Currency currency = currencyService.getCurrency(request.getCurrency());
        Transaction transaction = new Transaction(request.getType(), request.getAmount(), currency);

        WalletHandler handler = walletHandlerRegistry.getHandler(request.getType());
        if (handler == null) {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        List<TransactionParticipant> participants = handler.handle(request, user, wallet, transaction);

        transactionRepository.save(transaction);
        participantRepository.saveAll(participants);

        // Convert participants to DTOs
        List<TransactionParticipantDTO> participantResponses = participants.stream()
                .map(p -> new TransactionParticipantDTO(
                        p.getUser().getId(),
                        p.getWallet().getId(),
                        p.getRole()))
                .toList();

        return new TransactionResponse(transaction, participantResponses);
    }


    public List<TransactionResponse> getTransactions(Long userId, Long walletId)
            throws UserNotFoundException, AccessDeniedException {

        User user = userService.getUserById(userId);
        Wallet wallet = walletService.verifyUserWallet(userId, walletId);

//        if (user == null || wallet == null) {
//            throw new AccessDeniedException("Access Denied");
//        }

        List<TransactionParticipant> participants = participantRepository.findByUser(user);

        Map<Long, List<TransactionParticipant>> transactionParticipantMap = participants.stream()
                .collect(Collectors.groupingBy(p -> p.getTransaction().getId()));

        List<Transaction> transactions = transactionParticipantMap.values().stream()
                .map(list -> list.getFirst().getTransaction())
                .toList();

        return transactions.stream()
                .map(transaction -> {
                    List<TransactionParticipantDTO> participantResponses =
                            transactionParticipantMap.get(transaction.getId())
                            .stream()
                            .map(p -> new TransactionParticipantDTO(
                                    p.getUser().getId(),
                                    p.getWallet().getId(),
                                    p.getRole()))
                            .toList();
                    return new TransactionResponse(transaction, participantResponses);
                })
                .toList();
    }



}
