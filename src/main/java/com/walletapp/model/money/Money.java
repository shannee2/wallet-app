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

    public Money convertTo(Currency targetCurrency) {
        if (this.currency.equals(targetCurrency)) {
            return this;
        }

        double baseAmount = this.amount / this.currency.getConversionFactor();
        double convertedAmount = baseAmount * targetCurrency.getConversionFactor();

        return new Money(convertedAmount, targetCurrency);
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}