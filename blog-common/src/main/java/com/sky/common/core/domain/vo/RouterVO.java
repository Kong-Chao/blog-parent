package com.sky.common.core.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author admin
 */
@Data
public class RouterVO {
    private String path;
    private String name;
    private String component;
    private RouteMeta meta;
    private List<RouterVO> children;


    @Data
    @AllArgsConstructor
    public static class RouteMeta {
        private String title;
        private String icon;
        private String perms;
        private boolean cache;
        private boolean visible;
        private boolean frame;
    }
}
