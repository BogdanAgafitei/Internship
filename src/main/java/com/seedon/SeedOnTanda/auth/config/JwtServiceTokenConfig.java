package com.seedon.SeedOnTanda.auth.config;

import com.seedon.SeedOnTanda.auth.facade.AuthenticationFacade;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceTokenConfig {

    private static final String SECRET_KEY_SEEDON = "VJPS8TbMEbGRWMA4dh97vWzVnD6zKQhmKqJqU8DG";

    private final AuthenticationFacade authenticationFacade;

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public boolean isTokenValid(String token) {
        return authenticationFacade.isTokenValid(token);
    }

    public Date extractExpiration(String token) throws Exception {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Base64.getEncoder().encode(SECRET_KEY_SEEDON.getBytes()));
    }
}
