<!-- src/layout/module/SidebarContent.vue -->
<template>
  <a-menu theme="dark" mode="inline" :defaultSelectedKeys="[defaultSelectedKey]" :openKeys="[openKey]" @click="handleMenuClick">
    <template v-for="route in menuRoutes" :key="route.path">
      <a-sub-menu v-if="route.children" :key="route.path" :title="route.meta.title">
        <template v-for="child in route.children" :key="child.path">
          <a-menu-item>
            <router-link :to="`${route.path}/${child.path}`">{{ child.meta.title }}</router-link>
          </a-menu-item>
        </template>
      </a-sub-menu>
      <a-menu-item v-else>
        <router-link :to="route.path">{{ route.meta.title }}</router-link>
      </a-menu-item>
    </template>
  </a-menu>
</template>

<script setup>
import { computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute();

const menuRoutes = computed(() => {
  return router.options.routes.filter(route => route.meta && route.meta.title);
});

const defaultSelectedKey = computed(() => {
  return route.path;
});

const openKey = computed(() => {
  const paths = route.path.split('/');
  return paths.length > 2 ? `/${paths.slice(1, 2).join('/')}` : '';
});

const handleMenuClick = ({ key }) => {
  router.push(key);
};
</script>
