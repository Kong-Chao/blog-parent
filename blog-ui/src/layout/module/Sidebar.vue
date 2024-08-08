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
      <template v-for="menuItem in menuItems" :key="menuItem.key">
        <template v-if="menuItem.children && menuItem.children.length">
          <a-sub-menu :key="menuItem.key">
            <template #title>
              <span>
                <component :is="menuItem.icon" />
                <span>{{ menuItem.title }}</span>
              </span>
            </template>
            <a-menu-item
                v-for="child in menuItem.children"
                :key="child.key"
                :index="child.key"
                @click="() => navigateTo(child.path)"
            >
              <component :is="child.icon" />
              <span>{{ child.title }}</span>
            </a-menu-item>
          </a-sub-menu>
        </template>
        <a-menu-item
            v-else
            :key="menuItem.key"
            @click="() => navigateTo(menuItem.path)"
        >
          <component :is="menuItem.icon" />
          <span>{{ menuItem.title }}</span>
        </a-menu-item>
      </template>
    </a-menu>
  </a-layout-sider>
</template>

<script>
import {computed, defineComponent, onMounted, ref, watch} from "vue";
import usePermissionStore from "@/store/modules/permission";
import permission from "@/store/modules/permission";
import {useRoute, useRouter} from "vue-router";
import * as Icons from "@ant-design/icons-vue";

export default defineComponent({
  methods: {permission, usePermissionStore},
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
    const permissionStore = usePermissionStore();
    const selectedKeys = ref([]);
    const openKeys = ref([]);
    //根据权限存储的路由生成菜单项
    const menuItems = computed(() => {
      return generateMenuItems(permissionStore.routes);
    })

    // 递归生成菜单项的辅助函数
    const generateMenuItems = (routes, basePath = "") => {
      return routes.filter((route) => route.meta && route.meta.visible !== false) // 只有可见的路线
          .map((route) => {
            // 获取图标组件
            const IconComponent = route.meta.icon? Icons[route.meta.icon] : null;
            // 构建完整路径
            const fullPath = `${basePath}/${route.path}`.replace("//", "/");
            const menuItem = {
              key: fullPath,
              title: route.meta.title,
              icon: IconComponent,
              path: fullPath,
              children: route.children ? generateMenuItems(route.children, fullPath) : [],
            };

            return menuItem;
          })
    }

    const handleClick = (e) => {
      console.log(1111,router);
      router.push(e);
    };

    const onOpenChange = (keys) => {
      openKeys.value = keys;
    };

    const navigateTo = (path) => {
      console.log("Navigating to:", path);
      router.push(path).catch((err) => {
        console.error("Navigation error:", err);
      });
    };

    watch(
        () => route.path,
        (newPath) => {
          selectedKeys.value = [newPath];
        }
    );

    onMounted(() => {
      watch(
          () => permissionStore.routes,
          (newRoutes) => {
            if (newRoutes && newRoutes.length > 0) {
              console.log('Dynamic routes updated:', router.getRoutes());
            }
          },
          { immediate: true }
      )
    });

    return {
      selectedKeys,
      openKeys,
      handleClick,
      computedCollapsed,
      menuItems,
      onOpenChange,
      navigateTo
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
