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
        String authToken = request.getHeader("Authorization");
        if (StrUtil.isNotBlank(authToken) && authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);
        }
        // 当 token 存在且尚未验证身份时，执行验证逻辑
        if (authToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Claims claims = jwtTokenUtil.getAllClaimsFromToken(authToken);
            if (claims != null){
                String userKey = Constants.AUTH_TOKEN + claims.get("uuid");
                UserBO loginUser = JsonUtils.mapToObject(redisService.getCacheObject(userKey), UserBO.class);
                if (loginUser != null) {
                    setAuthenticationContext(loginUser,request);
                }else {
                    log.warn("用户数据在缓存中不存在，userKey: {}", userKey);
                }
            }
        }
        chain.doFilter(request, response);
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

}
