package com.walletapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletapp.dto.general.ErrorResponse;
import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionResponse;
import com.walletapp.dto.transaction.TransactionWalletDTO;
import com.walletapp.model.currency.Currency;
import com.walletapp.model.currency.CurrencyType;
import com.walletapp.model.transaction.Transaction;
import com.walletapp.model.transaction.TransactionType;
import com.walletapp.model.transaction.TransactionWalletType;
import com.walletapp.registry.WalletHandlerRegistry;
import com.walletapp.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WalletHandlerRegistry walletHandlerRegistry;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void createTransaction_ShouldReturnTransactionResponse_WhenValidDepositRequest() throws Exception {
        Long userId = 1L;
        Long walletId = 1L;

        TransactionWalletDTO transactionWallet = new TransactionWalletDTO(walletId, TransactionWalletType.SELF);

        TransactionRequest request = new TransactionRequest(15.0, "INR", TransactionType.DEPOSIT, transactionWallet);

        Transaction transaction = new Transaction(TransactionType.DEPOSIT, 15.0, new Currency(CurrencyType.INR, 1));
        transaction.setId(1L);
        transaction.setDate(new Date());

        TransactionResponse mockResponse = new TransactionResponse(transaction, List.of(transactionWallet));

        when(transactionService.createTransaction(any(TransactionRequest.class), eq(userId), eq(walletId)))
                .thenReturn(mockResponse);

        mockMvc.perform(post("/users/{userId}/wallets/{walletId}/transactions", userId, walletId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(1))
                .andExpect(jsonPath("$.amount").value(15.0))
                .andExpect(jsonPath("$.currencyType").value("INR"))
                .andExpect(jsonPath("$.transactionType").value("DEPOSIT"))
                .andExpect(jsonPath("$.transactionWallets[0].walletId").value(walletId))
                .andExpect(jsonPath("$.transactionWallets[0].transactionWalletType").value("SELF"))
                .andDo(print());

        verify(transactionService, times(1)).createTransaction(any(TransactionRequest.class), eq(userId), eq(walletId));
    }

    @Test
    void createTransaction_ShouldReturnTransactionResponse_WhenValidWithdrawRequest() throws Exception {
        Long userId = 1L;
        Long walletId = 1L;

        TransactionWalletDTO transactionWallet = new TransactionWalletDTO(walletId, TransactionWalletType.SELF);

        TransactionRequest request = new TransactionRequest(15.0, "INR", TransactionType.WITHDRAW, transactionWallet);

        Transaction transaction = new Transaction(TransactionType.WITHDRAW, 15.0, new Currency(CurrencyType.INR, 1));
        transaction.setId(1L);
        transaction.setDate(new Date());

        TransactionResponse mockResponse = new TransactionResponse(transaction, List.of(transactionWallet));

        when(transactionService.createTransaction(any(TransactionRequest.class), eq(userId), eq(walletId)))
                .thenReturn(mockResponse);

        mockMvc.perform(post("/users/{userId}/wallets/{walletId}/transactions", userId, walletId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(1))
                .andExpect(jsonPath("$.amount").value(15.0))
                .andExpect(jsonPath("$.currencyType").value("INR"))
                .andExpect(jsonPath("$.transactionType").value("WITHDRAW"))
                .andExpect(jsonPath("$.transactionWallets[0].walletId").value(walletId))
                .andExpect(jsonPath("$.transactionWallets[0].transactionWalletType").value("SELF"))
                .andDo(print());

        verify(transactionService, times(1)).createTransaction(any(TransactionRequest.class), eq(userId), eq(walletId));
    }

    @Test
    void createTransaction_ShouldReturnTransactionResponse_WhenValidTransferRequest() throws Exception {
        Long userId = 1L;
        Long walletId = 1L;

        TransactionWalletDTO transactionWalletReceiver = new TransactionWalletDTO(walletId, TransactionWalletType.RECEIVER);
        TransactionWalletDTO transactionWalletSender = new TransactionWalletDTO(walletId, TransactionWalletType.SENDER);

        TransactionRequest request = new TransactionRequest(15.0, "INR", TransactionType.TRANSFER, transactionWalletReceiver);

        Transaction transaction = new Transaction(TransactionType.TRANSFER, 15.0, new Currency(CurrencyType.INR, 1));
        transaction.setId(1L);
        transaction.setDate(new Date());

        TransactionResponse mockResponse = new TransactionResponse(transaction, List.of(transactionWalletSender, transactionWalletReceiver));

        when(transactionService.createTransaction(any(TransactionRequest.class), eq(userId), eq(walletId)))
                .thenReturn(mockResponse);

        mockMvc.perform(post("/users/{userId}/wallets/{walletId}/transactions", userId, walletId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value(1))
                .andExpect(jsonPath("$.amount").value(15.0))
                .andExpect(jsonPath("$.currencyType").value("INR"))
                .andExpect(jsonPath("$.transactionType").value("TRANSFER"))
                .andExpect(jsonPath("$.transactionWallets[0].walletId").value(walletId))
                .andExpect(jsonPath("$.transactionWallets[0].transactionWalletType").value("SENDER"))
                .andExpect(jsonPath("$.transactionWallets[1].walletId").value(transactionWalletReceiver.getWalletId()))
                .andExpect(jsonPath("$.transactionWallets[1].transactionWalletType").value("RECEIVER"))
                .andDo(print());

        verify(transactionService, times(1)).createTransaction(any(TransactionRequest.class), eq(userId), eq(walletId));
    }



    @Test
    void getTransactions_ShouldReturnTransactionList_WhenValidUserIdAndWalletId() throws Exception {
        Long userId = 1L;
        Long walletId = 1L;

        TransactionWalletDTO walletDTO = new TransactionWalletDTO(walletId, com.walletapp.model.transaction.TransactionWalletType.RECEIVER);

        Transaction transaction = new Transaction(TransactionType.TRANSFER, 15.0, new Currency(CurrencyType.INR,1));
        transaction.setId(1L);
        transaction.setDate(new Date());

        TransactionResponse mockResponse = new TransactionResponse(transaction, List.of(walletDTO));

        when(transactionService.getTransactions(userId, walletId)).thenReturn(List.of(mockResponse));

        mockMvc.perform(get("/users/{userId}/wallets/{walletId}/transactions", userId, walletId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].transactionId").value(1))
                .andExpect(jsonPath("$[0].amount").value(15.0))
                .andExpect(jsonPath("$[0].currencyType").value("INR"))
                .andExpect(jsonPath("$[0].transactionType").value("TRANSFER"))
                .andExpect(jsonPath("$[0].transactionWallets[0].walletId").value(walletId))
                .andExpect(jsonPath("$[0].transactionWallets[0].transactionWalletType").value("RECEIVER"))
                .andDo(print());

        verify(transactionService, times(1)).getTransactions(userId, walletId);
    }

    @Test
    void getTransactions_ShouldReturnEmptyList_WhenNoTransactions() throws Exception {
        Long userId = 1L;
        Long walletId = 1L;

        when(transactionService.getTransactions(userId, walletId)).thenReturn(List.of());

        mockMvc.perform(get("/users/{userId}/wallets/{walletId}/transactions", userId, walletId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0))
                .andDo(print());

        verify(transactionService, times(1)).getTransactions(userId, walletId);
    }

    //    @Test
//    void createTransaction_ShouldReturn500_WhenExceptionOccurs() throws Exception {
//        Long userId = 1L;
//        Long walletId = 1L;
//        TransactionRequest request = new TransactionRequest(15.0, "INR", TransactionType.TRANSFER, null);
//
//        when(transactionService.createTransaction(any(TransactionRequest.class), eq(userId), eq(walletId)))
//                .thenThrow(new RuntimeException("Test Exception"));
//
//        mockMvc.perform(post("/users/{userId}/wallets/{walletId}/transactions", userId, walletId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.code").value(500))
//                .andExpect(jsonPath("$.message").value("Internal server error, Test Exception"))
//                .andDo(print());
//
//        verify(transactionService, times(1)).createTransaction(any(TransactionRequest.class), eq(userId), eq(walletId));
//    }

//    @Test
//    void getTransactions_ShouldReturn500_WhenExceptionOccurs() throws Exception {
//        Long userId = 1L;
//        Long walletId = 1L;
//
//        when(transactionService.getTransactions(userId, walletId)).thenThrow(new RuntimeException("Database error"));
//
//        mockMvc.perform(get("/users/{userId}/wallets/{walletId}/transactions", userId, walletId))
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.code").value(500))
//                .andExpect(jsonPath("$.message").value("Internal server error, Database error"))
//                .andDo(print());
//
//        verify(transactionService, times(1)).getTransactions(userId, walletId);
//    }
}
