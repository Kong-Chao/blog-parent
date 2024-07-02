import axios from "axios";

// 创建axios的实例
const axiosInstance  = axios.create({
    baseURL: 'http://api.blog.com', // 设置基础请求路径
    timeout: 10000, // 请求超时时间（毫秒）
    headers: {
        'Content-Type': 'application/json;charset=utf-8',  // 设置请求头部
    }
})

// request拦截器
axiosInstance.interceptors.request.use(
    (config) => {
        // 可在此处修改请求配置，如添加 token 到 headers
        // config.headers['Authorization'] = 'Bearer token';
        return config;
    },
    (error) => {
        console.log(error);
        return Promise.reject(error);
    }
);

// response拦截器
axiosInstance.interceptors.response.use(
    (response) => {
        // 可在此处统一处理成功响应数据
        return response.data;
    },
    (error) => {
        console.log(error);
        // 可在此处统一处理请求失败（如请求错误、网络错误等）
        return Promise.reject(error);
    }
)

export default axiosInstance;
