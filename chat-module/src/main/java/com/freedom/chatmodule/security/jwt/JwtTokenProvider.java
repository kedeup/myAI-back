package com.freedom.chatmodule.security.jwt;


import com.sun.security.auth.UserPrincipal;
import io.jsonwebtoken.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.RequestContextFilter;

import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author kede·W  on  2023/4/13
 */
@Slf4j
@Component
public class JwtTokenProvider {

    private static final String secretKey = "mySecretKey";
    private static final long tokenExpirationMsec = 360000;

//    @Value("${jwt.secret}")
//    private String jwtSecret;
//
//    @Value("${jwt.expiration}")
//    private int tokenExpirationMsec;

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal =
                new UserPrincipal(authentication.getPrincipal().toString());

        // 构造JWT荷载
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + tokenExpirationMsec);
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userPrincipal.getName());


        return Jwts.builder()
                .setSubject(userPrincipal.getName())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

//    public Long getUserIdFromToken(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody();
//
//        return Long.parseLong(claims.getSubject());
//    }

        public String getUserNameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
