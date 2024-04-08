package com.ibm.mateusmelo.security.jwt;

import com.ibm.mateusmelo.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {
    @Value("${security.jwt.expiration}")
    private String expiration;
    @Value("${security.jwt.signature-key}")
    private String signatureKey;

    public String generateToken(User user) {
        long expString = Long.parseLong(expiration);
        LocalDateTime expDateTime = LocalDateTime.now().plusMinutes(expString);
        Date expirationDate = Date.from(expDateTime.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .claims(new HashMap<>())
                .subject(user.getUsername())
                .expiration(expirationDate)
                .signWith(getSecretKey())
                .compact();
    }

    public String getLoginUsername(String token){
        return getClaims(token).getSubject();
    }

    public boolean isValidToken (String token) {
        try {
            Date expToken = getClaims(token).getExpiration();
            LocalDateTime expDateTime = expToken.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(expDateTime);
        } catch (Exception e) {
            return false;
        }
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(signatureKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
    }
}
