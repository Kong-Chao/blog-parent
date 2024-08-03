import {defineStore} from "pinia";
import {constantRoutes} from "@/router/router";
import {getRouters} from "@/api/menu";

const usePermissionStore = defineStore(
    'permission',
    {
        state: () => ({
            routes: [],
            addRoutes: [],
            defaultRoutes: [],
            topbarRouters: [],
            sidebarRouters: []
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
                return new Promise(resolve => {
                    getRouters().then(res => {
                        const defaultData = JSON.parse(JSON.stringify(res.data));
                        // 过滤动态路由数据
                        console.log(defaultData)
                    })
                    resolve();
                })
            }
        }
    }
)



export default usePermissionStore