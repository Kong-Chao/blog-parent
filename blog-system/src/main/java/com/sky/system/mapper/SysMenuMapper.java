package com.sky.system.mapper;

import com.sky.common.core.domain.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单权限表(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2024-08-02 10:39:09
 */
@Mapper
public interface SysMenuMapper {
    /**
     * 查询所有菜单--管理员
     * @return
     */
    List<SysMenu> selectAllMenus();

    /**
     * 根据用户id查询菜单
     * @param userId
     * @return
     */
    List<SysMenu> selectMenusByUserId(Long userId);
}

