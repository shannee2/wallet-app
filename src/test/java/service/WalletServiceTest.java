package service;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.exceptions.users.UserNotFoundException;
import com.walletapp.exceptions.wallets.WalletNotFoundException;
import com.walletapp.model.money.Currency;
import com.walletapp.model.money.CurrencyType;
import com.walletapp.model.money.Money;
import com.walletapp.model.transaction.TransactionType;
import com.walletapp.model.user.User;
import com.walletapp.model.wallet.Wallet;
import com.walletapp.repository.CurrencyRepository;
import com.walletapp.repository.UserRepository;
import com.walletapp.repository.WalletRepository;
import com.walletapp.service.CurrencyService;
import com.walletapp.service.JWTService;
import com.walletapp.service.UserService;
import com.walletapp.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private AuthenticationManager authManager;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JWTService jwtService;
    @Mock
    private CurrencyService currencyService;
    @Mock
    private UserService userService;

    @InjectMocks
    private WalletService walletService;

    private User user;
    private Wallet wallet;
    private Currency currency;

    @BeforeEach
    void setUp() {
        user = new User(1L,"testUser","password123");
        currency = new Currency(CurrencyType.INR, 1.0);
        wallet = new Wallet(1L, currency, user);
    }

    @Test
    void testFindWalletById_Success() {
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));

        Wallet foundWallet = walletService.findWalletById(1L);

        assertThat(foundWallet).isNotNull();
        assertThat(foundWallet.getId()).isEqualTo(1L);
    }

    @Test
    void testFindWalletById_NotFound() {
        when(walletRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(WalletNotFoundException.class, () -> walletService.findWalletById(99L));
    }

    @Test
    void testDepositMoney_Success() throws UserNotFoundException, AccessDeniedException {
        TransactionRequest request = new TransactionRequest(100.0, "INR", TransactionType.DEPOSIT);

        when(walletRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(wallet));
        when(currencyService.getCurrency("INR")).thenReturn(currency);
        when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet updatedWallet = walletService.depositMoney(request, 1L, 1L);

        assertThat(updatedWallet).isNotNull();
        verify(walletRepository).save(wallet);
    }

    @Test
    void testDepositMoney_WalletNotFound() {
        TransactionRequest request = new TransactionRequest(100.0, "INR", TransactionType.DEPOSIT);
        when(walletRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(WalletNotFoundException.class, () -> walletService.depositMoney(request, 1L));
    }

    @Test
    void testDepositMoney_UserWalletVerificationFails() {
        TransactionRequest request = new TransactionRequest(100.0, "INR", TransactionType.DEPOSIT);
        when(walletRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(AccessDeniedException.class, () -> walletService.depositMoney(request, 1L, 1L));
    }

    @Test
    void testWithdrawMoney_Success() throws UserNotFoundException, AccessDeniedException {
        wallet.setMoney(new Money(1000, currency));
        TransactionRequest request = new TransactionRequest(100.0, "INR", TransactionType.WITHDRAW);
        when(walletRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(wallet));
        when(currencyService.getCurrency("INR")).thenReturn(currency);
        when(walletRepository.save(wallet)).thenReturn(wallet);

        Wallet updatedWallet = walletService.withdrawMoney(request, 1L, 1L);

        assertThat(updatedWallet).isNotNull();
        verify(walletRepository).save(wallet);
    }

    @Test
    void testWithdrawMoney_UserWalletVerificationFails() {
        TransactionRequest request = new TransactionRequest(100.0, "INR", TransactionType.WITHDRAW);
        when(walletRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(AccessDeniedException.class, () -> walletService.withdrawMoney(request, 1L, 1L));
    }

    @Test
    void testVerifyUserWallet_Success() throws AccessDeniedException {
        when(walletRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(wallet));

        Wallet verifiedWallet = walletService.verifyUserWallet(1L, 1L);

        assertThat(verifiedWallet).isNotNull();
        assertThat(verifiedWallet.getUser()).isEqualTo(user);
    }

    @Test
    void testVerifyUserWallet_AccessDenied() {
        when(walletRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(AccessDeniedException.class, () -> walletService.verifyUserWallet(1L, 1L));
    }
}
