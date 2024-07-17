<!-- src/layouts/index.vue -->
<template>
  <a-layout style="min-height: 100vh">
    <a-layout-sider collapsible v-model:collapsed="collapsed" @collapse="onCollapse">
      <div class="logo" />
      <sidebar-content />
    </a-layout-sider>
    <a-layout>
      <a-layout-header style="background: #fff; padding: 0 16px;">
        <a-icon
            class="trigger"
            :type="collapsed ? 'menu-unfold' : 'menu-fold'"
            @click="toggleCollapse"
        />
      </a-layout-header>
      <a-layout-content style="margin: 0 16px;">
        <router-view />
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script setup>
import { ref } from 'vue';
import SidebarContent from '@/layout/module/SidebarContent.vue';

const collapsed = ref(false);

const onCollapse = (collapsed) => {
  console.log(collapsed);
};

const toggleCollapse = () => {
  collapsed.value = !collapsed.value;
};
</script>

<style>
.logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}
.trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 16px;
  cursor: pointer;
  transition: color 0.3s;
}
.trigger:hover {
  color: #1890ff;
}
</style>
