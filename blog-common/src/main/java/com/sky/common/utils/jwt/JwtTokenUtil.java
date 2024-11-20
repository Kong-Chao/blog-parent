package com.sky.common.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author kc
 * jwt工具类
 */
@Component
@Slf4j
public class JwtTokenUtil {

   @Value("${token.secret}")
    private String secretKey;

    @Getter
    @Value("${token.expireTime}")
    private long expirationTime; // 单位：毫秒

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
        Claims claims = getAllClaimsFromToken(accessToken);
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
     * 获取token的过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 从Token中获取任意声明
     * @param token 令牌
     * @param claimsResolver 声明解析函数
     * @param <T> 返回类型
     * @return 解析结果
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 获取token中的载荷数据
     * @param token 令牌
     * @return 载荷数据
     */
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 验证token
     * @param token 令牌
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            boolean isExpired = claims.getExpiration().before(new Date());
            return !isExpired;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    /**
     * 计算过期时间
     * @param expirationTime 过期时间
     * @return 计算后的过期时间
     */
    private Date calculateExpirationDate(long expirationTime) {
        return new Date(System.currentTimeMillis() + expirationTime);
    }

}
