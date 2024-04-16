package com.sky.system.service.impi;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.common.core.domain.enyity.SysUser;
import com.sky.system.mapper.SysUserMapper;
import com.sky.system.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author kc
 */
@Service
public class SystemServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser selectByUserName(String username) {
        return sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName,username));
    }
}
