<template>
  <a-layout class="layout">
    <Sidebar :collapsed="collapsed" />
    <a-layout class="site-layout"> <!-- 添加一个class以便于样式控制 -->
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
          <a-avatar :src="require('@/assets/images/avatar.jpg')" />
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
  min-height: 100vh; // 确保布局占据整个视口高度

  .site-layout {
    margin-left: 200px; // 默认情况下侧边栏的宽度
    transition: margin-left 0.2s; // 为侧边栏的展开/折叠添加动画过渡

    &.collapsed {
      margin-left: 80px; // 侧边栏折叠时的宽度
    }
  }

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
    z-index: 10; // 确保header的层级高于内容区域

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
    min-height: calc(100vh - 64px); // 确保内容区域最小高度
    overflow: auto; // 防止内容过多时溢出

    @media (max-width: 768px) {
      margin: 0; // 在较小屏幕上取消内边距
      padding: 16px; // 调整内边距以适应小屏幕
    }
  }
}
</style>
