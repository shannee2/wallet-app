package com.walletapp.model.wallet;

import com.walletapp.exceptions.wallets.InsufficientBalanceException;
import com.walletapp.exceptions.wallets.InvalidAmountException;
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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



    public Wallet(User user) {
        String defaultCurrency = "INR";
        this.money = new Money(0.0, defaultCurrency);
        this.user = user;
    }

    public Wallet(String currency, User user) {
        this.money = new Money(0.0, currency);
        this.user = user;
    }

    public Wallet(Long id, String currency, User user) {
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