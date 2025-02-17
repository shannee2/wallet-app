package com.walletapp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletapp.dto.transaction.TransactionRequest;
import com.walletapp.dto.transaction.TransactionType;
import com.walletapp.model.User;
import com.walletapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;
    private String register = "/users";

    @Test
    public void testRegisterUser() throws Exception {
        User user = new User("testUser", "password123");

        mockMvc.perform(post("/users")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUser() throws Exception {
        User user = new User("testUser", "password123");
        Mockito.when(userService.getUser("testUser")).thenReturn(user);

        mockMvc.perform(get("/users/testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }
}

