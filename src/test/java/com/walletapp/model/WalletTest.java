package com.walletapp.model;
import com.walletapp.exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {
    @Test
    public void testWalletCreation(){
        Wallet wallet = new Wallet();
        assertNotNull(wallet);
    }

    @Test
    public void testDepositMoney(){
        Wallet wallet = new Wallet();
        assertDoesNotThrow(()->wallet.depositMoney(2000));
    }

    @Test
    public void testWithdrawMoneyWhenAvailable(){
        Wallet wallet = new Wallet();
        wallet.depositMoney(2000);
        assertDoesNotThrow(()->wallet.withdrawMoney(100));
    }

    @Test
    public void testThrowException_WhenWithdrawingMoney_IfInsufficientBalance(){
        Wallet wallet = new Wallet();
        wallet.depositMoney(200);
        assertThrows(InsufficientBalanceException.class, ()->wallet.withdrawMoney(300));
    }
}
