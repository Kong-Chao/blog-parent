import {getInfo, login, logout} from "@/api/login";
import {defineStore} from "pinia";
import {getRefreshToken, getToken, removeRefreshToken, removeToken, setRefreshToken, setToken} from "@/utils/auth";

const useUserStore = defineStore(
    'user',
    {
        state: ()=> ({
            token: getToken(),
            refreshToken: getRefreshToken(),
            id: '',
            name: '',
            avatar: '',
            roles: [],
            permissions: []
        }),
        actions: {
           // 登录
            login(userInfo) {
                const userData = {
                    username : userInfo.username.trim(),
                    password : userInfo.password,
                    code: userInfo.code
                };
                return new Promise((resolve, reject) => {
                    login(userData).then(res => {
                        const {accessToken, refreshToken} = res.data;
                        setToken(accessToken);
                        setRefreshToken(refreshToken);
                        resolve();
                    }).catch(error => {
                        reject(error);
                    })
                })
            },
            // 登出
            logOut() {
                return new Promise((resolve, reject) => {
                    logout(this.token).then(()=>{
                        this.token = '';
                        this.refreshToken = '';
                        this.roles = [];
                        this.permissions = [];
                        removeToken();
                        removeRefreshToken();
                        resolve()
                    }).catch(error => {
                        console.log('error', error);
                        reject(error);
                    })
                })
            },
            // 获取用户信息
            getInfo(){
                return new Promise((resolve, reject) => {
                    getInfo().then(res=>{
                        const user = res.data;
                        this.id = user.userid;
                        this.name = user.username;
                        this.avatar = user.avatar;
                        if (user.roles && user.roles.length > 0) {
                            this.roles = user.roles;
                            this.permissions = user.permissions;
                        }else {
                            this.roles = ['ROLE_DEFAULT']
                        }
                        resolve(res);
                    }).catch(error => {
                        reject(error);
                    })
                })
            }
        },
    }
)

export default useUserStore;
