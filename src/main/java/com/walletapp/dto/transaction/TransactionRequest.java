package com.walletapp.dto.transaction;

import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionParticipant;
import com.walletapp.model.transaction.TransactionType;
import com.walletapp.service.TransactionService;

import java.util.List;

public class TransactionRequest {
    private double amount;
    private String currency;
    private TransactionType transactionType;
    private TransactionParticipantDTO transactionParticipant;

    public TransactionRequest() {}

    public TransactionRequest(double amount, String currency, TransactionType transactionType) {
        this.amount = amount;
        this.currency = currency;
        this.transactionType = transactionType;
        this.transactionParticipant = null;
    }

    public TransactionRequest(double amount, String currency, TransactionType transactionType, TransactionParticipantDTO transactionParticipant) {
        this.amount = amount;
        this.currency = currency;
        this.transactionType = transactionType;
        this.transactionParticipant = transactionParticipant;
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

    public TransactionParticipantDTO getTransactionParticipant() {
        return transactionParticipant;
    }
}
