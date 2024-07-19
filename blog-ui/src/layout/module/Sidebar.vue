<template>
  <a-layout-sider v-model:collapsed="computedCollapsed" :trigger="null" collapsible>
    <div class="logo">My App</div>
    <a-menu theme="dark" mode="inline" v-model:selectedKeys="selectedKeys">
      <a-menu-item key="home">
        <home-outlined />
        <span>首页</span>
      </a-menu-item>
      <a-menu-item key="user">
        <user-outlined />
        <span>用户管理</span>
      </a-menu-item>
      <a-menu-item key="video">
        <video-camera-outlined />
        <span>视频管理</span>
      </a-menu-item>
      <a-menu-item key="upload">
        <upload-outlined />
        <span>上传管理</span>
      </a-menu-item>
      <a-sub-menu key="sub4">
        <template #icon>
          <SettingOutlined />
        </template>
        <template #title>Navigation Three</template>
        <a-menu-item key="9">Option 9</a-menu-item>
        <a-menu-item key="10">Option 10</a-menu-item>
        <a-menu-item key="11">Option 11</a-menu-item>
        <a-menu-item key="12">Option 12</a-menu-item>
      </a-sub-menu>
    </a-menu>
  </a-layout-sider>
</template>

<script>
import {computed, defineComponent, ref, watch} from 'vue';
import {
  HomeOutlined,
  UserOutlined,
  VideoCameraOutlined,
  UploadOutlined,
  SettingOutlined
} from '@ant-design/icons-vue';

export default defineComponent({
  props: {
    collapsed: {
      type: Boolean,
      default: false
    }
  },
  setup(props) {
    const  computedCollapsed = computed(() => props.collapsed);
    const selectedKeys = ref(['home']);
    const openKeys = ref([]);
    const handleClick = e => {
      console.log('click', e);
    };
    const titleClick = e => {
      console.log('titleClick', e);
    };
    watch(
        () => openKeys.value,
        val => {
          console.log('openKeys', val);
        },
    );
    return {
      selectedKeys,
      openKeys,
      handleClick,
      titleClick,
      computedCollapsed
    };
  },
  components: {
    UploadOutlined,
    HomeOutlined,
    VideoCameraOutlined,
    UserOutlined,
    SettingOutlined
  },
});
</script>

<style lang="scss" scoped>
.sider {
  .logo {
    height: 64px;
    margin: 16px;
    color: #fff;
    text-align: center;
    font-size: 24px;
    line-height: 64px;
    background: #001529; /* 更改为侧边栏背景色 */
  }

  .ant-menu-dark .ant-menu-item {
    color: #fff; /* 菜单项文字颜色 */
  }

  .ant-menu-dark .ant-menu-item:hover {
    background-color: #1890ff; /* 菜单项悬停背景色 */
    color: #fff; /* 菜单项悬停文字颜色 */
  }

  .ant-menu-dark .ant-menu-submenu-title {
    color: #fff; /* 子菜单标题文字颜色 */
  }

  .ant-menu-dark .ant-menu-submenu-title:hover {
    color: #1890ff; /* 子菜单标题悬停文字颜色 */
  }
}
</style>
