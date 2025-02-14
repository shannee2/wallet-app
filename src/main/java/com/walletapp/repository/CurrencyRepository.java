package com.walletapp.repository;

import com.walletapp.model.currency.Currency;
import com.walletapp.model.currency.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByType(CurrencyType type);
}