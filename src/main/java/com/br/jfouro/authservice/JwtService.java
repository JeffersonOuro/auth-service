package com.br.jfouro.authservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class JwtService {

    private final String secretKey = System.getenv("JWT_SECRET") != null
            ? System.getenv("JWT_SECRET")
            : "minhaChaveSecretaSuperSegura1234567890";

    public AuthResponse validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            return new AuthResponse(false, "Token não fornecido.");
        }

        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String usuario = claims.getSubject();
            return new AuthResponse(true, "Token válido para usuário: " + usuario);

        } catch (Exception e) {
            // Logar erro se necessário (System.out.println para Lambda logs)
            return new AuthResponse(false, "Token inválido: " + e.getMessage());
        }
    }

}