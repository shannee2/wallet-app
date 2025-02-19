package com.walletapp.repository;

import com.walletapp.model.transaction.TransactionWallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionWalletRepository extends JpaRepository<TransactionWallet, Long> {

//    List<TransactionWallet> findByUser(User user);

//    @Query("SELECT DISTINCT t FROM Transaction t " +
//            "JOIN t.participants tp " +
//            "WHERE t.id IN (SELECT tp2.transaction.id FROM TransactionParticipant tp2 WHERE tp2.user.id = :userId) " +
//            "AND (tp.role IN ('SENDER', 'RECEIVER') OR t.type IN ('DEPOSIT', 'TRANSFER', 'WITHDRAW'))")
//    List<Transaction> findUserTransactions(@Param("userId") Long userId);


//    List<TransactionParticipant> findByTransaction(Transaction transaction);

//    List<TransactionParticipant> findByWallet(Wallet wallet); // This is still useful
//    List<TransactionParticipant> findByTransaction(Transaction transaction);
//    List<TransactionParticipant> findByUser(User user); // For getTransactions
//List<TransactionParticipant> findByTransactionIds(@Param("transactionIds") Set<Long> transactionIds);

}
