package com.walletapp.model.transaction;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.walletapp.model.user.User;
import com.walletapp.model.wallet.Wallet;
import jakarta.persistence.*;

@Entity
@Table(name = "transaction_participants")
public class TransactionParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    @JsonManagedReference
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParticipantRole role;

    public TransactionParticipant() {}

    public TransactionParticipant(Transaction transaction, User user, Wallet wallet, ParticipantRole role) {
        this.transaction = transaction;
        this.user = user;
        this.wallet = wallet;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public User getUser() {
        return user;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public ParticipantRole getRole() {
        return role;
    }
}
