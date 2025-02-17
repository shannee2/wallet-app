package com.walletapp.model.currency;

import jakarta.persistence.*;


@Embeddable
public class Value {
    private double amount;

    @ManyToOne(optional = false)
    private Currency currency;

    public Value(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Value() {
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}