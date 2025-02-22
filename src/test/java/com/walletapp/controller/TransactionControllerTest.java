package com.walletapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionResponse;
import com.walletapp.model.money.Currency;
import com.walletapp.model.money.CurrencyType;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionRecipient;
import com.walletapp.model.transaction.TransactionType;
import com.walletapp.model.wallet.Wallet;
import com.walletapp.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
//@ExtendWith(MockitoExtension.class)

class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    private TransactionResponse depositResponse;
    private TransactionResponse withdrawResponse;
    private TransactionResponse transferResponse;

    private String TRANSACTION_URL =  "/users/1/wallets/1/transactions";

    @BeforeEach
    void setUp() {
        Currency currency = new Currency( CurrencyType.USD, 1L);
        Wallet senderWallet = new Wallet(1L);
        Wallet receiverWallet = new Wallet(2L);

        Transaction depositTransaction = new Transaction(1L, TransactionType.DEPOSIT, 100.0, currency, senderWallet);
        Transaction withdrawTransaction = new Transaction(2L, TransactionType.WITHDRAW, 50.0, currency, senderWallet);
        Transaction transferTransaction = new Transaction(3L, TransactionType.TRANSFER, 25.0, currency, senderWallet);

        TransactionRecipient recipient = new TransactionRecipient(transferTransaction, receiverWallet);

        depositResponse = new TransactionResponse(depositTransaction, null);
        withdrawResponse = new TransactionResponse(withdrawTransaction, null);
        transferResponse = new TransactionResponse(transferTransaction, recipient);
    }

    @Test
    void testCreateDepositTransaction() throws Exception {
        TransactionRequest request = new TransactionRequest(100.0, "USD", TransactionType.DEPOSIT);
        Mockito.when(transactionService.createTransaction(any(), anyLong(), anyLong())).thenReturn(depositResponse);

        mockMvc.perform(post(TRANSACTION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionType").value("DEPOSIT"))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.walletId").value(1))
                .andExpect(jsonPath("$.senderWalletId").doesNotExist())
                .andExpect(jsonPath("$.receiverWalletId").doesNotExist());
    }

    @Test
    void testCreateWithdrawTransaction() throws Exception {
        TransactionRequest request = new TransactionRequest(50.0, "USD", TransactionType.WITHDRAW);
        Mockito.when(transactionService.createTransaction(any(), anyLong(), anyLong())).thenReturn(withdrawResponse);

        mockMvc.perform(post(TRANSACTION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionType").value("WITHDRAW"))
                .andExpect(jsonPath("$.amount").value(50.0))
                .andExpect(jsonPath("$.walletId").value(1))
                .andExpect(jsonPath("$.senderWalletId").doesNotExist())
                .andExpect(jsonPath("$.receiverWalletId").doesNotExist());
    }

    @Test
    void testCreateTransferTransaction() throws Exception {
        TransactionRequest request = new TransactionRequest(25.0, "USD", TransactionType.TRANSFER, 2L);
        Mockito.when(transactionService.createTransaction(any(), anyLong(), anyLong())).thenReturn(transferResponse);

        mockMvc.perform(post(TRANSACTION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionType").value("TRANSFER"))
                .andExpect(jsonPath("$.amount").value(25.0))
                .andExpect(jsonPath("$.senderWalletId").value(1))
                .andExpect(jsonPath("$.receiverWalletId").value(2))
                .andExpect(jsonPath("$.walletId").doesNotExist());
    }

    @Test
    void testGetTransactions() throws Exception {
        List<TransactionResponse> transactions = List.of(depositResponse, withdrawResponse, transferResponse);
        Mockito.when(transactionService.getTransactions(anyLong(), anyLong())).thenReturn(transactions);

        mockMvc.perform(get(TRANSACTION_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].transactionType").value("DEPOSIT"))
                .andExpect(jsonPath("$[0].walletId").value(1))
                .andExpect(jsonPath("$[1].transactionType").value("WITHDRAW"))
                .andExpect(jsonPath("$[1].walletId").value(1))
                .andExpect(jsonPath("$[2].transactionType").value("TRANSFER"))
                .andExpect(jsonPath("$[2].senderWalletId").value(1))
                .andExpect(jsonPath("$[2].receiverWalletId").value(2))
                .andExpect(jsonPath("$[2].walletId").doesNotExist());
    }
}


//        MvcResult result = mockMvc.perform(post("/users/1/wallets/1/transactions")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andReturn(); // Store the result
//
//        System.out.println("Response Body: " + result.getResponse().getContentAsString());
