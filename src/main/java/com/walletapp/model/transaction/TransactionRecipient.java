package com.walletapp.model.transaction;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.walletapp.model.wallet.Wallet;
import jakarta.persistence.*;

@Entity
@Table(name = "transaction_recipient")
public class TransactionRecipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    @JsonManagedReference
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;


    public TransactionRecipient() {}

    public TransactionRecipient(Transaction transaction, Wallet wallet) {
        this.transaction = transaction;
        this.wallet = wallet;
    }

    public Long getId() {
        return id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
