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
