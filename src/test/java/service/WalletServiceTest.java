//package service;
//
//import com.walletapp.dto.transaction.TransactionRequest;
//import com.walletapp.exceptions.UserNotFoundException;
//import com.walletapp.model.transaction.Transaction;
//import com.walletapp.model.user.User;
//import com.walletapp.model.wallet.Wallet;
//import com.walletapp.model.currency.Currency;
//import com.walletapp.model.currency.CurrencyType;
//import com.walletapp.model.currency.Value;
//import com.walletapp.repository.CurrencyRepository;
//import com.walletapp.repository.UserRepository;
//import com.walletapp.repository.WalletRepository;
//import com.walletapp.service.WalletService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class WalletServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private CurrencyRepository currencyRepository;
//
//    @Mock
//    private WalletRepository walletRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private WalletService walletService;
//
//    private User user;
//    private Wallet wallet;
//
//    private Currency usd;
//    private Currency eur;
//    private Currency gbp;
//    private Currency inr;
//
//
//
//    @BeforeEach
//    void setUp() {
//        user = new User("testUser", "password");
//        wallet = new Wallet(new Currency(CurrencyType.INR, 1.0), user);
//
//        usd = new Currency(CurrencyType.USD, 1);
//        eur = new Currency(CurrencyType.EUR, 1);
//        gbp = new Currency(CurrencyType.GBP, 1);
//        inr = new Currency(CurrencyType.INR, 1);
//    }
//
//
//
//    @Test
//    void testDepositMoneyToWallet() throws UserNotFoundException {
//        TransactionRequest request = new TransactionRequest("testUser", 500.0, "INR", Transaction.TransactionType.DEPOSIT);
//
//        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
//        when(walletRepository.findByUserId(user.getId())).thenReturn(Optional.of(wallet));
//        when(currencyRepository.findByType(CurrencyType.INR)).thenReturn(Optional.of(new Currency(CurrencyType.INR, 1.0)));
//
//        walletService.depositMoneyToWallet(request, username);
//
//        verify(walletRepository).save(any(Wallet.class));
//        assertEquals(500.0, wallet.getBalance().getAmount());
//    }
//
//    @Test
//    public void testWithdrawMoneyFromWallet() throws UserNotFoundException {
//        TransactionRequest request = new TransactionRequest("testUser", 50.0, "INR", Transaction.TransactionType.WITHDRAW);
//        User user = new User();
//        user.setId(1L);
//        Wallet wallet = new Wallet(new Currency(CurrencyType.INR, 1.0), user);
//        wallet.depositMoney(new Value(100.0, wallet.getBalance().getCurrency()));
//
//        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
//        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(wallet));
//        when(currencyRepository.findByType(CurrencyType.INR)).thenReturn(Optional.of(new Currency(CurrencyType.INR, 1.0)));
//
//        walletService.withdrawMoneyFromWallet(request, username);
//
//        assertEquals(50.0, wallet.getBalance().getAmount());
//        verify(walletRepository).save(wallet);
//    }
//}