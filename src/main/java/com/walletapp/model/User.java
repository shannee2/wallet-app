package com.walletapp.model;

import com.walletapp.exceptions.UserWalletAlreadyExistsException;
import com.walletapp.model.currency.Value;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

//    @OneToOne(cascade = CascadeType.ALL)
//    private Wallet wallet;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
//        this.wallet = new Wallet();
    }

    public User() {}

//    public void depositMoney(Value value) {
//        this.wallet.depositMoney(value);
//    }
//
//    public void withdrawMoney(Value value) {
//        this.wallet.withdrawMoney(value);
//    }

    public String getUsername() {
        return username;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public Wallet getWallet() {
//        return wallet;
//    }

//    public void createWallet(Wallet wallet) {
//        if(this.wallet!=null){
//            throw new UserWalletAlreadyExistsException();
//        }
//        this.wallet = wallet;
//    }
}