import router from "@/router/router";
import NProgress from 'nprogress'

// 全局路由守卫
router.beforeEach((to, from, next) => {
    next(`/login?redirect=${to.fullPath}`) // 否则全部重定向到登录页
})
