<template>
  <template v-if="route.children?.length">
    <a-sub-menu :key="route.path" :title="route.meta?.title">
      <template #icon>
        <component :is="resolveComponent(route.meta?.icon || 'user-outlined')" />
      </template>
      <template v-for="child in route.children" :key="child.path">
        <MenuItem :route="child" />
      </template>
    </a-sub-menu>
  </template>
  <template v-else>
    <a-menu-item :key="route.path" @click="() => navigateTo(route.path)">
      <template #icon>
        <component :is="resolveComponent(route.meta?.icon || 'user-outlined')" />
      </template>
      {{ route.meta?.title }}
    </a-menu-item>
  </template>
</template>

<script setup>
import { resolveComponent } from "vue";
import {useRouter} from "vue-router";

const router = useRouter();
const props = defineProps({
  route: {
    type: Object,
    required: true,
  },
});

const navigateTo = (path) => {
  if (path) router.push(path);
};
</script>

