import { createRouter, createWebHistory } from "vue-router";
import Login from "@/views/login/Login.vue";
import Layout from "@/layout/Index.vue";

export const constantRoutes = [
    {
        path: "/",
        component: Layout,
        redirect: "/home",
        meta: { title: "首页", icon: "DashboardOutlined", visible: true },
        children: [
            {
                path: "home",
                name: "Home",
                component: () => import("@/views/home/Home.vue"),
                meta: { title: '首页', visible: true },
            },
        ],
    },
    {
        path: '/a',
        component: Layout,
        meta: { title: "测试", icon: "DashboardOutlined", visible: true },
        children: [
            {
                path: 'b',
                name: 'Test',
                component: () => import('@/views/test/a.vue'),
                meta: { title: '业务数据', icon: "DashboardOutlined", visible: true },
                children: [
                    {
                        path: 'c',
                        name: 'SubTest1',
                        component: () => import('@/views/test/SubTest.vue'),
                        meta: { title: '子测试1', icon: "DashboardOutlined", visible: true },
                        children: [
                            {
                                path: 'd',
                                name: 'SubTest2',
                                component: () => import('@/views/test/SubTest1.vue'),
                                meta: { title: '子测试2', icon: "DashboardOutlined", visible: true },
                                children: [
                                    {
                                        path: 'e',
                                        name: 'SubTest3',
                                        component: () => import('@/views/test/SubTest2.vue'),
                                        meta: { title: '子测试3', icon: "DashboardOutlined", visible: true },
                                    },
                                ]
                            },
                        ]
                    },
                ]
            },
        ],
    },
    {
        path: '/login',
        name: '登录',
        component: Login,
        meta: { hidden: true, visible: true },
    },
    {
        path: "/system",
        component: Layout,
        meta: { title: "系统管理", icon: "SettingOutlined", visible: true },
        children: [
            {
                path: "user",
                name: "User",
                component: () => import("@/views/system/User.vue"),
                meta: { title: '用户管理', icon: 'UserOutlined', visible: true },
            },
            {
                path: "menu",
                name: "Menu",
                component: () => import("@/views/system/Menu.vue"),
                meta: { title: '菜单管理', icon: 'MenuOutlined', visible: true },
            },
            {
                path: "permission",
                name: "Permission",
                component: () => import("@/views/system/Permission.vue"),
                meta: { title: '权限管理', icon: 'LockOutlined', visible: true },
            },
        ],
    },
    {
        path: "/log",
        component: Layout,
        meta: { title: "日志管理", icon: 'FileOutlined', visible: true },
        children: [
            {
                path: "systemLog",
                name: "SystemLog",
                component: () => import("@/views/log/Log.vue"),
                meta: { title: '系统日志', icon: 'FileExcelOutlined', visible: true },
            },
        ],
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes: constantRoutes,
});

export default router;
