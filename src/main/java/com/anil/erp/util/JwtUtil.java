package com.anil.erp.util;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {

    @Autowired
    private JwtConfig jwtConfig;

    public String generateToken(String username, List<String> lstRoles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", lstRoles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 20)) // 20 minutes expiry
                .signWith(jwtConfig.getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

