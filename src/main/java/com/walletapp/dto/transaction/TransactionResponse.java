package com.walletapp.dto.transaction;

import com.walletapp.model.currency.CurrencyType;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionType;

import java.time.LocalDateTime;
import java.util.Date;

public class TransactionResponse {
    private final Long transactionId;
    private final Long walletId;
    private final Long userId;
    private final CurrencyType currencyType;
    private final double amount;
    private final Date date;
    private final TransactionType transactionType;
    private final String message;
    private Long targetUserId;
    private Long targetWalletId;

    public TransactionResponse(Long transactionId, Long walletId, Long userId, CurrencyType currencyType, double amount, Date date, TransactionType transactionType, String message) {
        this.transactionId = transactionId;
        this.walletId = walletId;
        this.userId = userId;
        this.currencyType = currencyType;
        this.amount = amount;
        this.date = date;
        this.transactionType = transactionType;
        this.message = message;
    }
    public TransactionResponse(Long transactionId, Long walletId, Long userId, CurrencyType currencyType, double amount, Date date, TransactionType transactionType, String message, Long targetUserId, Long targetWalletId) {
        this.transactionId = transactionId;
        this.walletId = walletId;
        this.userId = userId;
        this.currencyType = currencyType;
        this.amount = amount;
        this.date = date;
        this.transactionType = transactionType;
        this.message = message;
        this.targetUserId = targetUserId;
        this.targetWalletId = walletId;
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "transactionId=" + transactionId +
                ", walletId=" + walletId +
                ", userId=" + userId +
                ", currencyType=" + currencyType +
                ", amount=" + amount +
                ", date=" + date +
                ", transactionType=" + transactionType +
                ", message='" + message + '\'' +
                '}';
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public Long getUserId() {
        return userId;
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

    public String getMessage() {
        return message;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public Long getTargetWalletId() {
        return targetWalletId;
    }
}
