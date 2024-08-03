package com.sky.system.service;

import com.sky.common.core.domain.vo.RouterVO;

import java.util.List;

/**
 * @author admin
 */
public interface SysMenuService {

    /**
     * 查询并构建路由
     * @return
     */
    public List<RouterVO> getRoutes();
}
