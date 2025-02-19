package com.walletapp.dto.transaction;

import com.walletapp.model.currency.CurrencyType;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionType;

import java.util.Date;
import java.util.List;

public class TransactionResponse {
    private final Long transactionId;
    private final CurrencyType currencyType;
    private final double amount;
    private final Date date;
    private final TransactionType transactionType;
    private final List<TransactionWalletDTO> transactionWallets;

    public TransactionResponse(Transaction transaction, List<TransactionWalletDTO> transactionWallets) {
        System.out.println(transactionWallets);
        this.transactionId = transaction.getId();
        this.currencyType = transaction.getCurrency().getType();
        this.amount = transaction.getAmount();
        this.date = transaction.getDate();
        this.transactionType = transaction.getType();
        this.transactionWallets = transactionWallets;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public List<TransactionWalletDTO> getTransactionWallets() {
        return transactionWallets;
    }
}
