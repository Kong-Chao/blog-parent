<template>
  <a-layout-sider v-model:collapsed="computedCollapsed" :trigger="null" collapsible>
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
        <a-menu-item v-if="!item.children" :key="item.key">
          <component :is="item.icon" />
          <span>{{ item.title }}</span>
        </a-menu-item>
        <a-sub-menu v-else :key="item.key">
          <template #title>
            <component :is="item.icon" />
            <span>{{ item.title }}</span>
          </template>
          <template v-for="subItem in item.children" :key="subItem.key">
            <a-menu-item>
              <component :is="subItem.icon" />
              <span>{{ subItem.title }}</span>
            </a-menu-item>
          </template>
        </a-sub-menu>
      </template>
    </a-menu>
  </a-layout-sider>
</template>

<script>
import { computed, defineComponent, onMounted, ref, watch } from 'vue';
import * as Icons from '@ant-design/icons-vue';
import { useRoute, useRouter } from 'vue-router';

export default defineComponent({
  props: {
    collapsed: {
      type: Boolean,
      default: false
    }
  },
  setup(props) {
    const router = useRouter();
    const route = useRoute();
    const computedCollapsed = computed(() => props.collapsed);
    const selectedKeys = ref([route.name]);
    const openKeys = ref([]);
    const menuItems = ref([]);

    const generateMenuItems = () => {
      menuItems.value = router.options.routes
          .filter(route => route.meta && route.meta.title)
          .map(route => {
            const icon = Icons[route.meta.icon] || Icons['HomeOutlined'];

            return {
              key: route.name,
              title: route.meta.title,
              icon: icon,
              children: route.children ? route.children.map(child => ({
                key: child.name,
                title: child.meta.title,
                icon: Icons[child.meta.icon] || Icons['HomeOutlined']
              })) : null
            };
          });
    };

    onMounted(() => {
      selectedKeys.value = [route.name];
      generateMenuItems();
    });

    const handleClick = e => {
      router.push({ name: e.key });
    };

    const onOpenChange = keys => {
      const latestOpenKey = keys.find(key => !openKeys.value.includes(key));
      if (latestOpenKey) {
        openKeys.value = [latestOpenKey];
      } else {
        openKeys.value = [];
      }
    };

    watch(
        () => route.name,
        () => {
          selectedKeys.value = [route.name];
        }
    );

    return {
      menuItems,
      selectedKeys,
      openKeys,
      handleClick,
      onOpenChange,
      computedCollapsed
    };
  }
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
    background: #001529;
  }

  .ant-menu-dark .ant-menu-item {
    color: #fff;
  }

  .ant-menu-dark .ant-menu-item:hover {
    background-color: #1890ff;
    color: #fff;
  }

  .ant-menu-dark .ant-menu-submenu-title {
    color: #fff;
  }

  .ant-menu-dark .ant-menu-submenu-title:hover {
    color: #1890ff;
  }
}
</style>
