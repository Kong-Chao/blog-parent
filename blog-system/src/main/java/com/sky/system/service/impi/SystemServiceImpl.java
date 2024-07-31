package com.sky.system.service.impi;

import com.sky.system.mapper.SysUserMapper;
import com.sky.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author kc
 */
@Service
public class SystemServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
