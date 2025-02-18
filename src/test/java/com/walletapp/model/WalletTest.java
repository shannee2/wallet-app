package com.walletapp.model;
import com.walletapp.exceptions.InsufficientBalanceException;
import com.walletapp.exceptions.InvalidAmountException;
import com.walletapp.model.currency.Currency;
import com.walletapp.model.currency.CurrencyType;
import com.walletapp.model.currency.Value;
import com.walletapp.model.wallet.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {
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
    public void testWalletCreation(){
        Wallet wallet = new Wallet();
        assertNotNull(wallet);
    }

    @Test
    public void testDepositMoney(){
        Wallet wallet = new Wallet(new Value(1, inr));
        Value value = new Value(2000, inr);
        assertDoesNotThrow(()->wallet.depositMoney(value));
    }

    @Test
    public void testWithdrawMoneyWhenAvailable(){
        Wallet wallet = new Wallet(new Value(1, inr));
        Value value = new Value(2000, inr);

        wallet.depositMoney(value);

        assertDoesNotThrow(()->wallet.withdrawMoney(new Value(100, inr)));
    }

    @Test
    public void testThrowException_WhenWithdrawingMoney_IfInsufficientBalance(){
        Wallet wallet = new Wallet(new Value(0, inr));
        Value value = new Value(200, inr);
        wallet.depositMoney(value);
        assertThrows(InsufficientBalanceException.class, ()->wallet.withdrawMoney(new Value(201, inr)));
    }

    @Test
    public void testThrowException_WhenDepositing0Amount(){
        Wallet wallet = new Wallet(new Value(1, inr));
        Value value = new Value(0, inr);
        assertThrows(InvalidAmountException.class, ()->wallet.depositMoney(value));
    }

    @Test
    public void testThrowException_WhenDepositingNegativeAmount(){
        Wallet wallet = new Wallet();

        Value value = new Value(-1, inr);

        assertThrows(InvalidAmountException.class, ()->wallet.depositMoney(value));
    }

    @Test
    public void testThrowException_WhenWithdrawing0Amount(){
        Wallet wallet = new Wallet(new Value(1, inr));
        Value value = new Value(100, inr);

        wallet.depositMoney(value);
        Value withdrawValue = new Value(0, inr);

        assertThrows(InvalidAmountException.class, ()->wallet.withdrawMoney(withdrawValue));
    }

    @Test
    public void testThrowException_WhenWithdrawingNegativeAmount(){
        Wallet wallet = new Wallet(new Value(1, inr));
        Value value = new Value(100, inr);

        wallet.depositMoney(value);
        Value withdrawValue = new Value(0, inr);

        assertThrows(InvalidAmountException.class, ()->wallet.withdrawMoney(withdrawValue));
    }
}
