package com.mju.lighthouseai.global.jwt;

import com.mju.lighthouseai.domain.user.entity.User;
import com.mju.lighthouseai.domain.user.entity.UserRole;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String ACCESS_TOKEN_HEADER = "Access-Token";
    public static final String REFRESH_TOKEN_HEADER = "Refresh-Token";

    @Value("${spring.jwt.access.expiration-period}")
    private Long accessTokenExpirationPeriod;

    @Value("${spring.jwt.refresh.expiration-period}")
    private Long refreshTokenExpirationPeriod;

    @Value("${spring.jwt.secret-key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private SecretKey key;
    public static final Logger logger = LoggerFactory.getLogger("JWT log");

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createAccessToken(String email, UserRole role) {
        Date date = new Date();
        return BEARER_PREFIX +
                Jwts.builder()
                        .subject(email)
                        .claim(AUTHORIZATION_KEY, role)
                        .expiration(new Date(date.getTime() + accessTokenExpirationPeriod))
                        .issuedAt(date)
                        .signWith(key, Jwts.SIG.HS256)
                        .compact();
    }

    public String createRefreshToken(String email, UserRole role) {
        Date date = new Date();
        return BEARER_PREFIX +
                Jwts.builder()
                        .subject(email)
                        .claim(AUTHORIZATION_KEY, role)
                        .expiration(new Date(date.getTime() + refreshTokenExpirationPeriod))
                        .issuedAt(date)
                        .signWith(key, Jwts.SIG.HS256)
                        .compact();
    }

    // JWT Cookie 에 저장
    public void addJwtToCookie(String token, HttpServletResponse res) {
        token = URLEncoder.encode(token, StandardCharsets.UTF_8).replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
        cookie.setPath("/");

        // Response 객체에 Cookie 추가
        res.addCookie(cookie);
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        logger.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            logger.error("Invalid JWT signature");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty");
        }
        return false;
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public String getAccessTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(7);
        }
        return null;
    }

    public void addAccessTokenToHeader(final User user, final HttpServletResponse httpServletResponse) {
        String token = createAccessToken(user.getEmail(), user.getRole());
        logger.info("header에 AccessToken 추가");
        httpServletResponse.addHeader(ACCESS_TOKEN_HEADER, token);
    }

    public void addRefreshTokenToHeader(final User user, final HttpServletResponse httpServletResponse) {
        String token = createRefreshToken(user.getEmail(), user.getRole());
        logger.info("header에 RefreshToken 추가");
        httpServletResponse.addHeader(REFRESH_TOKEN_HEADER, token);
    }
}
