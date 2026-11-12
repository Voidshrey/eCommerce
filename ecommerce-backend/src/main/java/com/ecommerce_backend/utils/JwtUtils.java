package com.ecommerce_backend.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.security.Key;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.Map;

@ControllerAdvice
@Component
public class JwtUtils {


    // we need a security key
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // expiration in MS
    private final int jwtExpirationMs = 86400000;



    public String generateToken(String userName, String role) {
        return Jwts
                .builder()
                .signWith(key)
                .setSubject(userName)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .compact();

    }

    public String getUserNameFromToken(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) throws SignatureException {
        try {
            Jwts.parserBuilder().setSigningKey(key)
                    .build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new SignatureException("Invalid token signature or structure", e);
        }
    }

    public String getRoleFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("role", String.class);
    }
}
