package handler;

import com.walletapp.handler.DepositWalletHandler;
import com.walletapp.handler.TransferWalletHandler;
import com.walletapp.handler.WalletHandlerRegistry;
import com.walletapp.handler.WithdrawWalletHandler;
import com.walletapp.model.transaction.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class WalletHandlerRegistryTest {
    private WalletHandlerRegistry registry;

    @Mock
    private DepositWalletHandler depositWalletHandler;

    @Mock
    private WithdrawWalletHandler withdrawWalletHandler;

    @Mock
    private TransferWalletHandler transferWalletHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(depositWalletHandler.getType()).thenReturn(TransactionType.DEPOSIT);
        when(withdrawWalletHandler.getType()).thenReturn(TransactionType.WITHDRAW);
        when(transferWalletHandler.getType()).thenReturn(TransactionType.TRANSFER);
        registry = new WalletHandlerRegistry(List.of(depositWalletHandler, withdrawWalletHandler, transferWalletHandler));
        registry.init();
    }

    @Test
    void testGetHandler() {
        assertEquals(depositWalletHandler, registry.getHandler(TransactionType.DEPOSIT));
        assertEquals(withdrawWalletHandler, registry.getHandler(TransactionType.WITHDRAW));
        assertEquals(transferWalletHandler, registry.getHandler(TransactionType.TRANSFER));
    }

    @Test
    void testGetHandlerWithInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> registry.getHandler(TransactionType.valueOf("INVALID")));
    }
}

