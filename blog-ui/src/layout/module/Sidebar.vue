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
import { computed, defineComponent, onMounted, ref, watch } from "vue";
import * as Icons from "@ant-design/icons-vue";
import { useRoute, useRouter } from "vue-router";
import { join } from "path-browserify"; // 使用 path-browserify 库进行路径拼接
import MenuItem from "@/layout/module/menuItem/MenuItem.vue"

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
    const computedCollapsed = computed(() => props.collapsed);
    const selectedKeys = ref([route.path]);
    const openKeys = ref([]);
    const menuItems = ref([]);

    // 动态生成菜单项
    const generateMenuItems = (routes, basePath = "") => {
      return routes
          .filter((route) => route.meta && route.meta.visible && !route.meta.hidden)
          .map((route) => {
            const icon = Icons[route.meta.icon] || Icons["HomeOutlined"];
            const fullPath = join(basePath, route.path); // 使用 path-browserify 处理路径拼接

            const menuItem = {
              key: fullPath,
              title: route.meta.title,
              icon: icon,
              path: fullPath,
              visible: route.meta.visible,
              children: route.children
                  ? generateMenuItems(route.children, fullPath)
                  : [],
            };

            return menuItem;
          });
    };

    onMounted(() => {
      selectedKeys.value = [route.path];
      menuItems.value = generateMenuItems(router.options.routes);
      console.log('menuItems', menuItems.value);
    });

    const handleClick = (e) => {
      const { key } = e;
      if (key) {
        router.push(key);
      }
    };

    const onOpenChange = (keys) => {
      // 设置菜单展开状态
      openKeys.value = keys;
    };

    watch(
        () => route.path,
        () => {
          selectedKeys.value = [route.path];
        }
    );

    return {
      selectedKeys,
      openKeys,
      handleClick,
      computedCollapsed,
      menuItems,
      onOpenChange,
    };
  },
});
</script>

<style scoped>
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
