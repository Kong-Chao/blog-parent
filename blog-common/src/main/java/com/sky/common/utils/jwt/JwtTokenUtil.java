package com.sky.common.utils.jwt;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
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
        Claims claims = getAllClaimsFromToken(accessToken);
        if (claims == null){
            return null;
        }
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
        final Claims claims = getAllClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        return claimsResolver.apply(claims);
    }

    /**
     * 获取token中的载荷数据
     * @param token 令牌
     * @return 载荷数据
     */
    public Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.warn("Token已过期: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("不支持的Token: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("无效的Token: {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("签名验证失败: {}", e.getMessage());
        } catch (Exception e) {
            log.error("解析Token时发生异常: {}", e.getMessage());
        }
        return null;
    }


    /**
     * 验证token
     * @param token 令牌
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        return token != null && !isTokenExpired(token);
    }

    /**
     * 验证Token是否过期
     * @param token 令牌
     * @return 是否过期
     */
    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration != null && expiration.before(new Date());
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
