package com.walletapp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionType;
import com.walletapp.handler.TransactionHandler;
import com.walletapp.registry.TransactionHandlerRegistry;
import com.walletapp.service.WalletService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WalletService walletService;

    @Autowired
    private ObjectMapper objectMapper;
    private String register = "/users";

    @MockitoBean
    private TransactionHandlerRegistry transactionHandlerRegistry;

    @MockitoBean  // Mock the specific handlers
    private TransactionHandler depositHandler;

    @MockitoBean   // Mock the specific handlers
    private TransactionHandler withdrawHandler;

    @Test
    public void testDepositMoney() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", "testUser");
        requestBody.put("amount", 100.0);
        requestBody.put("currency", "INR");
        requestBody.put("transactionType", "DEPOSIT");

        String jsonRequest = objectMapper.writeValueAsString(requestBody);

        Mockito.when(transactionHandlerRegistry.getHandler(TransactionType.DEPOSIT)).thenReturn(depositHandler);
        Mockito.doNothing().when(depositHandler).handle(Mockito.any());

        mockMvc.perform(post("/wallet")
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)) // Send the correct JSON
                .andExpect(status().isOk());
    }

    @Test
    public void testWithdrawMoney() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", "testUser");
        requestBody.put("amount", 100.0);
        requestBody.put("currency", "INR");
        requestBody.put("transactionType", "WITHDRAW");

        String jsonRequest = objectMapper.writeValueAsString(requestBody);  // Create the JSON

        Mockito.when(transactionHandlerRegistry.getHandler(TransactionType.WITHDRAW)).thenReturn(depositHandler);
        Mockito.doNothing().when(depositHandler).handle(Mockito.any());

        mockMvc.perform(post("/wallet")
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)) // Send the correct JSON
                .andExpect(status().isOk());
    }
}

