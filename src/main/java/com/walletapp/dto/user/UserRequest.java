package com.walletapp.dto.user;

public class UserRequest {
    private String username;
    private String password;
    private String currency;

    public UserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserRequest(String username, String password, String currency) {
        this.username = username;
        this.password = password;
        this.currency = currency;
    }

    public UserRequest(){}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCurrency() {
        return currency;
    }
}
