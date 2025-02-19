package com.walletapp.service;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.exceptions.WalletNotFoundException;
import com.walletapp.model.user.User;
import com.walletapp.model.user.UserPrincipal;
import com.walletapp.model.wallet.Wallet;
import com.walletapp.model.currency.Currency;
import com.walletapp.model.currency.CurrencyType;
import com.walletapp.model.currency.Value;
import com.walletapp.repository.CurrencyRepository;
import com.walletapp.repository.UserRepository;
import com.walletapp.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.nio.file.AccessDeniedException;
import java.util.Objects;


@Service
public class WalletService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final WalletRepository walletRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final CurrencyService currencyService;
    private final UserService userService;

    @Autowired
    public WalletService(
            UserRepository userRepository,
            CurrencyRepository currencyRepository,
            WalletRepository walletRepository,
            @Lazy AuthenticationManager authManager,
            PasswordEncoder encoder,
            CurrencyService currencyService,
            JWTService jwtService,
            UserService userService) {
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
        this.walletRepository = walletRepository;
        this.authManager = authManager;
        this.passwordEncoder = encoder;
        this.jwtService = jwtService;
        this.currencyService = currencyService;
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new UserPrincipal(user);
    }

    private User findUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    public Wallet findWalletByUserId(Long userId){
        return walletRepository.findByUserId(userId)
                .orElseThrow(WalletNotFoundException::new);
    }

    public Wallet findWalletById(Long walletId){
        return walletRepository.findById(walletId)
                .orElseThrow(WalletNotFoundException::new);
    }

    public void depositMoneyToWallet(TransactionRequest transactionRequest, Long userId, Long walletId) throws UserNotFoundException, AccessDeniedException {
        User user = userService.getUserById(userId);
        Wallet wallet = findWalletById(walletId);
        depositMoneyToWallet(transactionRequest, user, wallet);

//        if(!Objects.equals(user.getId(), wallet.getUser().getId())){
//            throw new AccessDeniedException("Access Denied");
//        }
//
//        Currency currency = currencyService.getCurrency(transactionRequest.getCurrency());
//
//        Value value = new Value(transactionRequest.getAmount(), currency);
//        wallet.depositMoney(value);
//        walletRepository.save(wallet);
    }

    public void depositMoneyToWallet(TransactionRequest transactionRequest, User user, Wallet wallet) throws UserNotFoundException, AccessDeniedException {

        if(!Objects.equals(user.getId(), wallet.getUser().getId())){
            throw new AccessDeniedException("Access Denied");
        }

        Currency currency = currencyService.getCurrency(transactionRequest.getCurrency());

        Value value = new Value(transactionRequest.getAmount(), currency);
        wallet.depositMoney(value);
        walletRepository.save(wallet);
    }



    public void withdrawMoneyFromWallet(TransactionRequest transactionRequest, Long userId, Long walletId) throws UserNotFoundException, AccessDeniedException {
        User user = userService.getUserById(userId);
        Wallet wallet = findWalletById(walletId);

        withdrawMoneyFromWallet(transactionRequest, user, wallet);

//        if(!Objects.equals(user.getId(), wallet.getUser().getId())){
//            throw new AccessDeniedException("Access Denied");
//        }
//
//        Currency currency = currencyService.getCurrency(transactionRequest.getCurrency());
//
//        Value value = new Value(transactionRequest.getAmount(), currency);
//        wallet.withdrawMoney(value);
//        walletRepository.save(wallet);
    }

    public void withdrawMoneyFromWallet(TransactionRequest transactionRequest, User user, Wallet wallet) throws UserNotFoundException, AccessDeniedException {
        if(!Objects.equals(user.getId(), wallet.getUser().getId())){
            throw new AccessDeniedException("Access Denied");
        }

        Currency currency = currencyService.getCurrency(transactionRequest.getCurrency());

        Value value = new Value(transactionRequest.getAmount(), currency);
        wallet.withdrawMoney(value);
        walletRepository.save(wallet);
    }

    public Wallet verifyUserWallet(Long userId, Long walletId) {
        Wallet wallet = findWalletById(walletId);
        if(Objects.equals(wallet.getUser().getId(), userId)){
            return wallet;
        }
        return null;
    }
}