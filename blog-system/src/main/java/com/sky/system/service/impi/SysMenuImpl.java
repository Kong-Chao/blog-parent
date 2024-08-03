package com.sky.system.service.impi;

import com.sky.common.core.domain.entity.SysMenu;
import com.sky.common.core.domain.vo.RouterVO;
import com.sky.system.mapper.SysMenuMapper;
import com.sky.system.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author admin
 */
@Service
@Slf4j
public class SysMenuImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 查询并构建路由
     *
     * @return
     */
    @Override
    public List<RouterVO> getRoutes() {
        List<SysMenu> sysMenus = sysMenuMapper.selectAllMenus();
        return buildRouteTree(sysMenus,0L);
    }

    private List<RouterVO> buildRouteTree(List<SysMenu> menuList, Long parentId){
        List<RouterVO> routers = new ArrayList<>();

        List<SysMenu> filteredMenus  = menuList.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .sorted(Comparator.comparingInt(SysMenu::getOrderNum))
                .collect(Collectors.toList());
        for (SysMenu menu : filteredMenus) {
            RouterVO vo = new RouterVO();
            vo.setPath(menu.getPath());
            vo.setName(menu.getRouteName());
            vo.setComponent(menu.getComponent());
            vo.setMeta(new RouterVO.RouteMeta(
                    menu.getMenuName(),
                    menu.getIcon(),
                    menu.getPerms(),
                    Objects.equals(menu.getIsCache(), "0"),
                    Objects.equals(menu.getVisible(), "0"),
                    Objects.equals(menu.getIsFrame(), "0")
            ));

            // 递归添加子节点
            List<RouterVO> children = buildRouteTree(menuList,menu.getMenuId());
            if (!children.isEmpty()){
                vo.setChildren(children);
            }
            routers.add(vo);
        }
        return routers;
    }

}
