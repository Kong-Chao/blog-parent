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
      <template v-for="item in menuItems" :key="item.key">
        <a-menu-item
            v-if="item.visible && (!item.children || item.children.length === 0)"
            :key="item.key"
        >
          <component :is="item.icon" />
          <span>{{ item.title }}</span>
        </a-menu-item>
        <a-sub-menu
            v-if="item.visible && item.children && item.children.length > 0"
            :key="item.key"
        >
          <template #title>
            <component :is="item.icon" />
            <span>{{ item.title }}</span>
          </template>
          <template v-for="subItem in item.children" :key="subItem.key">
            <a-menu-item
                v-if="subItem.visible && (!subItem.children || subItem.children.length === 0)"
                :key="subItem.key"
            >
              <component :is="subItem.icon" />
              <span>{{ subItem.title }}</span>
            </a-menu-item>
            <a-sub-menu
                v-if="subItem.visible && subItem.children && subItem.children.length > 0"
                :key="subItem.key"
            >
              <template #title>
                <component :is="subItem.icon" />
                <span>{{ subItem.title }}</span>
              </template>
              <template v-for="subSubItem in subItem.children" :key="subSubItem.key">
                <a-menu-item
                    v-if="subSubItem.visible"
                    :key="subSubItem.key"
                >
                  <component :is="subSubItem.icon" />
                  <span>{{ subSubItem.title }}</span>
                </a-menu-item>
              </template>
            </a-sub-menu>
          </template>
        </a-sub-menu>
      </template>
    </a-menu>
  </a-layout-sider>
</template>

<script>
import { computed, defineComponent, onMounted, ref, watch } from "vue";
import * as Icons from "@ant-design/icons-vue";
import { useRoute, useRouter } from "vue-router";
import { join } from "path-browserify"; // 使用 path-browserify 库进行路径拼接

export default defineComponent({
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
    });

    const handleClick = (e) => {
      const { key } = e;
      if (key) {
        router.push(key);
      }
    };

    const onOpenChange = (keys) => {
      const latestOpenKey = keys.find((key) => !openKeys.value.includes(key));
      if (latestOpenKey) {
        openKeys.value = [latestOpenKey];
      } else {
        openKeys.value = [];
      }
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
