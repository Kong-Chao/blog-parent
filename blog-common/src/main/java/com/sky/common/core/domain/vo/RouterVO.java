package com.sky.common.core.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author admin
 */
@Data
public class RouterVO {
    private String name;
    private String path;
    // 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
    private Boolean hidden;
    // 当一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
    private Boolean alwaysShow;
    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    private String redirect;
    private String component;
    private RouteMeta meta;
    private List<RouterVO> children;

    @Data
    @AllArgsConstructor
    public static class RouteMeta {
        private String title;
        private String icon;
    }
}
