package com.sky.framework.secutilty.filter;

import com.sky.common.constant.Constants;
import com.sky.common.core.domain.entity.UserBO;
import com.sky.common.utils.json.JsonUtils;
import com.sky.common.utils.jwt.JwtTokenUtil;
import com.sky.framework.core.redis.service.RedisService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * token验证过滤器
 * @author kc
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final RedisService redisService;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService detailsService, RedisService redisService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = detailsService;
        this.redisService = redisService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                // 解析token
                Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken);
                String userKey = Constants.AUTH_TOKEN + claims.get("uuid").toString();
                Map<String, Object> userMap = redisService.getCacheObject(userKey);
                UserBO loginUser = JsonUtils.mapToObject(userMap, UserBO.class);

                if (jwtTokenUtil.validateToken(jwtToken, loginUser)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            loginUser, null, loginUser.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // 保存登录用户信息到SecurityContextHolder
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                log.error("无法获得JWT令牌,原因:{}",e.getMessage());
            }
        }
        chain.doFilter(request, response);
    }
}
