package com.walletapp.repository;

import com.walletapp.model.currency.Currency;
import com.walletapp.model.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByWalletId(Long wallet_id);
}
