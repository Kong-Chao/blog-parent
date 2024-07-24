<template>
  <a-breadcrumb class="breadcrumb">
    <a-breadcrumb-item>
      <router-link to="/">首页</router-link>
    </a-breadcrumb-item>
    <a-breadcrumb-item v-for="(breadcrumb, index) in breadcrumbs" :key="index">
      <router-link :to="breadcrumb.path">{{ breadcrumb.meta.title }}</router-link>
    </a-breadcrumb-item>
  </a-breadcrumb>
</template>

<script>
import { defineComponent, computed } from 'vue';
import { useRoute } from 'vue-router';

export default defineComponent({
  name: 'Breadcrumb',
  setup() {
    const route = useRoute();

    // 动态生成面包屑
    const breadcrumbs = computed(() => {
      const matched = route.matched.filter((item) => item.meta && item.meta.title);
      return matched.map((item) => ({
        path: item.path,
        meta: item.meta,
      }));
    });

    return {
      breadcrumbs,
    };
  },
});
</script>

<style lang="scss" scoped>
.breadcrumb {
  margin: 0; // 移除上下边距
  padding: 8px 16px; // 设置适当的内边距
  background-color: transparent;
  display: flex;
  align-items: center; // 垂直居中对齐
  flex-grow: 1; // 使面包屑占据剩余空间
  text-align: center; // 确保面包屑内容在容器中居中
  height: 100%; // 确保高度填满父容器
  box-sizing: border-box; // 确保内边距和边框不影响宽度计算

  a {
    color: #1890ff;
    text-decoration: none;
    margin-right: 8px; // 设置链接间距

    &:hover {
      color: #40a9ff;
    }
  }
}
</style>
