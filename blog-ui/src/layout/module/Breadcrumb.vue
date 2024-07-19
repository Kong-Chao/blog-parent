<template>
  <a-breadcrumb>
    <a-breadcrumb-item v-for="(item, index) in breadcrumbItems" :key="index">
      <router-link v-if="index !== breadcrumbItems.length - 1" :to="item.path">{{ item.meta.title }}</router-link>
      <span v-else>{{ item.meta.title }}</span>
    </a-breadcrumb-item>
  </a-breadcrumb>
</template>

<script>
import { computed } from 'vue';
import { useRoute } from 'vue-router';

export default {
  name: 'Breadcrumb',
  setup() {
    const route = useRoute();

    const breadcrumbItems = computed(() => {
      return route.matched.filter(route => route.meta && route.meta.title);
    });

    return {
      breadcrumbItems,
    };
  },
};
</script>

<style scoped>
/* Add your styles here */
</style>
