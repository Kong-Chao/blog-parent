<template>
  <a-layout class="layout">
    <Sidebar :collapsed="collapsed" />
    <a-layout>
      <a-layout-header class="header">
        <div class="trigger-wrapper">
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
        </div>
        <div class="user-profile">
          <a-avatar :src="require('@/assets/images/avatar.jpg')"/>
          <a-dropdown>
            <template #overlay>
              <a-menu>
                <a-menu-item key="1">
                  <router-link to="/profile">个人中心</router-link>
                </a-menu-item>
                <a-menu-item key="2">
                  <a @click="handleLogout">退出登录</a>
                </a-menu-item>
              </a-menu>
            </template>
            <a class="ant-dropdown-link" @click="e => e.preventDefault()">
              小蜜瓜<a-icon type="down" />
            </a>
          </a-dropdown>
        </div>
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
import { Avatar, Dropdown, Menu } from 'ant-design-vue';

export default defineComponent({
  components: {
    Sidebar,
    MenuUnfoldOutlined,
    MenuFoldOutlined,
    'a-avatar': Avatar,
    'a-dropdown': Dropdown,
    'a-menu': Menu,
    'a-menu-item': Menu.Item,
    'a-icon': () => import('@ant-design/icons-vue').then((module) => module.default),
  },
  setup() {
    const handleLogout = () => {
      // 处理退出登录逻辑
    };

    return {
      collapsed: ref(false),
      handleLogout,
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
    justify-content: space-between;

    .trigger-wrapper {
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

    .user-profile {
      display: flex;
      align-items: center;

      .ant-avatar {
        margin-right: 8px;
      }

      .ant-dropdown-link {
        cursor: pointer;
      }
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
