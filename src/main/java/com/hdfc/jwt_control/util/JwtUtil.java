package com.hdfc.jwt_control.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {

    // Holds the signing and verification key.
    private final SecretKey secretKey;

    // Holds the access-token lifetime in milliseconds.
    private final long accessTokenExpiration;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration}")
            long accessTokenExpiration) {

        // Convert the configured secret into a cryptographic key.
        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));

        // Store the configured access-token lifetime.
        this.accessTokenExpiration = accessTokenExpiration;
    }

    public String generateAccessToken(
            String username, String role) {

        // Capture the current time.
        Date now = new Date();

        // Calculate when this token should expire.
        Date expiry = new Date(
                now.getTime() + accessTokenExpiration);
        //If now is 10:00:00 and the configured duration is 60 seconds, expiry is 10:01:00.

        // Build, sign, and serialize the JWT.
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(secretKey)
                .compact();
    }

    public Claims extractClaims(String token) {

        // Verify and parse the signed JWT.
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {

        // Read the subject claim from the JWT.
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {

        // Compare the token expiry with the current time.
        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    public boolean isTokenValid(String token) {

        try {
            // Parse and verify the JWT.
            Claims claims = extractClaims(token);

            // Return true if the expiry is still in the future.
            return claims.getExpiration().after(new Date());

        } catch (Exception e) {

            // Parsing, signature, or other validation failed.
            e.printStackTrace();

            return false;
        }
    }
}