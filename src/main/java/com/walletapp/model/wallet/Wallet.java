package com.walletapp.model.wallet;

import com.walletapp.exceptions.wallets.InsufficientBalanceException;
import com.walletapp.exceptions.wallets.InvalidAmountException;
import com.walletapp.model.money.Currency;
import com.walletapp.model.money.CurrencyType;
import com.walletapp.model.money.Money;
import com.walletapp.model.user.User;
import jakarta.persistence.*;

@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Money money;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false, insertable = false, updatable = false)
    private Currency currency;

    public Wallet(User user) {
        Currency defaultCurrency = new Currency(CurrencyType.INR, 1.0);
        this.money = new Money(0.0, defaultCurrency);
        this.user = user;
    }

    public Wallet(Currency currency, User user) {
        this.currency = currency;
        this.money = new Money(0.0, currency);
        this.user = user;
    }

    public Wallet(Long id, Currency currency, User user) {
        this.currency = currency;
        this.money = new Money(0.0, currency);
        this.user = user;
        this.id = id;
    }

    public Wallet() {

    }
    public Wallet(Long id) {
        this.id = id;
    }

    public Wallet(Money money) {
        this.money = money;
    }


    public void deposit(Money money) {
        if(money.getAmount() <= 0){
            throw new InvalidAmountException();
        }

        double newAmount = this.money.getAmount() + money.getAmount();
        this.money = new Money(newAmount , this.money.getCurrency());
    }

    public void withdraw(Money money) {
        if(money.getAmount() > this.money.getAmount()){
            throw new InsufficientBalanceException();
        }
        if(money.getAmount() <= 0){
            throw new InvalidAmountException();
        }

        double newAmount = this.money.getAmount() - money.getAmount();
        this.money = new Money(newAmount , this.money.getCurrency());
    }

    public Money getMoney() {
        return this.money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public Long getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }
}