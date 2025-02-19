package com.walletapp.model.transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.walletapp.model.currency.Currency;
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

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TransactionWallet> transactionWallets = new ArrayList<>();

    public Transaction() {
        this.date = new Date();
    }

    public Transaction(TransactionType type, double amount, Currency currency) {
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.date = new Date();
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

    public List<TransactionWallet> getTransactionRoles() {
        return transactionWallets;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

//    public void setTransactionRoles(List<TransactionRole> participants) {
//        this.transactionRoles = participants;
//    }
}
