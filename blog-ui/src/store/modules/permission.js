import {defineStore} from "pinia";
import {constantRoutes} from "@/router";
import {getRouters} from "@/api/menu";
import Layout from "@/layout/Index.vue";
import ParentView from "@/components/ParentView/index.vue";

const usePermissionStore = defineStore(
    'permission',
    {
        state: () => ({
            routes: [],
            addRoutes: [],
            defaultRoutes: [],
        }),
        actions: {
            setRoutes(routes) {
                this.addRoutes = routes
                this.routes = constantRoutes.concat(routes)
            },
            setDefaultRoutes(routes) {
                this.defaultRoutes = constantRoutes.concat(routes)
            },
            generateRoutes(){
                return new Promise((resolve,reject) => {
                    getRouters().then(res => {
                        const rdata = JSON.parse(JSON.stringify(res.data));
                        const rewriteRoutes = filterAsyncRouter(rdata)
                        this.setRoutes(rewriteRoutes);
                        resolve(rewriteRoutes); // 返回生成的动态路由
                    }).catch((error) =>{
                        reject(error);
                    })
                })
            }
        }
    }
)

// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap) {
     asyncRouterMap.forEach(route => {
        if (route.component) {
            // Layout ParentView 组件特殊处理
            if (route.component === 'Layout') {
                route.component = Layout;
            }
            else if (route.component === 'ParentView') {
                route.component = ParentView;
            }else {
                route.component = loadView(route.component);
            }
        }
        if (route.children !== null && route.children && route.children.length) {
            route.children = filterAsyncRouter(route.children);
        }else {
            delete route['children'];
        }

        if (route.meta) {
            route.meta = {
                ...route.meta,
                title: route.meta.title || '',
                icon: route.meta.icon || '',
                cache: route.meta.cache || false,
                visible: route.meta.visible !== false,
                perms: route.meta.perms || '',
                frame: route.meta.frame || false,
            }
        }
    });

     return asyncRouterMap;
}

function filterChildren(childrenMap, lastRouter = false) {
    var children = []
    childrenMap.forEach((el) => {
        if (el.children && el.children.length) {
            if (el.component === 'ParentView' && !lastRouter) {
                el.children.forEach(c => {
                    c.path = el.path + '/' + c.path
                    if (c.children && c.children.length) {
                        children = children.concat(filterChildren(c.children, c))
                        return
                    }
                    children.push(c)
                })
                return
            }
        }
        if (lastRouter) {
            el.path = lastRouter.path + '/' + el.path
            if (el.children && el.children.length) {
                children = children.concat(filterChildren(el.children, el))
                return
            }
        }
        children = children.concat(el)
    })
    return children
}

function loadView(view) {
    return () => import(`@/views/${view}.vue`);
}

export default usePermissionStore