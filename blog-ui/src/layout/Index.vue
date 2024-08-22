<template>
  <a-layout class="layout">
    <a-layout-sider
        v-model:collapsed="collapsed"
        :trigger="null"
        collapsible
        class="sider"
    >
      <div class="logo">My App</div>
      <a-menu
          theme="dark"
          mode="inline"
          v-model:selectedKeys="selectedKeys"
          :openKeys="openKeys"
          @click="handleClick"
          @openChange="onOpenChange"
      >
        <template v-for="menuItem in menuItems" :key="menuItem.key">
          <a-sub-menu
              v-if="menuItem.children && menuItem.children?.length > 0"
              :key="menuItem.key"
          >
            <template #title>
              <span>
                <component :is="menuItem.icon" v-if="menuItem.icon" />
                <span>{{ menuItem.title }}</span>
              </span>
            </template>
            <template v-for="child in menuItem.children" :key="child.key">
              <a-menu-item v-if="!child.children" :key="child.key">
                <component :is="child.icon" v-if="child.icon" />
                <span>{{ child.title }}</span>
              </a-menu-item>
              <a-sub-menu v-else :key="child.key">
                <template #title>
                  <span>
                    <component :is="child.icon" v-if="child.icon" />
                    <span>{{ child.title }}</span>
                  </span>
                </template>
                <template v-for="subChild in child.children" :key="subChild.key">
                  <a-menu-item v-if="!subChild.children" :key="subChild.key">
                    <component :is="subChild.icon" v-if="subChild.icon" />
                    <span>{{ subChild.title }}</span>
                  </a-menu-item>
                  <a-sub-menu v-else :key="subChild.key">
                    <template #title>
                      <span>
                        <component :is="subChild.icon" v-if="subChild.icon" />
                        <span>{{ subChild.title }}</span>
                      </span>
                    </template>
                    <template v-for="nestedChild in subChild.children" :key="nestedChild.key">
                      <a-menu-item>
                        <component :is="nestedChild.icon" v-if="nestedChild.icon" />
                        <span>{{ nestedChild.title }}</span>
                      </a-menu-item>
                    </template>
                  </a-sub-menu>
                </template>
              </a-sub-menu>
            </template>
          </a-sub-menu>
          <a-menu-item v-else :key="menuItem.key">
            <component :is="menuItem.icon" v-if="menuItem.icon" />
            <span>{{ menuItem.title }}</span>
          </a-menu-item>
        </template>
      </a-menu>
    </a-layout-sider>
    <a-layout class="site-layout">
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
        <a-breadcrumb class="ant-breadcrumb">
          <a-breadcrumb-item>
            <router-link to="/">首页</router-link>
          </a-breadcrumb-item>
          <template v-for="item in breadcrumbs" :key="item.path">
            <a-breadcrumb-item v-if="item.path !== '/'">
              <span class="breadcrumb-disabled">{{ item.title }}</span>
            </a-breadcrumb-item>
          </template>
        </a-breadcrumb>
        <div class="user-profile">
          <a-avatar :src="require('@/assets/images/avatar.jpg')" />
          <a-dropdown>
            <template #overlay>
              <a-menu>
                <a-menu-item key="1">
                  <router-link to="/user/profile">个人中心</router-link>
                </a-menu-item>
                <a-menu-item key="2">
                  <a @click="handleLogout">退出登录</a>
                </a-menu-item>
              </a-menu>
            </template>
            <a class="ant-dropdown-link" @click="(e) => e.preventDefault()">
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
import { computed, defineComponent, ref, watch } from "vue";
import { Avatar, Dropdown, Menu, Modal } from "ant-design-vue";
import useUserStore from "@/store/modules/user";
import { useRoute, useRouter } from "vue-router";
import usePermissionStore from "@/store/modules/permission";
import { join } from "path-browserify";
import { iconMap } from "@/utils/icons"; // 引入面包屑组件
import { MenuUnfoldOutlined, MenuFoldOutlined } from '@ant-design/icons-vue';

export default defineComponent({
  components: {
    'menu-unfold-outlined': MenuUnfoldOutlined,
    'menu-fold-outlined': MenuFoldOutlined,
    "a-avatar": Avatar,
    "a-dropdown": Dropdown,
    "a-menu": Menu,
    "a-menu-item": Menu.Item,
    "a-sub-menu": Menu.SubMenu,
    "a-icon": () =>
        import("@ant-design/icons-vue").then((module) => module.default),
  },
  setup() {
    const router = useRouter();
    const route = useRoute();

    // 当前选中和展开的菜单项
    const selectedKeys = ref([route.path]);
    const openKeys = ref([]);

    // 生成菜单项
    const menuItems = computed(() => {
      return generateMenuItems(allRoutes.value);
    });

    // 获取所有注册路由
    const permissionStore = usePermissionStore();
    const allRoutes = computed(() => permissionStore.routes);

    // 构建菜单路由
    const generateMenuItems = (routes, basePath = "") => {
      return (
          routes
              // Filter out routes that should not be visible in the menu
              .filter((route) => route.meta?.visible !== false)
              .map((route) => {
                const fullPath = join(basePath, route.path);
                const iconName = route.meta?.icon;
                const IconComponent = iconName ? iconMap[iconName] : null;
                const children = route.children
                    ? generateMenuItems(route.children, fullPath)
                    : [];
                const menuItem = {
                  key: fullPath,
                  title: route.meta?.title || route.name,
                  icon: IconComponent ? IconComponent : null,
                  ...(children.length > 0 && { children }), // 仅在children非空时添加children属性
                };
                return menuItem;
              })
      );
    };

    // 菜单项点击事件
    const handleClick = (e) => {
      router.push(e.key);
      selectedKeys.value = [e.key];
    };

    // 展开/关闭菜单事件
    const onOpenChange = (keys) => {
      openKeys.value = keys;
    };

    // 确保在路由变化时，选中项同步更新
    watch(
        () => route.path,
        (newPath) => {
          selectedKeys.value = [newPath];
        }
    );

    // 动态生成面包屑
    const breadcrumbs = computed(() => {
      return getBreadcrumb(route.matched);
    });

    const getBreadcrumb = (matchedRoutes) => {
      //过滤带有meta.title的路由
      let filteredRoutes = matchedRoutes
          .filter(item => item.meta && item.meta.title)
          .map(route => ({
            path: join('/', route.path),
            title: route.meta?.title || route.name
          }));

      //确保'首页'在开头，避免重复
      if (filteredRoutes.length === 0 || filteredRoutes[0].path !== '/') {
        filteredRoutes = [{ path: '/', title: '首页' }].concat(filteredRoutes);
      }

      //根据标题和路径重复删除面包屑
      const uniqueBreadcrumbs = [];
      const seenPaths = new Set();

      for (const item of filteredRoutes) {
        if (!seenPaths.has(item.path)) {
          seenPaths.add(item.path);
          uniqueBreadcrumbs.push(item);
        }
      }

      return uniqueBreadcrumbs;
    };

    const handleLogout = () => {
      // 处理退出登录逻辑
      Modal.confirm({
        title: "确认退出登录",
        content: "您确定要退出登录吗？",
        okText: "确认",
        cancelText: "取消",
        onOk() {
          useUserStore()
              .logOut()
              .then(() => {
                location.href = "/index";
              });
        },
        onCancel() {},
      });
    };

    return {
      collapsed: ref(false),
      handleLogout,
      selectedKeys,
      openKeys,
      handleClick,
      menuItems,
      onOpenChange,
      breadcrumbs
    };
  },
});
</script>

<style lang="scss" scoped>
.layout {
  min-height: 100vh;
  background: #f0f2f5;
}
.sider {
  background: #001529;
  color: #fff;
  overflow: auto;
}
.logo {
  height: 64px;
  background: #001529;
  color: #fff;
  text-align: center;
  line-height: 64px;
  font-size: 18px;
  font-weight: bold;
}
.site-layout {
  transition: margin-left 0.2s, padding-left 0.2s;
}
.header {
  background: #fff;
  padding: 0 16px;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  border-bottom: 1px solid #e8e8e8;
  height: 64px;
  line-height: 64px;

  .trigger-wrapper {
    display: flex;
    align-items: center;
    margin-right: 8px;
    height: 100%;
  }

  .ant-breadcrumb {
    margin: 0;
    padding-left: 8px;
    display: flex;
    align-items: center;
    height: 100%;
    cursor: not-allowed;
  }
}

.trigger {
  font-size: 16px;
  line-height: 64px;
  cursor: pointer;
}
.user-profile {
  display: flex;
  align-items: center;
  margin-left: auto;
}
.user-profile .ant-avatar {
  margin-right: 8px;
}
.user-profile .ant-dropdown-link {
  margin-right: 8px;
  display: flex;
  align-items: center;
}
.content {
  margin: 24px;
  padding: 24px;
  background: #fff;
  min-height: calc(100vh - 64px - 24px - 24px);
}

.breadcrumb-disabled {
  cursor: not-allowed;
  color: #999;
}
</style>



