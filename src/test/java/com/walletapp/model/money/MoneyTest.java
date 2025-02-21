package com.walletapp.model.money;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoneyTest {

    private Currency usd;
    private Currency eur;
    private Currency gbp;
    private Currency inr;

    @BeforeEach
    public void setUp() {
        usd = new Currency(CurrencyType.USD, 1);
        eur = new Currency(CurrencyType.EUR, 1);
        gbp = new Currency(CurrencyType.GBP, 1);
        inr = new Currency(CurrencyType.INR, 1);
    }

    @Test
    public void testConstructor() {
        Money money = new Money(100.0, usd);
        assertEquals(100.0, money.getAmount());
        assertEquals(usd, money.getCurrency());
    }

    @Test
    public void testGetAmount() {
        Money money = new Money(200.0, eur);
        assertEquals(200.0, money.getAmount());
    }

    @Test
    public void testGetCurrency() {
        Money money = new Money(300.0, gbp);
        assertEquals(gbp, money.getCurrency());
    }

//    @Test
//    public void testConvertToInr() {
//        Value value = new Value(1.0, usd);
//        Value convertedValue = value.convertTo(inr);
//        assertEquals(86.0, convertedValue.getAmount(), 0.01);
//        assertEquals(inr, convertedValue.getCurrency());
//    }
//
//    @Test
//    public void testConvertToUsd() {
//        Value value = new Value(85.0, eur);
//        Value convertedValue = value.convertTo(usd);
//        assertEquals(100.0, convertedValue.getAmount(), 0.01);
//        assertEquals(usd, convertedValue.getCurrency());
//    }
//
//    @Test
//    public void testConvertToGbp() {
//        Value value = new Value(100.0, usd);
//        Value convertedValue = value.convertTo(gbp);
//        assertEquals(75.0, convertedValue.getAmount(), 0.01);
//        assertEquals(gbp, convertedValue.getCurrency());
//    }
//
//    @Test
//    public void testConvertFromInrToEur() {
//        Value value = new Value(74.0, inr);
//        Value convertedValue = value.convertTo(eur);
//        assertEquals(0.73, convertedValue.getAmount(), 0.01);
//        assertEquals(eur, convertedValue.getCurrency());
//    }
}