import { createApp } from 'vue'
import App from './App.vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import router from "@/router/router";
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import Cookies from 'js-cookie'

const app = createApp(App)
app.use(VueAxios,axios)
app.use(router)
// 注册 Ant Design Vue
app.use(Antd)
app.use(Cookies)
app.mount('#app')
