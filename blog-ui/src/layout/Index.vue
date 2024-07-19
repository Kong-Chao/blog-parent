<template>
  <a-layout class="layout">
    <Sidebar :collapsed = "collapsed" />
    <a-layout>
      <a-layout-header class="header">
        <menu-unfold-outlined
            v-if="collapsed"
            class="trigger"
            @click="() => (collapsed = !collapsed)"
        />
        <menu-fold-outlined
            v-else
            class="trigger"
            @click="() => (collapsed = !collapsed)"
        />
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script>
import { defineComponent, ref } from 'vue';
import Sidebar from "@/layout/module/Sidebar.vue";
import { MenuUnfoldOutlined, MenuFoldOutlined } from '@ant-design/icons-vue';

export default defineComponent({
  components: {
    Sidebar,
    MenuUnfoldOutlined,
    MenuFoldOutlined
  },
  setup() {
    return {
      selectedKeys: ref(['home']),
      collapsed: ref(false),
    };
  },
});
</script>

<style lang="scss" scoped>
.layout {
  min-height: 100vh;

  .logo {
    height: 64px;
    margin: 16px;
    color: #fff;
    text-align: center;
    font-size: 24px;
    line-height: 64px;
  }

  .header {
    background: #fff;
    padding: 0 16px;
    display: flex;
    align-items: center;

    .trigger {
      font-size: 18px;
      cursor: pointer;
      transition: color 0.3s;
    }

    .trigger:hover {
      color: #1890ff;
    }
  }

  .content {
    margin: 24px 16px;
    padding: 24px;
    background: #fff;
    min-height: 280px;
  }
}
</style>
