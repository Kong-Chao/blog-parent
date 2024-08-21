package com.sky.common.utils.jwt;

import com.sky.common.core.domain.UserBO;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author kc
 * jwt工具类
 */
@Component
@Slf4j
public class JwtTokenUtil {

   @Value("${token.secret}")
    private  String secretKey;

    @Getter
    @Value("${token.expireTime}")
    private long expirationTime;

    /**
     * 令牌生成
     */
    public String generateToken(Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(calculateExpirationDate(expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * 刷新令牌
     * @param accessToken
     * @return
     */
    public String generateRefreshToken(String accessToken) {
        Optional<Claims> claimsOpt = getAllClaimsFromToken(accessToken);

        if (!claimsOpt.isPresent()) {
            throw new IllegalArgumentException("无效的访问令牌");
        }

        Claims claims = claimsOpt.get();
        Map<String, Object> refreshedClaims = new HashMap<>(claims);
        // 过期时间为访问令牌过期时间的两倍
        return Jwts.builder()
                .setClaims(refreshedClaims)
                .setIssuedAt(new Date())
                .setExpiration(calculateExpirationDate(2 * expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }


    /**
     * 获取token中用户名
     */
    public String getUserNameFromToken(String token) {
        return getAllClaimsFromToken(token)
                .map(claims ->claims.get("username",String.class))
                .orElse(null);
    }

    /**
     * 从令牌中获取用户ID
     */
    public Long getUserIdFromToken(String token) {
        return getAllClaimsFromToken(token)
                .map(claims -> claims.get("userId", Long.class))
                .orElse(null);
    }

    /**
     * 获取token中的载荷数据
     */
    public Optional<Claims> getAllClaimsFromToken(String token) {
        try {
            return Optional.of(Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody());
        } catch (ExpiredJwtException e) {
            log.warn("JWT token expired: ", e);
        } catch (MalformedJwtException e) {
            log.warn("JWT token is malformed: ", e);
        } catch (UnsupportedJwtException e) {
            log.warn("JWT token is unsupported: ", e);
        } catch (IllegalArgumentException e) {
            log.warn("JWT token is illegal: ", e);
        } catch (JwtException e) {
            log.error("JWT token parsing error: ", e);
        }
        return Optional.empty();
    }

    /**
     * 获取token的过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token)
                .map(Claims::getExpiration)
                .orElseThrow(() -> new IllegalArgumentException("无效的JWT令牌"));
    }

    /**
     * 验证token
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        Optional<Claims> claimsOpt = getAllClaimsFromToken(token);

        // 无效令牌
        if (!claimsOpt.isPresent()) {
            return false;
        }

        Claims claims = claimsOpt.get();
        final String userName = claims.get("username", String.class);
        final Long userId = claims.get("userid", Long.class);

        return (userName.equals(userDetails.getUsername())
                && userId.equals(((UserBO) userDetails).getUserId())
                && !isTokenExpired(token));
    }


    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date calculateExpirationDate(long expirationTime) {
        return new Date(System.currentTimeMillis() + expirationTime);
    }

}
