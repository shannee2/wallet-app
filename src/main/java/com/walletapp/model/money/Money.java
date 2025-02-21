package com.walletapp.model.money;

import jakarta.persistence.*;


@Embeddable
public class Money {
    private double amount;

    @ManyToOne(optional = false)
    private Currency currency;

    public Money(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money() {
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}