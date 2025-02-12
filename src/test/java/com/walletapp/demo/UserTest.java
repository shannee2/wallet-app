package com.walletapp.demo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void TestUserCreation(){
        User user = new User();
        assertNotNull(user);
    }

    @Test
    public void TestUserRegister(){
        User user = new User();
        String username = "johndoe";
        String password = "abcd";
        user.register(username, password);
        assertTrue(user.isRegistered());
    }

    @Test
    public void TestUserNotRegisteredInitially(){
        User user = new User();
        assertFalse(user.isRegistered());
    }
}
