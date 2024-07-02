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
            @submit.prevent="handleLogin"
            layout="vertical"
            class="login-form"
        >
          <a-form-item name="username" label="用户名" :rules="[{ required: true, message: '请输入用户名' }]">
            <a-input v-model:value="loginForm.username" placeholder="请输入用户名" />
          </a-form-item>
          <a-form-item name="password" label="密码" :rules="[{ required: true, message: '请输入密码' }]">
            <a-input-password v-model:value="loginForm.password" placeholder="请输入密码" />
          </a-form-item>
          <a-form-item>
            <a-checkbox v-model:checked="loginForm.remember">记住我</a-checkbox>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" html-type="submit" class="login-form-button">
              登录
            </a-button>
          </a-form-item>
        </a-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import {message} from 'ant-design-vue';
import {login} from "@/api/login";

const loginForm = ref({
  username: 'admin',
  password: 'admin123',
  remember: false,
  captchaVerification: ''
});

const rules = ref({
  username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
  password: [{required: true, message: '请输入密码', trigger: 'blur'}],
});

const handleLogin = () => {
  if (!loginForm.value.username || !loginForm.value.password) {
    message.error('请输入用户名和密码');
    return;
  }
  const loginData = {
    username: loginForm.value.username,
    password: loginForm.value.password
  };

  const res = login(loginData);
  console.log(res.data);
  // 模拟登录
  message.success('登录成功');
};
</script>

<style scoped>
@import 'https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css';

.login-container {
  display: flex;
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
  color: white;
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
  width: 400px;
  padding: 40px;
  background-color: white;
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
