import { createRouter, createWebHistory } from "vue-router";
import Login from "@/views/login/Login.vue";
import Layout from "@/layout/Index.vue"

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
                meta: { title: '', visible: false },
            },
        ],
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
        meta: {
            visible: false
        },
    },
    {
        path: "/user",
        component: Layout,
        meta: { visible: false },
        children: [
            {
                path: "profile",
                name: "Profile",
                component: () => import("@/views/system/user/profile/index.vue"),
                meta: { title: '个人中心', visible: false },
            },
        ],
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes: constantRoutes,
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition
        } else {
            return { top: 0 }
        }
    },
});

export default router;
