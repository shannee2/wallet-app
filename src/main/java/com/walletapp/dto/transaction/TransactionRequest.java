package com.walletapp.dto.transaction;

import com.walletapp.model.transaction.TransactionType;

public class TransactionRequest {
    private double amount;
    private String currency;
    private TransactionType transactionType;
    private Long receiverWalletId;

    public TransactionRequest() {}

    public TransactionRequest(double amount, String currency, TransactionType transactionType) {
        this.amount = amount;
        this.currency = currency;
        this.transactionType = transactionType;
        this.receiverWalletId = null;
    }

    public TransactionRequest(double amount, String currency, TransactionType transactionType, Long receiverWalletId) {
        this.amount = amount;
        this.currency = currency;
        this.transactionType = transactionType;
        this.receiverWalletId = receiverWalletId;
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Long getReceiverWalletId() {
        return receiverWalletId;
    }
}
