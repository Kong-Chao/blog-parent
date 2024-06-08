package com.sky.common.utils.jwt;

import com.sky.common.core.domain.model.LoginUser;
import com.sky.common.utils.UUIDutil;
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

    @Value("${token.refreshExpireTime}")
    private long REFRESH_EXPIRATION_TIME;

    /**
     * 令牌生成
     */
    public String generateToken(LoginUser loginUser){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",loginUser.getUserId());
        // 添加唯一的jti声明
        claims.put("jti", UUIDutil.generateRandomString(12));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(loginUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * 刷新令牌
     * @param loginUser
     * @return
     */
    public String generateRefreshToken(LoginUser loginUser) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",loginUser.getUserId());
        // 添加唯一的jti声明
        claims.put("jti", UUIDutil.generateRandomString(12));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(loginUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * 获取token中用户名
     */
    public String getUserNameFromToken(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (SignatureException ex) {
            log.error("无效的JWT签名", ex);
        } catch (MalformedJwtException ex) {
            log.error( "无效的JWT令牌", ex);
        } catch (ExpiredJwtException ex) {
            log.error("JWT令牌过期", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("不支持的JWT令牌", ex);
        } catch (IllegalArgumentException ex) {
            log.error("JWT声明字符串为空", ex);
        }
        return null;
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
    private Claims getAllClaimsFromToken(String token) {
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
        final String userName = getUserNameFromToken(token);
        final Long userId = getUserIdFromToken(token);
        return (userName.equals(userDetails.getUsername()) && userId.equals(((LoginUser) userDetails).getUserId()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
