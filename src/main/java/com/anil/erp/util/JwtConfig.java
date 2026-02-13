package com.anil.erp.util;


import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    public Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}