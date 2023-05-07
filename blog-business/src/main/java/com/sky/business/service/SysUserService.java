package com.sky.business.service;

import com.sky.business.pojo.SysUser;

/**
 * @author kc
 * @create 2023-05-07 18:38
 */
public interface SysUserService {
    SysUser findUserById(Long id);
}
