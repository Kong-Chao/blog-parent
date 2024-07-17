import { createRouter, createWebHistory } from "vue-router";
import Login from "@/views/login/Login.vue";
import Layout from "@/layout/Index.vue";

export const constantRoutes = [
    {
        path: "/",
        component: Layout,
        children: [
            {
                path: "",
                name: "Home",
                component: () => import("@/views/home/Home.vue"),
            },
        ],
    },
    {
        path: "/login",
        name: "登录",
        component: Login,
        meta: { hidden: true },
    },
    {
        path: "/system",
        component: Layout,
        meta: { title: "系统管理" },
        children: [
            {
                path: "user",
                name: "User",
                component: () => import("@/views/system/User.vue"),
                meta: { title: "用户管理" },
            },
            {
                path: "menu",
                name: "Menu",
                component: () => import("@/views/system/Menu.vue"),
                meta: { title: "菜单管理" },
            },
            {
                path: "permission",
                name: "Permission",
                component: () => import("@/views/system/Permission.vue"),
                meta: { title: "权限管理" },
            },
        ],
    },
    {
        path: "/log",
        component: Layout,
        meta: { title: "日志管理" },
        children: [
            {
                path: "systemLog",
                name: "SystemLog",
                component: () => import("@/views/log/Log.vue"),
                meta: { title: "日志管理" },
            },
        ],
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes: constantRoutes,
});

export default router;
