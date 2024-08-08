import { createApp } from 'vue'
import App from './App.vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import index from "@/router";
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import Cookies from 'js-cookie'
import {createPinia} from "pinia";
import './permission' // 全局路由守卫--permission control

const app = createApp(App)
const pinia = createPinia();
app.use(pinia);
app.use(VueAxios,axios)
app.use(index)
// 注册 Ant Design Vue
app.use(Antd)
app.use(Cookies)
app.mount('#app')
