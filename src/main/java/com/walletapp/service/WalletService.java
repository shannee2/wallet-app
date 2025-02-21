package com.walletapp.service;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.exceptions.users.UserNotFoundException;
import com.walletapp.exceptions.wallets.WalletNotFoundException;
import com.walletapp.model.user.User;
import com.walletapp.model.user.UserPrincipal;
import com.walletapp.model.wallet.Wallet;
import com.walletapp.model.money.Currency;
import com.walletapp.model.money.Money;
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

    public Wallet findWalletById(Long walletId){
        return walletRepository.findById(walletId)
                .orElseThrow(WalletNotFoundException::new);
    }

    public Wallet depositMoney(TransactionRequest transactionRequest, Long walletId) throws UserNotFoundException, AccessDeniedException {
        Wallet wallet = findWalletById(walletId);
        return depositMoney(transactionRequest, wallet.getUser().getId(), walletId);
    }

    public Wallet depositMoney(TransactionRequest transactionRequest, Long userId, Long walletId) throws UserNotFoundException, AccessDeniedException {
        Wallet wallet = verifyUserWallet(userId, walletId);
        Currency currency = currencyService.getCurrency(transactionRequest.getCurrency());

        Money money = new Money(transactionRequest.getAmount(), currency);
        wallet.deposit(money);
        return walletRepository.save(wallet);
    }

    public Wallet withdrawMoney(TransactionRequest transactionRequest, Long userId, Long walletId) throws UserNotFoundException, AccessDeniedException {
        Wallet wallet = verifyUserWallet(userId, walletId);
        Currency currency = currencyService.getCurrency(transactionRequest.getCurrency());

        Money money = new Money(transactionRequest.getAmount(), currency);
        wallet.withdraw(money);
        return walletRepository.save(wallet);
    }

    public Wallet verifyUserWallet(Long userId, Long walletId) throws AccessDeniedException {
        return walletRepository.findByIdAndUserId(walletId, userId)
                .orElseThrow(() -> new AccessDeniedException("Wallet does not belong to the user"));
    }
}