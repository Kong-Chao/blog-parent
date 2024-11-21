import axios from 'axios';
import {getRefreshToken, getToken, removeRefreshToken, removeToken, setRefreshToken, setToken} from "@/utils/auth";
import {tansParams} from "@/utils/common";
import errorCode from "@/utils/errorCode";
import {message, Modal} from "ant-design-vue";
import useUserStore from "@/store/modules/user";
import {refreshToken} from "@/api/login";

const baseURL = '/dev-api';

// 是否显示重新登录
export let isRelogin = { show: false };

let isRefreshing = false;
let requests = [];

const axiosInstance = axios.create({
    baseURL: baseURL, // 设置基础请求路径
    timeout: 10000, // 请求超时时间（毫秒）
    headers: {
        'X-Requested-With': 'XMLHttpRequest'
    }
});

// 添加请求到待重发队列
function pushRequestToQueue(config, resolve, reject) {
    requests.push({config, resolve, reject});
}

// 执行待重发的请求
function processRequestsQueue(newToken) {
    requests.forEach(({config, resolve}) => {
        config.headers['Authorization'] = `Bearer ${newToken}`;
        resolve(axiosInstance(config));
    });
    requests = [];
}

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
    const code = res.status;
    // 获取错误信息
    const msg = res.data.msg || res.data.repMsg || errorCode[code] || errorCode['default'];
    // 二进制数据直接返回
    if (res.request.responseType === 'blob' || res.request.responseType === 'arraybuffer'){
        return res.data;
    }
    // 验证码
    if (code === 200 && res.data && res.data?.repCode === '0000'){
        return res.data;
    }
    // 业务错误逻辑
    if (code === 200 && res.data && res.data?.code !== 200 && res.data.repCode === '6111' && !msg.includes("token已过期")) {
        message.error(msg);
        return Promise.reject(msg);
    }
    return Promise.resolve(res.data);
},
 error => {
    const code = error.response.status;
    if (code === 401) {
        if (!isRefreshing){
            console.log('Token 过期，执行刷新 Token 的逻辑');
            isRefreshing = true;
            const refresh = getRefreshToken();
            return refreshToken({refreshToken : refresh})
                .then((res) => {
                    const {accessToken,refreshToken} = res.data;
                    setToken(accessToken);
                    setRefreshToken(refreshToken);

                    // 更新待重发的请求的 Token
                    processRequestsQueue(accessToken);

                    return axiosInstance(error.config);
                    })
                .catch(error => {
                    console.error('刷新 Token 失败:', error);
                    if (!isRelogin.show){
                        isRelogin.show = true;
                        Modal.confirm({
                            title: '系统提示',
                            content: '登录状态已过期，您可以继续留在该页面，或者重新登录',
                            okText: '确定',
                            okType: 'danger',
                            cancelText: '取消',
                            onOk() {
                                isRelogin.show = false;
                                useUserStore().logOut().then(() => {
                                    location.href = '/index';
                                })
                            },
                            onCancel(){
                                isRelogin.show = false;
                            }
                        });
                    }
                    return Promise.reject('无效的会话，或者会话已过期，请重新登录。');
                })
                .finally(() => {
                    isRefreshing = false;
                })
        }else {
            // 正在刷新 Token，将当前请求添加到待重发队列
            return new Promise((resolve, reject) => {
                pushRequestToQueue(error.config, resolve, reject);
            });
        }
    }else {
        let {message: msg} = error;
        if (msg === 'Network Error') {
            msg = '后端接口连接异常';
        } else if (msg.includes('timeout')) {
            msg = '系统接口请求超时';
        } else if (msg.includes('Request failed with status code')) {
            msg = '系统接口' + msg.substr(msg.length - 3) + '异常';
        }
        message.error(msg);
        return Promise.reject(error);
    }
 }
);

export default axiosInstance;
