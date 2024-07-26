<template>
  <div class="login-container">
    <div class="left-panel">
      <h1>欢迎登录</h1>
      <p>请在右侧输入您的登录信息</p>
    </div>
    <div class="right-panel">
      <div class="login-box animate__animated animate__fadeInRight">
        <h2 class="login-box-title">欢迎光临</h2>
        <a-form
            :model="loginForm"
            :rules="rules"
            ref="loginRef"
            layout="vertical"
            class="login-form"
            @submit.prevent="handleLogin"
        >
          <a-form-item name="username" label="用户名">
            <a-input
                v-model:value="loginForm.username"
                placeholder="请输入用户名"
                :disabled="loading"
            />
          </a-form-item>
          <a-form-item name="password" label="密码">
            <a-input-password
                v-model:value="loginForm.password"
                placeholder="请输入密码"
                :disabled="loading"
            />
          </a-form-item>
          <a-form-item>
            <a-checkbox v-model:checked="loginForm.remember" :disabled="loading"
            >记住我</a-checkbox
            >
          </a-form-item>
          <a-form-item>
            <a-button
                type="primary"
                html-type="submit"
                :loading="loading"
                :disabled="loading"
                class="login-form-button"
                @click.prevent="handleLogin"
            >
              {{ loading ? '登录中...' : '登录' }}
            </a-button>
          </a-form-item>
        </a-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { getCurrentInstance, reactive, ref, watch } from 'vue';
import Cookies from 'js-cookie';
import useUserStore from '@/store/modules/user';
import { useRoute, useRouter } from 'vue-router';
import { message } from 'ant-design-vue'; // 引入ant-design的message组件

const route = useRoute();
const router = useRouter();
const loginRef = ref();
// 当前组件的实例
const { proxy } = getCurrentInstance();

const redirect = ref(undefined);
watch(
    route,
    (newRoute) => {
      redirect.value = newRoute.query && newRoute.query.redirect;
    },
    { immediate: true }
);

const loginForm = reactive({
  username: '',
  password: '',
  remember: false,
});
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
};

const loading = ref(false);

// 登录
const handleLogin = () => {
  proxy.$refs.loginRef
      .validate()
      .then(() => {
        loading.value = true; // 启动loading状态
        // 勾选记住
        if (loginForm.remember) {
          Cookies.set('username', loginForm.username, { expires: 30 });
          Cookies.set('password', loginForm.password, { expires: 30 });
          Cookies.set('remember', loginForm.remember, { expires: 30 });
        } else {
          Cookies.remove('username');
          Cookies.remove('password');
          Cookies.remove('remember');
        }
        // 调用user.js的action方法
        useUserStore()
            .login(loginForm)
            .then(() => {
              loading.value = false;
              message.success('登录成功！', 2);
              router.push('/');
            })
            .catch((error) => {
              loading.value = false;
              message.error('登录失败，请重试！', 2);
              console.error('登录失败', error);
            });
      })
      .catch((error) => {
        console.log('error', error);
      });
};
</script>

<style scoped>
@import 'https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css';

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: url('../../assets/images/login-background.jpg'); /* 替换为你的背景图片路径 */
  background-size: cover;
  background-position: center;
}

.left-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  text-align: center;
  background-color: transparent; /* 设置为透明 */
}

.right-panel {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  background-color: transparent; /* 设置为透明 */
}

.login-box {
  width: 100%;
  max-width: 400px;
  padding: 40px;
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 20px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.login-box-title {
  margin-bottom: 20px;
}

.login-form {
  width: 100%;
}

.login-form-button {
  width: 100%;
}
</style>
