package com.sky.system.service;

import com.sky.common.core.domain.entity.UserBO;

/**
 * @author kc
 */
public interface SysUserService {

    UserBO selectByUserName(String username);

    /**
     * 判断密码是否匹配
     *
     * @param rawPassword 未加密的密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    boolean isPasswordMatch(String rawPassword, String encodedPassword);
}
