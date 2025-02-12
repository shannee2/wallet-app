package com.walletapp.model;

public class User {
    private String username;
    private String password;
    private final Wallet wallet;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.wallet = new Wallet();
    }

    public User(String username, String password, Wallet wallet) {
        this.username = username;
        this.password = password;
        this.wallet = wallet;
    }

    public void depositMoneyToWallet(double amount) {
        this.wallet.depositMoney(amount);
    }

    public void withdrawMoneyFromWallet(double amount) {
        this.wallet.withdrawMoney(amount);
    }
}
