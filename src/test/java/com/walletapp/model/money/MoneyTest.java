package com.walletapp.model.money;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

    @Test
    void testConvertTo_SameCurrency_ReturnsSameMoney() {
        Currency usd = new Currency(CurrencyType.USD, 1.0);
        Money money = new Money(100, usd);

        Money convertedMoney = money.convertTo(usd);

        assertThat(convertedMoney).isSameAs(money);
    }

    @Test
    void testConvertTo_UsdToInr_CorrectConversion() {
        Currency usd = new Currency(CurrencyType.USD, 1.0);
        Currency inr = new Currency(CurrencyType.INR, 83.0);
        Money money = new Money(100, usd);

        Money convertedMoney = money.convertTo(inr);

        assertThat(convertedMoney.getAmount()).isEqualTo(8300.0);
        assertThat(convertedMoney.getCurrency()).isEqualTo(inr);
    }

    @Test
    void testConvertTo_InrToUsd_CorrectConversion() {
        Currency usd = new Currency(CurrencyType.USD, 1.0);
        Currency inr = new Currency(CurrencyType.INR, 83.0);
        Money money = new Money(8300, inr);

        Money convertedMoney = money.convertTo(usd);

        assertThat(convertedMoney.getAmount()).isEqualTo(100.0);
        assertThat(convertedMoney.getCurrency()).isEqualTo(usd);
    }

    @Test
    void testConvertTo_EurToGbp_CorrectConversion() {
        Currency eur = new Currency(CurrencyType.EUR, 0.85);
        Currency gbp = new Currency(CurrencyType.GBP, 0.75);
        Money money = new Money(85, eur);

        Money convertedMoney = money.convertTo(gbp);

        assertThat(convertedMoney.getAmount()).isEqualTo(75.0);
        assertThat(convertedMoney.getCurrency()).isEqualTo(gbp);
    }

    @Test
    void testConvertTo_ZeroAmount_RemainsZero() {
        Currency usd = new Currency(CurrencyType.USD, 1.0);
        Currency inr = new Currency(CurrencyType.INR, 83.0);
        Money money = new Money(0, usd);

        Money convertedMoney = money.convertTo(inr);

        assertThat(convertedMoney.getAmount()).isEqualTo(0.0);
        assertThat(convertedMoney.getCurrency()).isEqualTo(inr);
    }

    @Test
    void testConvertTo_SmallAmounts_PrecisionHandling() {
        Currency usd = new Currency(CurrencyType.USD, 1.0);
        Currency inr = new Currency(CurrencyType.INR, 83.0);
        Money money = new Money(1, usd);

        Money convertedMoney = money.convertTo(inr);

        assertThat(convertedMoney.getAmount()).isEqualTo(83.0);
    }
}