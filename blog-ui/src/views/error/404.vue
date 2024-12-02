<template>
  <a-result status="404" title="404" sub-title="对不起，您访问的页面不存在.">
    <template #extra>
      <a-button type="primary" @click="goHome">返回首页</a-button>
    </template>
  </a-result>
</template>

<script setup>
import {useRouter} from "vue-router";

const router = useRouter();
const goHome = () => {
  router.push('/')  // 跳转到首页
}
</script>
