import axios from 'axios';
import {getToken} from "@/utils/auth";
import {tansParams} from "@/utils/common";
import errorCode from "@/utils/errorCode";
import {message, Modal} from "ant-design-vue";
import useUserStore from "@/store/modules/user";
import res from "core-js/internals/is-forced";

const baseURL = '/dev-api';

// 是否显示重新登录
export let isRelogin = { show: false };

const axiosInstance = axios.create({
    baseURL: baseURL, // 设置基础请求路径
    timeout: 10000 // 请求超时时间（毫秒）
});

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'

// 请求拦截器
axiosInstance.interceptors.request.use(
    config => {
        // 是否需要设置 token
        const isToken = (config.headers || {}).isToken === false
        // 是否需要防止数据重复提交
        const isRepeatSubmit = (config.headers || {}).repeatSubmit === false
        if (getToken() && !isToken) {
            config.headers['Authorization'] = 'Bearer ' + getToken(); // 让每个请求携带自定义token 请根据实际情况自行修改
        }

        // get请求映射params参数
        if (config.method === 'get' && config.params) {
            let url = config.url + '?' + tansParams(config.params);
            url = url.slice(0, -1);
            config.params = {};
            config.url = url;
        }
        if (!isRepeatSubmit && (config.method === 'post' || config.method === 'put')) {
            const requestObj = {
                url: config.url,
                data: typeof config.data === 'object' ? JSON.stringify(config.data) : config.data,
                time: new Date().getTime()
            }
            const requestSize = Object.keys(JSON.stringify(requestObj)).length; // 请求数据大小
            const limitSize = 5 * 1024 * 1024; // 限制存放数据5M
            if (requestSize >= limitSize) {
                console.warn(`[${config.url}]: ` + '请求数据大小超出允许的5M限制，无法进行防重复提交验证。')
                return config;
            }
        }
        return config;
    },
    error => {
        console.log(error)
        return Promise.reject(error);
    }
);

// 响应拦截器
axiosInstance.interceptors.response.use(res => {
    // 未设置状态码则默认为成功状态
    const code = res.data.code || 200;
    // 获取错误信息
    const msg = res.data.msg || errorCode[code] || errorCode['default'];
    // 二进制数据直接返回
    if (res.request.responseType === 'blob' || res.request.responseType === 'arraybuffer'){
        return res.data;
    }
    if (code === 401) {
        if (!isRelogin.show){
            isRelogin.show = true;
            Modal.confirm({
                title: '系统提示',
                content: '登录状态已过期，您可以继续留在该页面，或者重新登录',
                okText: '重新登录',
                cancelText: '取消',
                iconType: 'warning',
                onOk() {
                    isRelogin.show = false;
                    // 执行系统退出操作
                    useUserStore().logOut().then(() => {
                        location.href = '/login';
                    }).catch(() =>{
                        isRelogin.show = false;
                    })
                }
            });
        }
        return Promise.reject('无效的会话，或者会话已过期，请重新登录。')
    }else if(code === 500) {
        message.error(msg);
        return Promise.reject(new Error(msg));
    }else if (code !== 200) {
        message.error({ title: msg })
        return Promise.reject('error')
    } else {
        return  Promise.resolve(res.data)
    }
},
error => {
    console.log('err',error);
    let {message} = error;
    if (message === "Network Error"){
        message = "后端接口连接异常";
    }else if(message.includes("timeout")){
        message = "系统接口请求超时";
    }
    return Promise.reject(error)
    }
);

export default axiosInstance;
