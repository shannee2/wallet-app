package com.walletapp.service;

import com.walletapp.model.currency.Currency;
import com.walletapp.model.currency.CurrencyType;
import com.walletapp.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public Currency getCurrency(String currencyType){
        return currencyRepository.findByType(CurrencyType.valueOf(currencyType))
                .orElseThrow(() -> new IllegalArgumentException("Currency not found"));
    }

    public Currency getCurrency(CurrencyType currencyType){
        return currencyRepository.findByType(currencyType)
                .orElseThrow(() -> new IllegalArgumentException("Currency not found"));
    }
}
