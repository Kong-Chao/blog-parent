package com.sky.system.mapper;

import com.sky.common.core.domain.entity.SysMenu;
import com.sky.common.core.domain.UserBO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * @author admin
 */
@Mapper
public interface AuthMapper {

    UserBO selectByUserName(String username);

    /**
     * 查询所有权限
     * @return
     */
    Set<SysMenu> selectAllAuthority();
}
