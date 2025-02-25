package com.walletapp.dto.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionType;
import com.walletapp.model.transaction.TransactionRecipient;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {
    private final Long transactionId;
    private final String currencyType;
    private final double amount;
    private final Date date;
    private final TransactionType transactionType;
    private final Long senderWalletId;
    private final Long receiverWalletId;
    private final Long walletId;

    public TransactionResponse(Transaction transaction, TransactionRecipient recipient) {
        this.transactionId = transaction.getId();
        this.currencyType = transaction.getCurrency() != null ? String.valueOf(transaction.getCurrency()) : null;
        this.amount = transaction.getAmount();
        this.date = transaction.getDate();
        this.transactionType = transaction.getType();


        // Assigning wallet IDs based on transaction type
        if (transaction.getType() == TransactionType.TRANSFER) {
            this.senderWalletId = transaction.getWallet().getId();
            this.receiverWalletId = recipient.getWallet().getId();
            this.walletId = null;
        } else { // DEPOSIT or WITHDRAWAL
            this.walletId = transaction.getWallet().getId();
            this.senderWalletId = null;
            this.receiverWalletId = null;
        }
    }

    // Getters
    public Long getTransactionId() { return transactionId; }
    public String getCurrencyType() { return currencyType; }
    public double getAmount() { return amount; }
    public Date getDate() { return date; }
    public TransactionType getTransactionType() { return transactionType; }
    public Long getSenderWalletId() { return senderWalletId; }
    public Long getReceiverWalletId() { return receiverWalletId; }
    public Long getWalletId() { return walletId; }
}
