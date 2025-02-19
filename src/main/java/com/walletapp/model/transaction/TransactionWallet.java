package com.walletapp.model.transaction;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.walletapp.model.wallet.Wallet;
import jakarta.persistence.*;

@Entity
@Table(name = "transaction_wallet")
public class TransactionWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    @JsonManagedReference
    private Transaction transaction;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionWalletType transactionWalletType;

    public TransactionWallet() {}

    public TransactionWallet(Transaction transaction, Wallet wallet, TransactionWalletType transactionWalletType) {
        this.transaction = transaction;
//        this.user = user;
        this.wallet = wallet;
        this.transactionWalletType = transactionWalletType;
    }

    public Long getId() {
        return id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

//    public User getUser() {
//        return user;
//    }

    public Wallet getWallet() {
        return wallet;
    }

    public TransactionWalletType getTransactionWalletType() {
        return transactionWalletType;
    }
}
