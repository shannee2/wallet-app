package com.walletapp.dto.transaction;

public class TransactionRequest {
    private final String username;
    private final double amount;
    private String currency;
    private TransactionType transactionType;

    public TransactionRequest(String username, double amount, String currency, TransactionType transactionType) {
        System.out.println("Transaction type is request constructor "+transactionType);
        this.username = username;
        this.amount = amount;
        this.currency = currency;
        this.transactionType = transactionType;
    }

    public String getUsername() {
        return username;
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
                "username='" + username + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", transactionType=" + transactionType +
                '}';
    }
}
