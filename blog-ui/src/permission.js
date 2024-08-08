import router from "@/router";
import NProgress from "nprogress";
import {getToken} from "@/utils/auth";
import useUserStore from "@/store/modules/user";
import {isRelogin} from "@/utils/request";
import usePermissionStore from "@/store/modules/permission";
import {message} from "ant-design-vue";
import {isHttp} from "@/utils/validate";

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
                        // 根据roles权限生成可访问的路由表
                        accessRoutes.forEach((route) => {
                            if (!isHttp(route.path)){
                                router.addRoute(route);
                            }
                        });
                        // 确保 Vue Router 实例已更新后再进行导航
                        next({ ...to, replace: true });
                    })
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