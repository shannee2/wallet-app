package com.walletapp.model.transaction;

import com.walletapp.model.money.Currency;
import com.walletapp.model.money.CurrencyType;
import com.walletapp.model.wallet.Wallet;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionTest {

    @Test
    void testTransactionConstructorWithoutId() {
        Currency currency = new Currency(CurrencyType.USD, 1.0);
        Wallet wallet = new Wallet();
        Transaction transaction = new Transaction(TransactionType.DEPOSIT, 100.0, currency, wallet);

        assertThat(transaction.getType()).isEqualTo(TransactionType.DEPOSIT);
        assertThat(transaction.getAmount()).isEqualTo(100.0);
        assertThat(transaction.getCurrency()).isEqualTo(currency);
        assertThat(transaction.getWallet()).isEqualTo(wallet);
        assertThat(transaction.getDate()).isNotNull();
    }

    @Test
    void testTransactionConstructorWithId() {
        Currency currency = new Currency(CurrencyType.USD, 1.0);
        Wallet wallet = new Wallet();
        Transaction transaction = new Transaction(1L, TransactionType.WITHDRAW, 50.0, currency, wallet);

        assertThat(transaction.getId()).isEqualTo(1L);
        assertThat(transaction.getType()).isEqualTo(TransactionType.WITHDRAW);
        assertThat(transaction.getAmount()).isEqualTo(50.0);
        assertThat(transaction.getCurrency()).isEqualTo(currency);
        assertThat(transaction.getWallet()).isEqualTo(wallet);
        assertThat(transaction.getDate()).isNotNull();
    }

    @Test
    void testSetters() {
        Transaction transaction = new Transaction();
        Date customDate = new Date();
        TransactionRecipient recipient = new TransactionRecipient();

        long id = 10L;
        transaction.setId(id);
        transaction.setDate(customDate);
        transaction.setRecipient(recipient);

        assertThat(transaction.getId()).isEqualTo(id);
        assertThat(transaction.getDate()).isEqualTo(customDate);
        assertThat(transaction.getRecipient()).isEqualTo(recipient);
    }
}
