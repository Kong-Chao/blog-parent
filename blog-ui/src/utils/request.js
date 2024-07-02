// src/utils/request.js
import axios from 'axios';

const baseURL = '/dev-api';

const axiosInstance = axios.create({
    baseURL: baseURL, // 设置基础请求路径
    timeout: 10000 // 请求超时时间（毫秒）
});

// 请求拦截器
axiosInstance.interceptors.request.use(
    config => {
        return config;
    },
    error => {
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
