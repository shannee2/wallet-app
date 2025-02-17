package com.walletapp.service;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JWTService {
    private String secretKey = "fJKFDSKA347";
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
//        return Jwts.builder()
//                .claims()
//                .add(claims)
//                .subject(username)
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis()) * 60 * 60 * 60)
//                .and()
//                .signWith(getKey())
//                .compact();
        return null;
    }

//    private Key getKey() {
//
//    }
}
