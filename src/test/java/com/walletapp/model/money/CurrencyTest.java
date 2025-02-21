package com.walletapp.model.money;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CurrencyTest {

    @Test
    void testCurrencyConstructor() {
        Currency currency = new Currency(CurrencyType.USD, 1.0);

        assertThat(currency.getType()).isEqualTo(CurrencyType.USD);
        assertThat(currency.getConversionFactor()).isEqualTo(1.0);
    }

    @Test
    void testDefaultConstructor() {
        Currency currency = new Currency();

        assertThat(currency.getType()).isNull();
        assertThat(currency.getConversionFactor()).isEqualTo(0.0);
    }

    @Test
    void testSettersAndGetters() {
        Currency currency = new Currency();
        currency = new Currency(CurrencyType.INR, 83.5);

        assertThat(currency.getType()).isEqualTo(CurrencyType.INR);
        assertThat(currency.getConversionFactor()).isEqualTo(83.5);
    }
}
