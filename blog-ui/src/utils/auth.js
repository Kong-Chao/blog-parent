import Cookies from "js-cookie";

const Tokenkey = "Blog-Admin-Token"
const RefreshTokenkey = "Blog-Admin-RefreshToken"


export function getToken() {
    return Cookies.get(Tokenkey)
}

export function setToken(token){
    return Cookies.set(Tokenkey,token)
}

export function removeToken(){
    return Cookies.remove(Tokenkey)
}

export function getRefreshToken() {
    return Cookies.get(RefreshTokenkey);
}

export function setRefreshToken(refreshToken) {
    return Cookies.set(RefreshTokenkey, refreshToken);
}

export function removeRefreshToken() {
    return Cookies.remove(RefreshTokenkey);
}
