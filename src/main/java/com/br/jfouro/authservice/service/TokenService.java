package com.br.jfouro.authservice.service;

import com.br.jfouro.authservice.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration; // em milissegundos

    private Date calculateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(calculateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token, User user) {
        return Optional.ofNullable(token)
                .map(t -> {
                    Claims claims = extractClaims(t);
                    return claims.getSubject().equals(user.getUsername())
                            && !claims.getExpiration().before(new Date());
                })
                .orElse(false);
    }

    public String getUsernameFromToken(String token) {
        return Optional.ofNullable(token)
                .map(t -> extractClaims(t).getSubject())
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));
    }
    
}