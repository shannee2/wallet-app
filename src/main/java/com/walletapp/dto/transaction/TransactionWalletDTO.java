package com.walletapp.dto.transaction;

import com.walletapp.model.transaction.TransactionWalletType;

public class TransactionWalletDTO {
    private Long walletId;
    private TransactionWalletType transactionWalletType;

    public TransactionWalletDTO(Long walletId, TransactionWalletType transactionWalletType) {
        this.walletId = walletId;
        this.transactionWalletType = transactionWalletType;
    }

    public TransactionWalletDTO(){}

    public Long getWalletId() {
        return walletId;
    }

    public TransactionWalletType getTransactionWalletType() {
        return transactionWalletType;
    }
}
