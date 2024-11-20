package com.sky.framework.secutilty.handler;

import com.alibaba.fastjson2.JSONObject;
import com.sky.common.constant.Constants;
import com.sky.common.core.domain.CommonResult;
import com.sky.common.utils.jwt.JwtTokenUtil;
import com.sky.framework.core.redis.service.RedisService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出处理类 返回成功
 * @author admin
 */
@Configuration
@Slf4j
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisService redisService;

    /**
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");

        // 获取token
        final String authToken = request.getHeader(Constants.AUTHORIZATION_HEADER);
        if (authToken != null && authToken.startsWith(Constants.TOKEN_PREFIX) && jwtTokenUtil.validateToken(authToken)) {
            String jwtToken = authToken.substring(Constants.TOKEN_PREFIX.length());
            Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken);
            if (claims != null) {
                String userKey = Constants.AUTH_TOKEN + claims.get("uuid").toString();
                redisService.deleteObject(userKey);
            }
        }
        CommonResult<String> result = CommonResult.success("退出成功");
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
