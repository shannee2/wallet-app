package com.walletapp.model.transaction;

import java.util.Date;

import com.walletapp.model.currency.Currency;
import com.walletapp.model.user.User;
import com.walletapp.model.wallet.Wallet;
import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_wallet_id") // Can be nullable if not a transfer
    private Wallet targetWallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id") // Can be nullable if not a transfer
    private User targetUser;

    public Transaction() {
        this.date = new Date();
    }

    public Transaction(Wallet wallet, User user, TransactionType type, double amount, Currency currency) {
        this.wallet = wallet;
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.date = new Date();
    }

    public Transaction(Wallet wallet, User user, TransactionType type, double amount, Currency currency, User targetUser, Wallet targetWallet) {
        this.wallet = wallet;
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.targetUser = targetUser;
        this.targetWallet = targetWallet;
        this.date = new Date();
    }

    public Long getId() {
        return id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public User getUser() {
        return user;
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

    public Wallet getTargetWallet() {
        return targetWallet;
    }

    public User getTargetUser() {
        return targetUser;
    }
}

