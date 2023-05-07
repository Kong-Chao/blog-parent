package com.sky.business.service.impl;

import com.sky.business.mapper.SysUserMapper;
import com.sky.business.pojo.SysUser;
import com.sky.business.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kc
 * @create 2023-05-07 18:42
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (null == sysUser){
            sysUser = new SysUser();
            sysUser.setNickname("叶欣");
        }
        return sysUser;
    }
}
