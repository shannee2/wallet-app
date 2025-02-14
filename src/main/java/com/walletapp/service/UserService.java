package com.walletapp.service;

import com.walletapp.config.AppConfig;
import com.walletapp.dto.TransactionRequest;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.exceptions.WalletNotFoundException;
import com.walletapp.model.User;
import com.walletapp.model.UserPrincipal;
import com.walletapp.model.Wallet;
import com.walletapp.model.currency.Currency;
import com.walletapp.model.currency.CurrencyType;
import com.walletapp.model.currency.Value;
import com.walletapp.repository.CurrencyRepository;
import com.walletapp.repository.UserRepository;
import com.walletapp.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final WalletRepository walletRepository;

    @Autowired
    public UserService(UserRepository userRepository, CurrencyRepository currencyRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new UserPrincipal(user);
    }

    @Transactional
    public void registerUser(User user) {
        Currency currency = currencyRepository.findByType(CurrencyType.INR)
                .orElseThrow(() -> new IllegalArgumentException("Currency not found"));

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            ResponseEntity.badRequest().body("Username already taken");
            return;
        }

        user.setPassword(AppConfig.passwordEncoder().encode(user.getPassword()));
        Wallet wallet = new Wallet(currency, user);
        userRepository.save(user);
        walletRepository.save(wallet);
    }

    public User getUser(String username) throws UserNotFoundException {
        return this.findUserByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private User findUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    private Wallet findWalletByUserId(Long userId){
        return walletRepository.findByUserId(userId)
                .orElseThrow(WalletNotFoundException::new);
    }


    public void depositMoneyToWallet(TransactionRequest transactionRequest) throws UserNotFoundException {
        User user = findUserByUsername(transactionRequest.getUsername());
        Wallet wallet = findWalletByUserId(user.getId());

        Currency currency = currencyRepository.findByType(CurrencyType.valueOf(transactionRequest.getCurrency()))
                .orElseThrow(() -> new IllegalArgumentException("Currency not found"));

        Value value = new Value(transactionRequest.getAmount(), currency);
        wallet.depositMoney(value);
        walletRepository.save(wallet);
    }

    public void withdrawMoneyFromWallet(TransactionRequest transactionRequest) throws UserNotFoundException {
        User user = findUserByUsername(transactionRequest.getUsername());
        Wallet wallet = findWalletByUserId(user.getId());

        Currency currency = currencyRepository.findByType(CurrencyType.valueOf(transactionRequest.getCurrency()))
                .orElseThrow(() -> new IllegalArgumentException("Currency not found"));

        Value value = new Value(transactionRequest.getAmount(), currency);
        wallet.withdrawMoney(value);
        walletRepository.save(wallet);
    }
}