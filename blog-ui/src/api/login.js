import request from "@/utils/request";

// 登录请求
export function login(data) {
    return request({
        url: '/system/auth/login',
        method: 'post',
        data: data
    })
}
