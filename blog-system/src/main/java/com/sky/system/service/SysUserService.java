package com.sky.system.service;

import com.sky.common.core.domain.enyity.SysUser;

/**
 * @author kc
 */
public interface SysUserService {

    SysUser selectByUserName(String username);
}
