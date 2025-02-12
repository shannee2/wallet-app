package com.walletapp.demo;

public class User {
    private String username;
    private String password;
    private final Wallet wallet;

    public User() {
        this.wallet = new Wallet();
    }

    public void register(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isRegistered(){
        return username!=null && password!=null;
    }

    public void depositMoneyToWallet(double amount) {
        this.wallet.depositMoney(amount);
    }
}
