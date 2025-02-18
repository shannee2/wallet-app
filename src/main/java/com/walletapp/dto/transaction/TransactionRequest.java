package com.walletapp.dto.transaction;

import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionType;

public class TransactionRequest {
    private final double amount;
    private String currency;
    private TransactionType transactionType;

    public TransactionRequest(double amount, String currency, TransactionType transactionType) {
        this.amount = amount;
        this.currency = currency;
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public TransactionType getType() {
        return this.transactionType;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", transactionType=" + transactionType +
                '}';
    }
}
