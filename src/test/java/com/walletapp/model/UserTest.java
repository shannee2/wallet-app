package com.walletapp.model;
import com.walletapp.exceptions.InsufficientBalanceException;
import com.walletapp.model.currency.Currency;
import com.walletapp.model.currency.CurrencyType;
import com.walletapp.model.currency.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserTest {

    private Currency usd;
    private Currency eur;
    private Currency gbp;
    private Currency inr;

    @BeforeEach
    public void setUp() {
        usd = new Currency(CurrencyType.USD, 1);
        eur = new Currency(CurrencyType.EUR, 1);
        gbp = new Currency(CurrencyType.GBP, 1);
        inr = new Currency(CurrencyType.INR, 1);
    }

    @Test
    public void TestUserCreation(){
        String username = "johndoe";
        String password = "Abc@123";

        User user = new User(username, password);

        assertNotNull(user);
    }

//    @Test
//    public void TestDepositMoneyIntoUserWallet(){
//        String username = "johndoe";
//        String password = "Abc@123";
//        User user = new User(username, password);
////        user.createWallet(new Wallet());
//
//        Value value = new Value(100, inr);
//
//        assertDoesNotThrow(()->user.depositMoney(value));
//    }
//
//    @Test
//    public void TestWithdrawMoneyFromUserWallet_IfSufficientBalance(){
//        String username = "johndoe";
//        String password = "Abc@123";
//        User user = new User(username, password);
//        user.createWallet(new Wallet());
//
//
//        Value value = new Value(100, inr);
//
//        user.depositMoney(value);
//        Value withdrawValue = new Value(100, inr);
//
//        assertDoesNotThrow(()->user.withdrawMoney(withdrawValue));
//    }

//    @Test
//    public void TestThrowException_WhenWithdrawMoneyFromUserWallet_IfSufficientBalance(){
//        String username = "johndoe";
//        String password = "Abc@123";
//        User user = new User(username, password);
//        user.createWallet(new Wallet());
//
//
//        Value value = new Value(100, inr);
//
//        user.depositMoney(value);
//
//        Value withdrawValue = new Value(920.1, inr);
//
//        assertThrows(InsufficientBalanceException.class, ()->user.withdrawMoney(withdrawValue));
//    }



//    @Test
//    public void TestDepositMoneyToWallet_IsCallingWalletMethod(){
//        String username = "johndoe";
//        String password = "Abc@123";
//        Wallet walletSpy = Mockito.spy(new Wallet());
//        User user = new User(username, password);
//        user.createWallet(walletSpy);
//
//        Value value = new Value(101, inr);
//        user.depositMoney(value);
//
//        verify(walletSpy, times(1)).depositMoney(value);
//    }

//    @Test
//    public void TestWithdrawMoneyFromWallet_IsCallingWalletMethod(){
//        String username = "johndoe";
//        String password = "Abc@123";
//        Wallet walletSpy = Mockito.spy(new Wallet());
//        User user = new User(username, password);
//        user.createWallet(walletSpy);
//
//        Value depositValue = new Value(101, inr);
//        Value withdrawValue = new Value(11, inr);
//
//
//        user.depositMoney(depositValue);
//        user.withdrawMoney(withdrawValue);
//
//        verify(walletSpy, times(1)).depositMoney(depositValue);
//        verify(walletSpy, times(1)).withdrawMoney(withdrawValue);
//    }
}
