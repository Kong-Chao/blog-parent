package com.sky.framework.web.service;

import com.sky.common.core.domain.enyity.SysUser;
import com.sky.common.core.domain.model.LoginUser;
import com.sky.common.core.exception.ServiceException;
import com.sky.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserservice;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserservice.selectByUserName(username);
        if (sysUser == null){
            log.info("登录用户:{}不存在",username);
            throw new ServiceException(00000,"登录用户" + "{" + username + "}" + "不存在！");
        }
        return new LoginUser(sysUser.getUserId(),sysUser.getDeptId(),sysUser,null);
    }
}
