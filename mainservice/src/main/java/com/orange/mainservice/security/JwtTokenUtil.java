package com.orange.mainservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class JwtTokenUtil {

    private final JwtTokenConfig tokenConfig;

    String createToken(Authentication authResult) {
        JwtUserDetails userDetails = (JwtUserDetails) authResult.getPrincipal();

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(tokenConfig.getTokenExpirationAfterDays())))
                .signWith(tokenConfig.getSecretKeyForSigning())
                .compact();
    }

    <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(tokenConfig.getSecretKeyForSigning())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    @SuppressWarnings("unchecked")
    Set<String> getAuthorities(String token) {
        Claims claims = getClaims(token);
        var authorities = (List<Map<String, String>>) claims.get("authorities");
        return authorities.stream()
                .map(auth -> auth.get("authority"))
                .collect(Collectors.toSet());
    }

    Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    Boolean isExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }
}
