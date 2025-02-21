package com.walletapp.service;

import com.walletapp.dto.user.UserRequest;
import com.walletapp.dto.user.UserResponse;
import com.walletapp.exceptions.users.UserNotFoundException;
import com.walletapp.exceptions.wallets.WalletNotFoundException;
import com.walletapp.model.user.User;
import com.walletapp.model.user.UserPrincipal;
import com.walletapp.model.wallet.Wallet;
import com.walletapp.model.money.Currency;
import com.walletapp.model.money.CurrencyType;
import com.walletapp.repository.CurrencyRepository;
import com.walletapp.repository.UserRepository;
import com.walletapp.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;


@Service
@Primary
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final WalletRepository walletRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Autowired
    public UserService(
            UserRepository userRepository,
            CurrencyRepository currencyRepository,
            WalletRepository walletRepository,
            @Lazy AuthenticationManager authManager,
            PasswordEncoder encoder,
            JWTService jwtService) {
        this.userRepository = userRepository;
        this.currencyRepository = currencyRepository;
        this.walletRepository = walletRepository;
        this.authManager = authManager;
        this.passwordEncoder = encoder;
        this.jwtService = jwtService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new UserPrincipal(user);
    }

    @Transactional
    public UserResponse registerUser(User user) {
        Currency currency = currencyRepository.findByType(CurrencyType.INR)
                .orElseThrow(() -> new IllegalArgumentException("Currency not found"));

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            ResponseEntity.badRequest().body("Username already taken");
            return null;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Wallet wallet = new Wallet(currency, user);
        userRepository.save(user);
        walletRepository.save(wallet);
        String token = jwtService.generateToken(user.getId());
        return new UserResponse(true, 201, "User Registered Successfully", token, user.getId());
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    private Wallet findWalletByUserId(Long userId){
        return walletRepository.findByUserId(userId)
                .orElseThrow(WalletNotFoundException::new);
    }

    public UserResponse verify(UserRequest userRequest) {
        String token;
        User user;
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequest.getUsername(),
                            userRequest.getPassword()
                    )
            );
            user = getUserByUsername(userRequest.getUsername());
            if (authentication.isAuthenticated()) {
                token = jwtService.generateToken(user.getId());
            } else {
                throw new AccessDeniedException("Authentication Failed");
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());
            throw new WalletNotFoundException();
        }
        return new UserResponse(true, 200, "Login Success", token, user.getId());
    }
}