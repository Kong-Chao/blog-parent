<template>
  <div>
    <a-menu-item
        v-if="!item.children || item.children.length === 0"
        :key="item.key"
    >
      <component :is="item.icon" />
      <span>{{ item.title }}</span>
    </a-menu-item>
    <a-sub-menu
        v-else :key="item.key"
    >
      <template #title>
        <component :is="item.icon" />
        <span>{{ item.title }}</span>
      </template>
      <template v-for="child in item.children" :key="child.key">
        <MenuItem :item="child" />
      </template>
    </a-sub-menu>
  </div>
</template>

<script>
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'MenuItem',
  props: {
    item: {
      type: Object,
      required: true
    }
  }
});
</script>
