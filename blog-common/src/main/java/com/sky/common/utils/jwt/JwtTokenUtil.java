package com.sky.common.utils.jwt;

import com.sky.common.core.domain.model.LoginUser;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author kc
 * jwt工具类
 */
@Component
@Slf4j
public class JwtTokenUtil {

   @Value("${token.secret}")
    private  String SECRET_KEY;

    @Value("${token.expireTime}")
    private long EXPIRATION_TIME;

    /**
     * 令牌生成
     */
    public String generateToken(Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * 刷新令牌
     * @param accessToken
     * @return
     */
    public String generateRefreshToken(String accessToken) {
        Claims claims = getAllClaimsFromToken(accessToken);
        if (claims == null){
            throw new IllegalArgumentException("无效的访问令牌");
        }
        Map<String, Object> refreshedClaims = new HashMap<>(claims);
        return Jwts.builder()
                .setClaims(refreshedClaims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 2 * EXPIRATION_TIME)) // 过期时间为访问令牌过期时间的两倍
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * 获取token中用户名
     */
    public String getUserNameFromToken(String token) {
        try {
            return getAllClaimsFromToken(token).get("username", String.class);
        } catch (JwtException ex) {
            log.error("JWT令牌解析错误", ex);
            return null;
        }
    }

    /**
     * 从令牌中获取用户ID
     */
    public Long getUserIdFromToken(String token) {
        try {
            return Objects.requireNonNull(getAllClaimsFromToken(token)).get("userId", Long.class);
        } catch (JwtException ex) {
            log.error("JWT令牌解析错误", ex);
            return null;
        }
    }

    /**
     * 获取token中的载荷数据
     */
    public Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException ex) {
            log.error("JWT令牌解析错误", ex);
            return null;
        }
    }

    /**
     * 获取token的过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        try {
            return Objects.requireNonNull(getAllClaimsFromToken(token)).getExpiration();
        } catch (JwtException ex) {
            log.error("JWT令牌解析错误", ex);
            return null;
        }
    }

    /**
     * 验证token
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = getAllClaimsFromToken(token).get("userName").toString();
        final Long userId = getUserIdFromToken(token);
        return (userName.equals(userDetails.getUsername())
                && userId.equals(((LoginUser) userDetails).getUserId())
                && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public long getExpirationTime(){
        return EXPIRATION_TIME;
    }
}
