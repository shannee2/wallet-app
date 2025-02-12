package com.walletapp.demo;

public class User {
    private String username;
    private String password;
    private final Wallet wallet;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.wallet = new Wallet();
    }

    public void depositMoneyToWallet(double amount) {
        this.wallet.depositMoney(amount);
    }
}
