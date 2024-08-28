package com.sky.system.service;

import com.sky.common.core.domain.UserBO;
import com.sky.common.core.exception.enums.GlobalErrorCodeConstants;
import com.sky.system.mapper.AuthMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBO userBO = authMapper.selectByUserName(username);
        if (userBO == null){
            log.error("登录用户:{}不存在",username);
            throw new UsernameNotFoundException(GlobalErrorCodeConstants.ACCOUNT_PASSWORD_ERROR.getMsg());
        }
        if (userBO.isAdmin()){
            userBO.setPermissions(authMapper.selectAllAuthority());
        }
        return userBO;
    }
}
