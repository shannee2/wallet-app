package com.walletapp.dto.wallet;

public class WalletRequest {
    private String currency;

    public WalletRequest(String currency) {
        this.currency = currency;
    }
    public WalletRequest() {}

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
