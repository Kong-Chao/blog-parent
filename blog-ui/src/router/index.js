import { createRouter, createWebHistory } from "vue-router";
import Login from "@/views/login/Login.vue";
import Layout from "@/layout/Index.vue"

export const constantRoutes = [
    {
        path: "/",
        component: Layout,
        redirect: "/home",
        hidden: false,
        meta: { title: "首页", icon: "DashboardOutlined"},
        children: [
            {
                path: "home",
                name: "Home",
                hidden: true,
                component: () => import("@/views/home/Home.vue"),
            },
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
        hidden: true,
    },
    {
        path: "/user",
        component: Layout,
        hidden: true,
        children: [
            {
                path: "profile",
                name: "Profile",
                hidden: false,
                component: () => import("@/views/system/user/profile/index.vue"),
                meta: { title: '个人中心', },
            },
        ],
    },
    {
        path: "/:pathMatch(.*)*",  // 捕获所有未匹配的路由
        hidden: true,
        component: () => import("@/views/error/404.vue"),
    }
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
