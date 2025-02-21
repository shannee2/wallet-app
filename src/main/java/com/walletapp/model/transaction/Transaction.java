package com.walletapp.model.transaction;

import java.util.Date;

import com.walletapp.model.money.Currency;
import com.walletapp.model.wallet.Wallet;
import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private double amount;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private TransactionRecipient recipient;

    public Transaction() {
        this.date = new Date();
    }

    public Transaction(TransactionType type, double amount, Currency currency, Wallet wallet) {
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.date = new Date();
        this.wallet = wallet;
    }
    public Transaction(Long id, TransactionType type, double amount, Currency currency, Wallet wallet) {
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.date = new Date();
        this.wallet = wallet;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setRecipient(TransactionRecipient recipient) {
        this.recipient = recipient;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public TransactionRecipient getRecipient() {
        return recipient;
    }
}
