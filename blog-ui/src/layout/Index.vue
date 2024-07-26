<template>
  <a-layout class="layout">
    <Sidebar :collapsed="collapsed" />
    <a-layout class="site-layout" :class="{ collapsed }">
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
        <!-- 在这里添加面包屑 -->
        <Breadcrumb />
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
import {Avatar, Dropdown, Menu, Modal} from 'ant-design-vue';
import Breadcrumb from '@/layout/module/Breadcrumb.vue';
import useUserStore from "@/store/modules/user"; // 引入面包屑组件

export default defineComponent({
  components: {
    Sidebar,
    Breadcrumb, // 注册面包屑组件
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
      Modal.confirm({
        title: '确认退出登录',
        content: '您确定要退出登录吗？',
        okText: '确认',
        cancelText: '取消',
        onOk() {
          useUserStore().logOut().then(() => {
            location.href = '/login';
          })
        },
        onCancel() {

        }
      })
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

  .header {
    background: #fff;
    padding: 0 16px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    z-index: 10;
    height: 64px; // 确保与侧边栏一致的高度

    .trigger-wrapper {
      display: flex;
      align-items: center;
      margin-right: 16px; // 右边距与 breadcrumb 的间距一致

      .trigger {
        font-size: 18px;
        cursor: pointer;
        transition: color 0.3s;
      }

      .trigger:hover {
        color: #1890ff;
      }
    }

    .breadcrumb {
      flex-grow: 1;
      margin: 0; // 移除上下边距
      padding: 8px 16px; // 设置适当的内边距
      background-color: transparent;
      display: flex;
      align-items: center; // 垂直居中对齐
      text-align: center; // 使面包屑内容居中
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
