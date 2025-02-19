package com.walletapp.repository;

import com.walletapp.model.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    List<Transaction> findByWallet(Wallet wallet);

    @Query(value = """
        SELECT DISTINCT t.*
        FROM Transaction t
        JOIN Transaction_Wallet tw ON t.id = tw.transaction_id
        WHERE t.id IN (
            SELECT transaction_id
            FROM Transaction_Wallet
            WHERE wallet_id = :walletId
        )
        AND (tw.Transaction_Wallet_Type IN ('SENDER', 'RECEIVER') OR t.type IN ('DEPOSIT', 'TRANSFER', 'WITHDRAW'))
        """, nativeQuery = true)

    List<Transaction> findWalletTransactions(@Param("walletId") Long walletId);
}













//
//SELECT DISTINCT
//   *
//FROM
//Transactions t
//JOIN
//Transaction_Participants tp ON t.id = tp.transaction_id
//        WHERE
//t.id IN (
//        SELECT transaction_id
//        FROM Transaction_Participants
//                WHERE user_id = 2
//)
//AND (tp.role IN ('SENDER', 'RECEIVER') OR t.type IN ('DEPOSIT', 'TRANSFER', 'WITHDRAW'));
