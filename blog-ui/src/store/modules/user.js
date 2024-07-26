import {login, logout} from "@/api/login";
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
                    logout(this.token).then(res=>{
                        console.log(res);
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
            }
        },
    }
)

export default useUserStore;
