<template>
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
    <a-menu-item :key="route.path" @click="() => navigateTo(route.path)">
      <template #icon>
        <component :is="resolveComponent(route.meta?.icon)" />
      </template>
      {{ route.meta?.title }}
    </a-menu-item>
  </template>
</template>

<script setup>
import {useRouter} from "vue-router";
import {iconMap} from "@/utils/icons";

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
// 自定义resolveIconComponent函数
const resolveComponent = (iconName) => {
  // 如果iconName是有效的，则返回对应的图标，否则返回默认图标
  if (iconName){
    return iconMap[iconName];
  }
};
</script>

