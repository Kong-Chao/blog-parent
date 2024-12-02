<template>
  <div class="div-layout">
    <a-layout breakpoint="lg" collapsed-width="0" @collapse="onCollapse" class="layout">
      <a-layout-sider v-model:collapsed="collapsed" :trigger="null" collapsible>
        <!-- logo 修改为图标，大小根据collapsed变化 -->
        <img
            src="@/assets/logo/logo.png"
            alt="Logo"
            class="logo"
            :style="{ width: collapsed ? '60px' : '80px', height: collapsed ? '45px' : '60px' }"
            @click="goHome"
        />
        <a-menu
            theme="dark"
            mode="inline"
            v-model="selectedKeys"
            :openKeys="openKeys"
            @openChange="handleOpenChange"
            @click="handleMenuClick"
        >
          <template v-for="route in processedRoutes" :key="route.path">
            <template v-if="route.children?.length">
              <a-sub-menu :key="route.path" :title="route.meta?.title">
                <template #icon>
                  <component :is="resolveComponent(route.meta?.icon)" />
                </template>
                <template v-for="child in route.children" :key="child.path">
                  <MenuItem :route="child" />
                </template>
              </a-sub-menu>
            </template>
            <template v-else>
              <MenuItem :route="route" />
            </template>
          </template>
        </a-menu>
      </a-layout-sider>
      <a-layout>
        <a-layout-header :style="{ background: '#fff', padding: 0 }">
          <menu-unfold-outlined v-if="collapsed" class="trigger" @click="toggleCollapsed" />
          <menu-fold-outlined v-else class="trigger" @click="toggleCollapsed" />
        </a-layout-header>
        <a-layout-content :style="{ margin: '12px 5px', padding: '24px', background: '#fff', minHeight: '350px' }">
          <div class="content">
            <router-view/>
          </div>
        </a-layout-content>
        <a-layout-footer style="text-align: center">
          Ant Design ©2018 Created by Ant UED
        </a-layout-footer>
      </a-layout>
    </a-layout>
  </div>
</template>

<script setup>
import { computed, ref } from "vue";
import { useRouter } from "vue-router";
import usePermissionStore from "@/store/modules/permission";
import MenuItem from "@/layout/module/MenuItem.vue";
import { MenuFoldOutlined, MenuUnfoldOutlined } from "@ant-design/icons-vue";
import { iconMap } from "@/utils/icons";

// 状态管理
const collapsed = ref(false);
const permissionStore = usePermissionStore();
const rawRoutes = computed(() => permissionStore.routes);
const selectedKeys = ref([]);
const openKeys = ref([]);
const router = useRouter();

// 处理后端返回的路由结构
const processedRoutes = computed(() => {
  const processRoutes = (routes, parentPath = "") => {
    return routes
        .filter((route) => route.hidden !== true) // 排除不可见路由
        .map((route) => {
          const fullPath = route.path.startsWith("/")
              ? route.path
              : `${parentPath}/${route.path}`; // 处理相对路径
          return {
            ...route,
            path: fullPath,
            children: route.children ? processRoutes(route.children, fullPath) : [],
          };
        });
  };
  return processRoutes(rawRoutes.value);
});

const onCollapse = (collapsed) => {
  collapsed.value = collapsed; // 更新状态
};

// 菜单事件处理
const handleMenuClick = ({ key }) => {
  selectedKeys.value = [key];
};
const handleOpenChange = (keys) => {
  openKeys.value = keys;
};
const toggleCollapsed = () => {
  collapsed.value = !collapsed.value;
};

// 自定义resolveIconComponent函数
const resolveComponent = (iconName) => {
  if (iconName) {
    return iconMap[iconName];
  }
};

// 跳转到首页
const goHome = () => {
  router.push("/"); // 导航到首页路径
};
</script>

<style lang="scss" scoped>
.div-layout {
  height: 100vh;
  width: 100%;
  display: flex;
  flex-direction: column;
}

.layout {
  height: 100%;
}

.trigger {
  padding: 0 24px;
  font-size: 18px;
  cursor: pointer;
  transition: color 0.3s;
}

.trigger:hover {
  color: #1890ff;
}

/* logo 样式 */
.logo {
  cursor: pointer;
  transition: width 0.3s, height 0.3s; /* 动画效果 */
  display: block;
  margin: 10px auto;
}

@media (max-width: 768px) {
  .content {
    font-size: 14px;
  }
  .logo {
    font-size: 16px;
    text-align: center;
    padding: 10px;
  }
}

@media (min-width: 768px) and (max-width: 1024px) {
  .content {
    font-size: 16px;
  }
  .logo {
    font-size: 18px;
  }
}

@media (min-width: 1024px) {
  .content {
    font-size: 18px;
  }
  .logo {
    font-size: 20px;
  }
}
</style>
