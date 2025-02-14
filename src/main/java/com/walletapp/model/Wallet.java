package com.walletapp.model;

import com.walletapp.dto.TransactionRequest;
import com.walletapp.exceptions.InsufficientBalanceException;
import com.walletapp.exceptions.InvalidAmountException;
import com.walletapp.model.currency.Currency;
import com.walletapp.model.currency.CurrencyType;
import com.walletapp.model.currency.Value;
import jakarta.persistence.*;

@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Value balance;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false, insertable = false, updatable = false)
    private Currency currency;

    public Wallet(User user) {
        Currency defaultCurrency = new Currency(CurrencyType.INR, 1.0);
        this.balance = new Value(0.0, defaultCurrency);
        this.user = user;
    }

    public Wallet(Currency currency, User user) {
        this.currency = currency;
        this.balance = new Value(0.0, currency);
        this.user = user;
    }

    public Wallet() {

    }

    public Wallet(Value balance) {
        this.balance = balance;
    }


    public void depositMoney(Value value) {
        if(value.getAmount() <= 0){
            throw new InvalidAmountException();
        }

        double newAmount = this.balance.getAmount() + value.getAmount();
        this.balance = new Value(newAmount , this.balance.getCurrency());
    }

    public void withdrawMoney(Value value) {
        if(value.getAmount() > this.balance.getAmount()){
            throw new InsufficientBalanceException();
        }
        if(value.getAmount() <= 0){
            throw new InvalidAmountException();
        }

        double newAmount = this.balance.getAmount() - value.getAmount();
        this.balance = new Value(newAmount , this.balance.getCurrency());
    }

    public void convertCurrency(Currency newCurrency) {
//        this.balance = this.balance.convertTo(newCurrency);
    }

    public Value getBalance() {
        return this.balance;
    }
}