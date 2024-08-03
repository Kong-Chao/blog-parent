import request from "@/utils/request";

// 登录请求
export function login(data) {
    return request({
        url: '/system/auth/login',
        headers: {
            // 默认登录请求不包含token
            isToken: false,
            // 防止数据重复提交
            repeatSubmit: false
        },
        method: 'post',
        data: data
    })
}

// 退出方法
export function logout() {
    return request({
        url: '/logout',
        method: 'post'
    })
}

export function getInfo() {
    return request({
        url: '/system/auth/getInfo',
        method: 'get'
    })
}

// 刷新token
export function refreshToken(data){
    return request({
        url: '/system/auth/refresh',
        method: 'post',
        data: data
    })
}

