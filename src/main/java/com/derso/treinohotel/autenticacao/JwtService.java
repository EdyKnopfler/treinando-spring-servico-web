package com.derso.treinohotel.autenticacao;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.derso.treinohotel.user.UserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final Duration EXPIRATION = Duration.ofMinutes(10);

    private final SecretKey key;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDTO dadosUsuario) {
        Instant now = Instant.now();

        return Jwts.builder()
            .subject(dadosUsuario.id().toString())
            .claim("email", dadosUsuario.email())
            .claim("userType", dadosUsuario.userType())
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(EXPIRATION)))
            .signWith(key)
            .compact();
    }

    public Optional<Claims> validateToken(String token) {
        try {
            return Optional.of(
                Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
            );
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        } 
    }

}
