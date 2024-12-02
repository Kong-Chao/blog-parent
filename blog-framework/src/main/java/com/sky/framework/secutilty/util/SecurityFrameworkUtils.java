package com.sky.framework.secutilty.util;

import com.sky.common.core.domain.UserBO;
import com.sky.common.core.exception.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * 安全服务工具类
 *
 * @author admin

 */
public class SecurityFrameworkUtils {

    private SecurityFrameworkUtils() {}

    /**
     * 获得当前认证信息
     *
     * @return 认证信息
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    @Nullable
    public static UserBO getLoginUser() {
        try {
            return (UserBO) getAuthentication().getPrincipal();
        } catch (Exception e)
        {
            throw new AuthException(HttpStatus.UNAUTHORIZED.value(),"获取用户信息异常");
        }
    }

    /**
     * 获得当前用户的编号，从上下文中
     *
     * @return 用户编号
     */
    @Nullable
    public static Long getLoginUserId() {
        try
        {
            return Objects.requireNonNull(getLoginUser()).getUserId();
        }
        catch (Exception e)
        {
            throw new AuthException(HttpStatus.UNAUTHORIZED.value(),"获取用户ID异常");
        }
    }

}
