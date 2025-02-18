package com.walletapp.service;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionResponse;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.exceptions.WalletNotFoundException;
import com.walletapp.model.currency.Currency;
import com.walletapp.model.currency.CurrencyType;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionType;
import com.walletapp.model.user.User;
import com.walletapp.model.wallet.Wallet;
import com.walletapp.repository.CurrencyRepository;
import com.walletapp.repository.TransactionRepository;
import com.walletapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private UserRepository userRepository;

    public TransactionResponse createTransaction(TransactionRequest request, Long userId, String username, Long walletId) throws UserNotFoundException, WalletNotFoundException, AccessDeniedException {
        User user = userService.verifyUsername(userId, username);
        Wallet wallet = walletService.verifyUserWallet(userId, walletId);

        if(user == null && wallet ==null){
            throw new AccessDeniedException("Access Denied");
        }

        Currency currency = currencyRepository.findByType(CurrencyType.valueOf(request.getCurrency()))
                .orElseThrow(() -> new RuntimeException("Currency not found"));

        if (request.getType() == TransactionType.DEPOSIT) {
            walletService.depositMoneyToWallet(request, username);
        } else if (request.getType() == TransactionType.WITHDRAW) {
            walletService.withdrawMoneyFromWallet(request, username);
        } else {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        Transaction transaction = new Transaction(wallet, user, request.getType(), request.getAmount(), currency);
        transactionRepository.save(transaction);
        return new TransactionResponse(transaction.getId(), transaction.getWallet().getId(), transaction.getUser().getId(), CurrencyType.valueOf(request.getCurrency()), request.getAmount(), transaction.getDate(), transaction.getType(), "Transaction Success");
    }


    public List<TransactionResponse> getTransactions(Long userId, String username, Long walletId) throws UserNotFoundException, AccessDeniedException {
        User user = userService.verifyUsername(userId, username);
        Wallet wallet = walletService.verifyUserWallet(userId, walletId);

        if(user == null && wallet ==null){
            throw new AccessDeniedException("Access Denied");
        }

        List<Transaction> transactions = transactionRepository.findByWalletId(walletId);
        List<TransactionResponse> transactionResponse = new ArrayList<>();


        for(Transaction transaction: transactions) {
            Long targetUserId = transaction.getTargetUser()!=null ? transaction.getTargetUser().getId() : null;
            Long targetWalletId = transaction.getTargetWallet()!=null ? transaction.getTargetWallet().getId() : null;

            TransactionResponse eachTransaction = new TransactionResponse(
                    transaction.getId(),
                    transaction.getWallet().getId(),
                    transaction.getUser().getId(),
                    transaction.getCurrency().getType(),
                    transaction.getAmount(),
                    transaction.getDate(),
                    transaction.getType(),"Transaction Success",
                    targetUserId,
                    targetWalletId
            );
            transactionResponse.add(eachTransaction);
        }
        return transactionResponse;
    }
}