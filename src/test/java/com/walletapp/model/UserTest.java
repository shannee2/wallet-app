package com.walletapp.model;
import com.walletapp.exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserTest {
    @Test
    public void TestUserCreation(){
        String username = "johndoe";
        String password = "Abc@123";

        User user = new User(username, password);

        assertNotNull(user);
    }

    @Test
    public void TestUserCreationWithWallet(){
        String username = "johndoe";
        String password = "Abc@123";
        Wallet wallet = new Wallet();

        User user = new User(username, password, wallet);

        assertNotNull(user);
    }

    @Test
    public void TestDepositMoneyIntoUserWallet(){
        String username = "johndoe";
        String password = "Abc@123";
        User user = new User(username, password);

        assertDoesNotThrow(()->user.depositMoneyToWallet(100.45));
    }

    @Test
    public void TestWithdrawMoneyFromUserWallet_IfSufficientBalance(){
        String username = "johndoe";
        String password = "Abc@123";
        User user = new User(username, password);

        user.depositMoneyToWallet(100.45);

        assertDoesNotThrow(()->user.withdrawMoneyFromWallet(10.45));
    }

    @Test
    public void TestThrowException_WhenWithdrawMoneyFromUserWallet_IfSufficientBalance(){
        String username = "johndoe";
        String password = "Abc@123";
        User user = new User(username, password);

        user.depositMoneyToWallet(100.45);

        assertThrows(InsufficientBalanceException.class, ()->user.withdrawMoneyFromWallet(110.45));
    }

    @Test
    public void TestDepositMoneyToWallet_IsCallingWalletMethod(){
        String username = "johndoe";
        String password = "Abc@123";
        Wallet walletSpy = Mockito.spy(new Wallet());
        User user = new User(username, password, walletSpy);

        user.depositMoneyToWallet(100.45);

        verify(walletSpy, times(1)).depositMoney(100.45);
    }

    @Test
    public void TestWithdrawMoneyFromWallet_IsCallingWalletMethod(){
        String username = "johndoe";
        String password = "Abc@123";
        Wallet walletSpy = Mockito.spy(new Wallet());
        User user = new User(username, password, walletSpy);

        user.depositMoneyToWallet(100);
        user.withdrawMoneyFromWallet(20);

        verify(walletSpy, times(1)).depositMoney(100);
        verify(walletSpy, times(1)).withdrawMoney(20);
    }
}
