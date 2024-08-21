package com.sky.framework.secutilty.filter;

import cn.hutool.core.util.StrUtil;
import com.sky.common.constant.Constants;
import com.sky.common.core.domain.UserBO;
import com.sky.common.utils.json.JsonUtils;
import com.sky.common.utils.jwt.JwtTokenUtil;
import com.sky.framework.core.redis.service.RedisService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * token验证过滤器
 * @author kc
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final RedisService redisService;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, RedisService redisService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.redisService = redisService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        extractToken(request)
                .flatMap(jwtTokenUtil::getAllClaimsFromToken)
                .flatMap(this::getLoginUserFromClaims)
                .filter(loginUser -> jwtTokenUtil.validateToken(getJwtToken(request), loginUser))
                .ifPresent(loginUser -> setAuthenticationContext(loginUser, request));
        chain.doFilter(request, response);
    }

    /**
     * 从授权头中提取JWT令牌。
     */
    private Optional<String> extractToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization"))
                .filter(header -> StrUtil.isNotBlank(header) && header.startsWith("Bearer "))
                .map(header -> header.substring(7));
    }

    /**
     * 使用存储在Redis中的uid从声明中检索登录用户。
     */
    private Optional<UserBO> getLoginUserFromClaims(Claims claims) {
        return Optional.ofNullable(claims.get("uuid"))
                .map(uuid -> Constants.AUTH_TOKEN + uuid.toString())
                .map(redisService::getCacheObject)
                .map(userMap -> JsonUtils.mapToObject((Map<String, Object>) userMap, UserBO.class));
    }

    /**
     * 使用给定的登录用户设置身份验证上下文。
     */
    private void setAuthenticationContext(UserBO loginUser, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                loginUser, null, loginUser.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     *从请求头中检索JWT令牌。
     */
    private String getJwtToken(HttpServletRequest request) {
        return request.getHeader("Authorization").substring(7);
    }
}
