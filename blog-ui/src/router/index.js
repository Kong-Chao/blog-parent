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
                meta: { title: '首页', visible: true },
            },
        ],
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
        meta: { hidden: true, visible: true },
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
