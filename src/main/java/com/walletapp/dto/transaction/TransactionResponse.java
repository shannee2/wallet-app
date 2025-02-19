package com.walletapp.dto.transaction;

import com.walletapp.model.currency.CurrencyType;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionType;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionResponse {
    private final Long transactionId;
    private final CurrencyType currencyType;
    private final double amount;
    private final Date date;
    private final TransactionType transactionType;
    private final List<TransactionParticipantDTO> participants;

    public TransactionResponse(Transaction transaction, List<TransactionParticipantDTO> participantResponses) {
        this.transactionId = transaction.getId();
        this.currencyType = transaction.getCurrency().getType();
        this.amount = transaction.getAmount();
        this.date = transaction.getDate();
        this.transactionType = transaction.getType();
        this.participants = participantResponses;
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

    public List<TransactionParticipantDTO> getParticipants() {
        return participants;
    }
}
