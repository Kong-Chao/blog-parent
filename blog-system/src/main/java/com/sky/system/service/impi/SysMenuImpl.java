package com.sky.system.service.impi;

import cn.hutool.core.util.StrUtil;
import com.sky.common.core.domain.entity.SysMenu;
import com.sky.common.core.domain.vo.RouterVO;
import com.sky.system.mapper.SysMenuMapper;
import com.sky.system.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
            vo.setPath(getPath(menu,parentId));
            vo.setName(getRouteName(menu.getRouteName(),menu.getPath()));
            vo.setComponent(getComponent(menu,menuList));
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

    private String getRouteName(String routeName,String path){
         String name = StrUtil.isEmpty(routeName)? path:routeName;
         return StringUtils.capitalize(name);
    }

    private String getPath(SysMenu menu, Long parentId){
        if (isHttpUrl(menu.getPath())){
            return menu.getPath();
        }
        if (0L == parentId){
            return "/" + menu.getPath();
        }
        return menu.getPath();
    }

    private String getComponent(SysMenu menu,List<SysMenu> menuList){
        // 目录-M
        if (Objects.equals(menu.getMenuType(),"M") && 0 == menu.getParentId()){
            // 顶层或父级菜单，使用Layout作为容器
            return "Layout";
        }
        // 目录M -父级菜单
        if (Objects.equals(menu.getMenuType(),"M") && StrUtil.isEmpty(menu.getComponent())){
            return "ParentView";
        }
        // C - 菜单
        if (Objects.equals(menu.getMenuType(),"C")){
           return menu.getComponent();
        }

        // F - 按钮 (不用于路由)
        return null;
    }

    private boolean isHttpUrl(String path) {
        return StrUtil.isNotEmpty(path) && (path.startsWith("http://") || path.startsWith("https://"));
    }

}
