package com.character_almanach.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.character_almanach.model.user.CustomUserDetails;
import com.character_almanach.model.user.Roles;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(CustomUserDetails userDetails) {

        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .claim("userId", userDetails.getId())
            .claim("roles", userDetails.getAuthorities().iterator().next().getAuthority())
            .setIssuedAt(new Date())
            .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
            .signWith(getSecretKey())
            .compact();
    }

    public String extractUserId(String token){
        return extractPayload(token).get("userId", String.class);
    }

    public Roles extractRole(String token){
        return Roles.valueOf(extractPayload(token).get("roles",String.class));
    }

    public Date extractExpiration(String token){
        return extractPayload(token).getExpiration();
    }

    public Date extractIssueDate(String token){
        return extractPayload(token).getIssuedAt();
    }

    public String extractUsername(String token) {
        return extractPayload(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims extractPayload(String token){

        return Jwts.parserBuilder()
            .setSigningKey(getSecretKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}