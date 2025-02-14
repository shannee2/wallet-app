package service;

import com.walletapp.dto.TransactionRequest;
import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.model.User;
import com.walletapp.model.Wallet;
import com.walletapp.model.currency.Currency;
import com.walletapp.model.currency.CurrencyType;
import com.walletapp.model.currency.Value;
import com.walletapp.repository.CurrencyRepository;
import com.walletapp.repository.UserRepository;
import com.walletapp.repository.WalletRepository;
import com.walletapp.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private Wallet wallet;

    private Currency usd;
    private Currency eur;
    private Currency gbp;
    private Currency inr;



    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);

        user = new User("testUser", "password");
        wallet = new Wallet(new Currency(CurrencyType.INR, 1.0), user);

        usd = new Currency(CurrencyType.USD, 1);
        eur = new Currency(CurrencyType.EUR, 1);
        gbp = new Currency(CurrencyType.GBP, 1);
        inr = new Currency(CurrencyType.INR, 1);
    }

    @Test
    void testRegisterUser() {
        when(currencyRepository.findByType(CurrencyType.INR)).thenReturn(Optional.of(new Currency(CurrencyType.INR, 1.0)));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        userService.registerUser(user);

        verify(userRepository).save(any(User.class));
        verify(walletRepository).save(any(Wallet.class));
    }

    @Test
    void testGetUser() throws UserNotFoundException {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        User foundUser = userService.getUser("testUser");

        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    void testDepositMoneyToWallet() throws UserNotFoundException {
        TransactionRequest request = new TransactionRequest("testUser", 500.0, "INR");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(walletRepository.findByUserId(user.getId())).thenReturn(Optional.of(wallet));
        when(currencyRepository.findByType(CurrencyType.INR)).thenReturn(Optional.of(new Currency(CurrencyType.INR, 1.0)));

        userService.depositMoneyToWallet(request);

        verify(walletRepository).save(any(Wallet.class));
        assertEquals(500.0, wallet.getBalance().getAmount());
    }

    @Test
    public void testWithdrawMoneyFromWallet() throws UserNotFoundException {
        TransactionRequest request = new TransactionRequest("testUser", 50.0, "INR");
        User user = new User();
        user.setId(1L);
        Wallet wallet = new Wallet(new Currency(CurrencyType.INR, 1.0), user);
        wallet.depositMoney(new Value(100.0, wallet.getBalance().getCurrency()));

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(wallet));
        when(currencyRepository.findByType(CurrencyType.INR)).thenReturn(Optional.of(new Currency(CurrencyType.INR, 1.0)));

        userService.withdrawMoneyFromWallet(request);

        assertEquals(50.0, wallet.getBalance().getAmount());
        verify(walletRepository).save(wallet);
    }
}