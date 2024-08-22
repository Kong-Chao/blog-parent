<template>
  <a-layout-sider
      v-model:collapsed="computedCollapsed"
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
      <MenuItem v-for="item in menuItems" :key="item.key" :item="item" />
    </a-menu>
  </a-layout-sider>
</template>

<script>
import {computed, defineComponent, ref, watch} from "vue";
import {useRoute, useRouter} from "vue-router";
import usePermissionStore from "@/store/modules/permission";
import { join } from 'path-browserify';
import MenuItem from "@/layout/module/menuItem/MenuItem.vue";
import {iconMap} from "@/utils/icons";

export default defineComponent({
  components: {MenuItem},
  props: {
    collapsed: {
      type: Boolean,
      default: false,
    },
  },
  setup(props) {
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
    const allRoutes = computed(() => permissionStore.routes)
    console.log('allRoutes',allRoutes.value);

    // 构建菜单路由
    const generateMenuItems = (routes, basePath = '') => {
      return routes
          .filter(route => route.meta?.visible !== false)
          .map(route => {
            const fullPath = join(basePath, route.path);
            const iconName = route.meta?.icon;
            const IconComponent = iconName ? iconMap[iconName] : null;
            const menuItem = {
              key: fullPath,
              title: route.meta?.title || route.name,
              icon: IconComponent ? IconComponent : null,
              children: route.children ? generateMenuItems(route.children, fullPath) : []
            };
          return menuItem;
      });
    };

    // 菜单项点击事件
    const handleClick = e => {
      router.push(e.key);
      selectedKeys.value = [e.key];
    };

    // 展开/关闭菜单事件
    const onOpenChange = keys => {
      openKeys.value = keys;
    };

    // 确保在路由变化时，选中项同步更新
    watch(
        () => route.path,
        newPath => {
          selectedKeys.value = [newPath];
        }
    );

    return {
      selectedKeys,
      openKeys,
      handleClick,
      computedCollapsed: computed(() => props.collapsed),
      menuItems,
      onOpenChange,
    };
  },
});
</script>

<style lang="scss" scoped>
.logo {
  height: 32px;
  margin: 16px;
  background: rgba(255, 255, 255, 0.3);
}
.sider {
  height: 100vh;
  overflow: auto;
  position: fixed;
  left: 0;
}
</style>
