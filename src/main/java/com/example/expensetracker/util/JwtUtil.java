package com.example.expensetracker.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String secret = "shkjdvbfdhy3746t4t67t77yf6776t87";
    private final long expirationMs = 86400000;

    private  SecretKey key(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String email, String role){
        return Jwts.builder()
                .subject(email)
                .claim("role", role) //add role claim
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public String extractEmail(String token){
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String extractRole(String token){
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role",String.class);
    }

    public boolean isTokenValid(String token){
        try {
            extractEmail(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
