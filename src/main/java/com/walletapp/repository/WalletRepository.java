package com.walletapp.repository;

import com.walletapp.model.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserId(Long user_id);
    Optional<Wallet> findByIdAndUserId(Long id, Long user_id);
}