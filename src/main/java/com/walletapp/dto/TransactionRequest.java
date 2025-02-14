package com.walletapp.dto;

public class TransactionRequest {
    private String username;
    private double amount;
    private String currency;

    public TransactionRequest(String username, double amount, String currency) {
        this.username = username;
        this.amount = amount;
        this.currency = currency;
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

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
