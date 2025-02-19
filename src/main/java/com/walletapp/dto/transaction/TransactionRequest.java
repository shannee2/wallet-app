package com.walletapp.dto.transaction;

import com.walletapp.model.transaction.TransactionType;

public class TransactionRequest {
    private double amount;
    private String currency;
    private TransactionType transactionType;
    private TransactionWalletDTO transactionWallet;

    public TransactionRequest() {}

    public TransactionRequest(double amount, String currency, TransactionType transactionType) {
        this.amount = amount;
        this.currency = currency;
        this.transactionType = transactionType;
        this.transactionWallet = null;
    }

    public TransactionRequest(double amount, String currency, TransactionType transactionType, TransactionWalletDTO transactionWallet) {
        this.amount = amount;
        this.currency = currency;
        this.transactionType = transactionType;
        this.transactionWallet = transactionWallet;
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public TransactionWalletDTO getTransactionWallet() {
        return transactionWallet;
    }
}
