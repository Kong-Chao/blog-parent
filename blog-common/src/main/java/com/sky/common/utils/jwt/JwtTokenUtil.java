package com.sky.common.utils.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

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
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
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
     * token验证
     */
    public boolean validateToken(String token, UserDetails userDetails){
        final String userName = getUserNameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }


}
