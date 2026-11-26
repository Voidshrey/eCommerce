package com.ecommerce_backend.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.security.Key;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
@Component
public class JwtUtils {


    // we need a security key

    @Value("${jwt.secret}")
    private String secretKey;
    // expiration in MS
    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    private final Set<String> blackListToken = new HashSet<>();


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String userName, String role) {
        return Jwts
                .builder()
                .signWith(getSignInKey())
                .setSubject(userName)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .compact();

    }

    public String getUserNameFromToken(String token) {

        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) throws SignatureException {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey())
                    .build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new SignatureException("Invalid token signature or structure", e);
        }
    }

    public String getRoleFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build()
                .parseClaimsJws(token).getBody().get("role", String.class);
    }

    public void addBlackList(String token) {
        if (!ObjectUtils.isEmpty(token)) {
            blackListToken.add(token);
        } else {
            throw new RuntimeException("Token is empty");
        }
    }

    public Boolean isBlackListToken(String token){
       return blackListToken.contains(token);
    }
}
