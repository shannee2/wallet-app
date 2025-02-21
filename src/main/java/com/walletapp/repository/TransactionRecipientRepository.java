package com.walletapp.repository;

import com.walletapp.model.transaction.TransactionRecipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRecipientRepository extends JpaRepository<TransactionRecipient, Long> {}
