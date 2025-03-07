package com.walletapp.service;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.wallet.WalletRequest;
import com.walletapp.dto.wallet.WalletResponse;
import com.walletapp.exceptions.users.UserNotFoundException;
import com.walletapp.exceptions.wallets.WalletNotFoundException;
import com.walletapp.model.user.User;
import com.walletapp.model.user.UserPrincipal;
import com.walletapp.model.wallet.Wallet;
import com.walletapp.model.money.Money;
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
    private final WalletRepository walletRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final CurrencyService currencyService;

    @Autowired
    public WalletService(
            UserRepository userRepository,
            WalletRepository walletRepository,
            @Lazy AuthenticationManager authManager,
            PasswordEncoder encoder,
            JWTService jwtService, CurrencyService currencyService) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.authManager = authManager;
        this.passwordEncoder = encoder;
        this.jwtService = jwtService;
        this.currencyService = currencyService;
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

    public Wallet createWallet(User user, String currency){
        if(currency == null){
            currency = "INR";
        }
        Wallet wallet = new Wallet(currency, user);
        return walletRepository.save(wallet);
    }

    public WalletResponse createWallet(WalletRequest request, Long userId) {
        User user = userRepository.findById(userId).get();
        Wallet wallet = createWallet(user, request.getCurrency());
        return new WalletResponse(wallet.getId());
    }

    public Wallet depositMoney(TransactionRequest transactionRequest, Long walletId) throws UserNotFoundException, AccessDeniedException {
        Wallet wallet = findWalletById(walletId);
        return depositMoney(transactionRequest, wallet.getUser().getId(), walletId);
    }

    public Wallet depositMoney(TransactionRequest transactionRequest, Long userId, Long walletId) throws UserNotFoundException, AccessDeniedException {
        Wallet wallet = verifyUserWallet(userId, walletId);

        Money money = new Money(transactionRequest.getAmount(), transactionRequest.getCurrency());
        Money moneyToDeposit = currencyService.convertCurrency(money, wallet.getMoney().getCurrency());
        wallet.deposit(moneyToDeposit);

        return walletRepository.save(wallet);
    }

    public Wallet withdrawMoney(TransactionRequest transactionRequest, Long userId, Long walletId) throws UserNotFoundException, AccessDeniedException {
        Wallet wallet = verifyUserWallet(userId, walletId);

        Money money = new Money(transactionRequest.getAmount(), transactionRequest.getCurrency());
        Money moneyToWithdraw = currencyService.convertCurrency(money, wallet.getMoney().getCurrency());
        wallet.withdraw(moneyToWithdraw);

        return walletRepository.save(wallet);
    }

    public Wallet verifyUserWallet(Long userId, Long walletId) throws AccessDeniedException {
        return walletRepository.findByIdAndUserId(walletId, userId)
                .orElseThrow(() -> new AccessDeniedException("Wallet does not belong to the user"));
    }

}