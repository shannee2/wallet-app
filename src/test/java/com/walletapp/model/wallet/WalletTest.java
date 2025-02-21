package com.walletapp.model.wallet;
import com.walletapp.exceptions.wallets.InsufficientBalanceException;
import com.walletapp.exceptions.wallets.InvalidAmountException;
import com.walletapp.model.money.Currency;
import com.walletapp.model.money.CurrencyType;
import com.walletapp.model.money.Money;
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
    public void testDeposit(){
        Wallet wallet = new Wallet(new Money(1, inr));
        Money money = new Money(2000, inr);
        assertDoesNotThrow(()->wallet.deposit(money));
    }

    @Test
    public void testWithdrawWhenAvailable(){
        Wallet wallet = new Wallet(new Money(1, inr));
        Money money = new Money(2000, inr);

        wallet.deposit(money);

        assertDoesNotThrow(()->wallet.withdraw(new Money(100, inr)));
    }

    @Test
    public void testThrowException_WhenWithdrawingMoney_IfInsufficientBalance(){
        Wallet wallet = new Wallet(new Money(0, inr));
        Money money = new Money(200, inr);
        wallet.deposit(money);
        assertThrows(InsufficientBalanceException.class, ()->wallet.withdraw(new Money(201, inr)));
    }

    @Test
    public void testThrowException_WhenDepositing0Amount(){
        Wallet wallet = new Wallet(new Money(1, inr));
        Money money = new Money(0, inr);
        assertThrows(InvalidAmountException.class, ()->wallet.deposit(money));
    }

    @Test
    public void testThrowException_WhenDepositingNegativeAmount(){
        Wallet wallet = new Wallet();

        Money money = new Money(-1, inr);

        assertThrows(InvalidAmountException.class, ()->wallet.deposit(money));
    }

    @Test
    public void testThrowException_WhenWithdrawing0Amount(){
        Wallet wallet = new Wallet(new Money(1, inr));
        Money money = new Money(100, inr);

        wallet.deposit(money);
        Money withdrawMoney = new Money(0, inr);

        assertThrows(InvalidAmountException.class, ()->wallet.withdraw(withdrawMoney));
    }

    @Test
    public void testThrowException_WhenWithdrawingNegativeAmount(){
        Wallet wallet = new Wallet(new Money(1, inr));
        Money money = new Money(100, inr);

        wallet.deposit(money);
        Money withdrawMoney = new Money(0, inr);

        assertThrows(InvalidAmountException.class, ()->wallet.withdraw(withdrawMoney));
    }
}
