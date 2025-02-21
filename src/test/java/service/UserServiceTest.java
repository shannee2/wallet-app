package service;

import com.walletapp.dto.user.UserRequest;
import com.walletapp.dto.user.UserResponse;
import com.walletapp.exceptions.users.UserNotFoundException;
import com.walletapp.model.money.Currency;
import com.walletapp.model.money.CurrencyType;
import com.walletapp.model.user.User;
import com.walletapp.model.wallet.Wallet;
import com.walletapp.repository.CurrencyRepository;
import com.walletapp.repository.UserRepository;
import com.walletapp.repository.WalletRepository;
import com.walletapp.service.JWTService;
import com.walletapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

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

    @InjectMocks
    private UserService userService;

    private User user;
    private Currency currency;
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        user = new User(1L, "testUser","password123");
        currency = new Currency(CurrencyType.INR, 1.0);
        wallet = new Wallet(currency, user);
    }

    @Test
    void testRegisterUser_Success() {
        when(currencyRepository.findByType(CurrencyType.INR)).thenReturn(Optional.of(currency));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(jwtService.generateToken(user.getId())).thenReturn("jwtToken");

        UserResponse response = userService.registerUser(user);

        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("User Registered Successfully");
        assertThat(response.getToken()).isEqualTo("jwtToken");

        verify(userRepository).save(user);
        verify(walletRepository).save(any(Wallet.class));
    }

    @Test
    void testRegisterUser_CurrencyNotFound() {
        when(currencyRepository.findByType(CurrencyType.INR)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));

        verify(userRepository, never()).save(any(User.class));
        verify(walletRepository, never()).save(any(Wallet.class));
    }

    @Test
    void testGetUserByUsername_Success() throws UserNotFoundException {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        User foundUser = userService.getUserByUsername("testUser");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("testUser");
    }

    @Test
    void testGetUserByUsername_NotFound() {
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername("unknownUser"));
    }

    @Test
    void testGetUserById_Success() throws UserNotFoundException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(99L));
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.getAllUsers();

        assertThat(users).hasSize(1);
        assertThat(users.getFirst().getUsername()).isEqualTo("testUser");
    }

    @Test
    void testVerify_Success() throws UserNotFoundException, AccessDeniedException {
        UserRequest request = new UserRequest("testUser", "password123");

        Authentication mockedAuthentication = mock(Authentication.class);
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockedAuthentication);
        when(mockedAuthentication.isAuthenticated()).thenReturn(true);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user.getId())).thenReturn("jwtToken");

        UserResponse response = userService.verify(request);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwtToken");
        assertThat(response.getUserId()).isEqualTo(user.getId());
    }
}
