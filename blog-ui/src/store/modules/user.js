import {login} from "@/api/login";
import {defineStore} from "pinia";

const useUserStore = defineStore(
    'user',
    {
        state: ()=> ({}),
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
                        console.log(res.data);
                        resolve();
                    }).catch(error => {
                        reject(error);
                    })
                })
            },
        },
    }
)

export default useUserStore;
