import axios from 'axios';
import {getToken} from "@/utils/auth";
import {tansParams} from "@/utils/common";

const baseURL = '/dev-api';

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
axiosInstance.interceptors.response.use(
    response => {
        return response.data;
    },
    error => {
        return Promise.reject(error);
    }
);

export default axiosInstance;
