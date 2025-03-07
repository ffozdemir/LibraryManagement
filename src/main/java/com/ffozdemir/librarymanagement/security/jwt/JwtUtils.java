package com.ffozdemir.librarymanagement.security.jwt;

import com.ffozdemir.librarymanagement.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${ffozdemir.app.jwtSecret}")
    private String jwtSecret;

    @Value("${ffozdemir.app.jwtExpirationMs}")
    private long jwtExpirationMs;


    public String generateJwtToken(
            Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigninKey(), SignatureAlgorithm.HS512)
                .claim("roles", userPrincipal.getAuthorities())
                .claim("userId", userPrincipal.getId())
                .compact();
    }


    private Key getSigninKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        if (keyBytes.length < 32) {
            LOGGER.warn("JWT secret key is too short, using default key");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmailFromToken(
            String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(
            String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigninKey())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Geçersiz JWT imzası: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Geçersiz JWT formatı: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("Süresi dolmuş JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Desteklenmeyen JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT token boş veya geçersiz: {}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("JWT token doğrulama hatası: {}", e.getMessage());
        }
        return false;
    }


}
