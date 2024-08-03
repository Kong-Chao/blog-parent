import { createRouter, createWebHistory } from "vue-router";
import Login from "@/views/login/Login.vue";
import NProgress from "nprogress";
import {getToken} from "@/utils/auth";
import useUserStore from "@/store/modules/user";
import {isRelogin} from "@/utils/request";
import Layout from "@/layout/Index.vue"
import {message} from "ant-design-vue";
import usePermissionStore from "@/store/modules/permission";

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
        name: '登录',
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

NProgress.configure({ showSpinner: false });

const whiteList = ['/login', '/register'];

// 全局路由守卫
router.beforeEach((to, from, next) => {
    NProgress.start()
    if (getToken()) {
        /* has token*/
        if (to.path === '/login') {

            next({ path: '/' })
            NProgress.done()
        }else if (whiteList.indexOf(to.path) !== -1){
            next()
        }else {
            if (useUserStore().roles.length === 0){
                isRelogin.show = true
                // 判断当前用户是否已拉取完user_info信息
                useUserStore().getInfo().then(() => {
                    isRelogin.show = false
                    // 获取路由处理
                    usePermissionStore().generateRoutes().then(accessRoutes => {
                            console.log(accessRoutes)
                        })
                    router.push({ path: '/' })
                }).catch(err => {
                    useUserStore().logOut().then(() => {
                        message.error(err)
                        next({ path: '/' })
                    })
                })
            }else {
                next()
            }
        }
    }else {
        // 没有token
        if (whiteList.indexOf(to.path) !== -1) {
            // 在免登录白名单，直接进入
            next()
        } else {
            next(`/login?redirect=${to.fullPath}`) // 否则全部重定向到登录页
            NProgress.done()
        }
    }
})

router.afterEach(() => {
    NProgress.done()
})

export default router;
