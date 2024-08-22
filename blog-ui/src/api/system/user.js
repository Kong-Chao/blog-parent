import request from "@/utils/request";

// 获取路由
export const listUser = (query) => {
    return request({
        url: '/v1/test',
        method: 'GET',
        params: query,
    })
}