package service;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionResponse;
import com.walletapp.exceptions.users.UserNotFoundException;
import com.walletapp.exceptions.wallets.WalletNotFoundException;
import com.walletapp.handler.WalletHandler;
import com.walletapp.handler.WalletHandlerRegistry;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionRecipient;
import com.walletapp.model.transaction.TransactionType;
import com.walletapp.model.wallet.Wallet;
import com.walletapp.repository.*;
import com.walletapp.service.*;
import com.walletapp.service.TransactionService;
import com.walletapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.nio.file.AccessDeniedException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionRecipientRepository transactionRecipientRepository;

    @Mock
    private UserService userService;

    @Mock
    private WalletService walletService;

    @Mock
    private CurrencyService currencyService;

    @Mock
    private WalletHandlerRegistry walletHandlerRegistry;

    @Mock
    private WalletHandler walletHandler;

    @Mock
    private Wallet wallet;

    private static final Long USER_ID = 1L;
    private static final Long WALLET_ID = 1L;
    private static final Long RECEIVER_WALLET_ID = 2L;

    @BeforeEach
    void setUp() throws AccessDeniedException {
//        when(walletService.verifyUserWallet(USER_ID, WALLET_ID)).thenReturn(wallet);
//        when(walletHandlerRegistry.getHandler(any(TransactionType.class))).thenReturn(walletHandler);
    }



    @Test
    void testCreateDepositTransaction() throws UserNotFoundException, WalletNotFoundException, AccessDeniedException {
        when(walletService.verifyUserWallet(USER_ID, WALLET_ID)).thenReturn(wallet);
        when(walletHandlerRegistry.getHandler(any(TransactionType.class))).thenReturn(walletHandler);

        TransactionRequest request = new TransactionRequest(200.0, "USD", TransactionType.DEPOSIT);
        Transaction transaction = new Transaction(TransactionType.DEPOSIT, 200.0, null, wallet);

        when(currencyService.getCurrency("USD")).thenReturn(null);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionResponse response = transactionService.createTransaction(request, USER_ID, WALLET_ID);

        assertThat(response).isNotNull();
        assertThat(response.getTransactionType()).isEqualTo(TransactionType.DEPOSIT);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void testCreateWithdrawTransaction() throws UserNotFoundException, WalletNotFoundException, AccessDeniedException {
        when(walletService.verifyUserWallet(USER_ID, WALLET_ID)).thenReturn(wallet);
        when(walletHandlerRegistry.getHandler(any(TransactionType.class))).thenReturn(walletHandler);

        TransactionRequest request = new TransactionRequest(100.0, "USD", TransactionType.WITHDRAW);
        Transaction transaction = new Transaction(TransactionType.WITHDRAW, 100.0, null, wallet);

        when(currencyService.getCurrency("USD")).thenReturn(null);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionResponse response = transactionService.createTransaction(request, USER_ID, WALLET_ID);

        assertThat(response).isNotNull();
        assertThat(response.getTransactionType()).isEqualTo(TransactionType.WITHDRAW);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void testCreateTransferTransaction() throws UserNotFoundException, WalletNotFoundException, AccessDeniedException {
        when(walletService.verifyUserWallet(USER_ID, WALLET_ID)).thenReturn(wallet);
        when(walletHandlerRegistry.getHandler(any(TransactionType.class))).thenReturn(walletHandler);

        TransactionRequest request = new TransactionRequest(150.0, "USD", TransactionType.TRANSFER, RECEIVER_WALLET_ID);
        Transaction transaction = new Transaction(TransactionType.TRANSFER, 150.0, null, wallet);
        Wallet recipientWallet = mock(Wallet.class);
        TransactionRecipient recipient = new TransactionRecipient(transaction, recipientWallet);

        when(currencyService.getCurrency("USD")).thenReturn(null);
        when(walletService.findWalletById(RECEIVER_WALLET_ID)).thenReturn(recipientWallet);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionRecipientRepository.save(any(TransactionRecipient.class))).thenReturn(recipient);

        TransactionResponse response = transactionService.createTransaction(request, USER_ID, WALLET_ID);

        assertThat(response).isNotNull();
        assertThat(response.getTransactionType()).isEqualTo(TransactionType.TRANSFER);
        verify(transactionRecipientRepository).save(any(TransactionRecipient.class));
    }


    @Test
    void testGetTransactions() throws UserNotFoundException, AccessDeniedException {
        Transaction transaction = new Transaction(TransactionType.DEPOSIT, 100.0, null, wallet);
        when(transactionRepository.findByWalletId(WALLET_ID)).thenReturn(List.of(transaction));

        List<TransactionResponse> responses = transactionService.getTransactions(USER_ID, WALLET_ID);

        assertThat(responses).isNotEmpty();
        assertThat(responses.getFirst().getTransactionType()).isEqualTo(TransactionType.DEPOSIT);
        verify(transactionRepository).findByWalletId(WALLET_ID);
    }
}
