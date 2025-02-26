package com.walletapp.dto.wallet;

public class WalletResponse {
    private Long walletId;

    public WalletResponse(Long walletId) {
        this.walletId = walletId;
    }

    public WalletResponse() {
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }
}
