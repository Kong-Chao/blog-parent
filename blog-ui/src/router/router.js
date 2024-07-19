import { createRouter, createWebHistory } from "vue-router";
import Login from "@/views/login/Login.vue";
import Layout from "@/layout/Index.vue";

export const constantRoutes = [
    {
        path: "/",
        component: Layout,
        meta: { title: "首页",icon: "DashboardOutlined"},
        redirect: "/home",
        children: [
            {
                path: "home",
                name: "Home",
                component: () => import("@/views/home/Home.vue"),
                meta: { title: '首页' },  // 添加title属性
            },
        ],
    },
    {
        path: '/login',
        name: '登录',
        component: Login,
        meta: { hidden: true },
    },
    {
        path: "/system",
        component: Layout,
        meta: { title: "系统管理",icon: "SettingOutlined" },
        children: [
            {
                path: "user",
                name: "User",
                component: () => import("@/views/system/User.vue"),
                meta: { title: '用户管理', icon: 'UserOutlined' },
            },
            {
                path: "menu",
                name: "Menu",
                component: () => import("@/views/system/Menu.vue"),
                meta: { title: '菜单管理', icon: 'MenuOutlined' },
            },
            {
                path: "permission",
                name: "Permission",
                component: () => import("@/views/system/Permission.vue"),
                meta: { title: '权限管理', icon: 'LockOutlined' },
            },
        ],
    },
    {
        path: "/log",
        component: Layout,
        meta: { title: "日志管理" ,icon: 'FileOutlined'},
        children: [
            {
                path: "systemLog",
                name: "SystemLog",
                component: () => import("@/views/log/Log.vue"),
                meta: { title: '系统日志', icon: 'FileExcelOutlined' },
            },
        ],
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes: constantRoutes,
});

export default router;
