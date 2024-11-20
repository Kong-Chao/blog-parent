package com.sky.framework.secutilty.filter;

import cn.hutool.core.util.StrUtil;
import com.sky.common.constant.Constants;
import com.sky.common.core.domain.UserBO;
import com.sky.common.utils.json.JsonUtils;
import com.sky.common.utils.jwt.JwtTokenUtil;
import com.sky.framework.core.redis.service.RedisService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authToken = request.getHeader(Constants.AUTHORIZATION_HEADER);
        if (StrUtil.isNotBlank(authToken) && authToken.startsWith(Constants.TOKEN_PREFIX)) {
            authToken = authToken.substring(Constants.TOKEN_PREFIX.length());
        }
        // 当 token 存在且尚未验证身份时，执行验证逻辑
        if (authToken != null && jwtTokenUtil.validateToken(authToken) && SecurityContextHolder.getContext().getAuthentication() == null) {
            Claims claims = jwtTokenUtil.getAllClaimsFromToken(authToken);
            String userKey = Constants.AUTH_TOKEN + claims.get("uuid");
            UserBO loginUser = JsonUtils.mapToObject(redisService.getCacheObject(userKey), UserBO.class);
            setAuthenticationContext(loginUser,request);
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
