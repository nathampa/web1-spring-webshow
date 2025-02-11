package br.com.ifs.spring1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    // Em uma aplicação real, esses valores devem ser configurados externamente (ex.: application.properties)
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static Key getSecretKey() {
        return SECRET_KEY;
    }
    private long jwtExpirationInMs = 3600000; // 1 hora

    // Gera o token com base no username (ou outra informação relevante)
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // Extrai o username do token
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Valida o token
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            // Aqui você pode tratar as exceções (token expirado, mal formado, etc.)
            return false;
        }
    }
}
