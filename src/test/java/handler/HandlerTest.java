package handler;

import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.exceptions.users.UserNotFoundException;
import com.walletapp.handler.*;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionType;
import com.walletapp.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HandlerTest {

    @Mock
    private WalletService walletService;

    @InjectMocks
    private DepositWalletHandler depositWalletHandler;

    @InjectMocks
    private WithdrawWalletHandler withdrawWalletHandler;

    @InjectMocks
    private TransferWalletHandler transferWalletHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDepositHandler() throws UserNotFoundException, AccessDeniedException {
        TransactionRequest request = mock(TransactionRequest.class);
        Transaction transaction = mock(Transaction.class);
        Long userId = 1L;
        Long walletId = 100L;

        depositWalletHandler.handle(request, userId, walletId, transaction);

        verify(walletService).depositMoney(request, userId, walletId);
        assertEquals(TransactionType.DEPOSIT, depositWalletHandler.getType());
    }

    @Test
    void testWithdrawHandler() throws UserNotFoundException, AccessDeniedException {
        TransactionRequest request = mock(TransactionRequest.class);
        Transaction transaction = mock(Transaction.class);
        Long userId = 1L;
        Long walletId = 100L;

        withdrawWalletHandler.handle(request, userId, walletId, transaction);

        verify(walletService).withdrawMoney(request, userId, walletId);
        assertEquals(TransactionType.WITHDRAW, withdrawWalletHandler.getType());
    }

    @Test
    void testTransferHandler() throws UserNotFoundException, AccessDeniedException {
        TransactionRequest request = mock(TransactionRequest.class);
        Transaction transaction = mock(Transaction.class);
        Long userId = 1L;
        Long senderWalletId = 100L;
        Long receiverWalletId = 200L;

        when(request.getReceiverWalletId()).thenReturn(receiverWalletId);

        transferWalletHandler.handle(request, userId, senderWalletId, transaction);

        verify(walletService).withdrawMoney(request, userId, senderWalletId);
        verify(walletService).depositMoney(request, receiverWalletId);
        assertEquals(TransactionType.TRANSFER, transferWalletHandler.getType());
    }
}
