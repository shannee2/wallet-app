package com.walletapp.demo;

public class User {
    private boolean isRegistered;
    private String username;
    private String password;

    public void register(String username, String password) {
        this.username = username;
        this.password = password;
        this.isRegistered = true;
    }

    public boolean isRegistered(){
        return isRegistered;
    }
}
