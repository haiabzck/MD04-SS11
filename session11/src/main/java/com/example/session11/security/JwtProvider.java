package com.example.session11.security;

import com.example.session11.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    private final String secret = "1234567890123456789012345678901234567890123456789012345678901234";
    private final Long expiration = 86400000L;

    public String generateToken(User user){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        Claims claims =  Jwts.claims().setSubject(user.getUsername());
        claims.put("role", user.getRole());
        return Jwts
                .builder()
                .setClaims(claims)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    public boolean validateToken(String token){
        return !Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().isEmpty();
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }}
