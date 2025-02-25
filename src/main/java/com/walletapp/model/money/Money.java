package com.walletapp.model.money;

import jakarta.persistence.*;


@Embeddable
public class Money {
    private double amount;
    private String currency;

    public Money(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money() {
    }


    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}