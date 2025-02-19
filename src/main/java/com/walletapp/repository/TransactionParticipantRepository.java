package com.walletapp.repository;

import com.walletapp.model.transaction.TransactionParticipant;
import com.walletapp.model.transaction.ParticipantRole;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionParticipantRepository extends JpaRepository<TransactionParticipant, Long> {

    List<TransactionParticipant> findByUser(User user);

    List<TransactionParticipant> findByTransaction(Transaction transaction);
}
