package com.walletapp.demo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void TestUserCreation(){
        String username = "johndoe";
        String password = "Abc@123";
        User user = new User(username, password);
        assertNotNull(user);
    }

    @Test
    public void TestDepositMoneyIntoUserWallet(){
        String username = "johndoe";
        String password = "Abc@123";
        User user = new User(username, password);
        assertDoesNotThrow(()->user.depositMoneyToWallet(100.45));
    }
}
