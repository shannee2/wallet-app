package com.walletapp.model;

import com.walletapp.exceptions.InsufficientBalanceException;

public class Wallet {
    private double amount;
    public void depositMoney(double amount) {
        this.amount +=amount;
    }

    public void withdrawMoney(double amount) {
        if(this.amount < amount){
            throw new InsufficientBalanceException();
        }
        this.amount-=amount;
    }
}
