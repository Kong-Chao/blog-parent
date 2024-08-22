<template>
  <div>
    <a-select
        v-model="selectedIcon"
        style="width: 250px;"
        placeholder="请选择图标"
        :options="iconOptions"
        @change="updateSelectedIcon"
    >
      <!-- 使用插槽自定义展示 -->
      <template #option="{ value, label, component }">
        <span>
          <component :is="component" style="margin-right: 8px;" />
          {{ label }}
        </span>
      </template>
    </a-select>
  </div>
</template>

<script>
import { defineComponent, ref } from 'vue';
import { iconOptions } from '@/utils/icons';

export default defineComponent({
  name: 'IconSelector',
  props: {
    modelValue: {
      type: String,
      default: '',
    },
  },
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    const selectedIcon = ref(props.modelValue);

    const updateSelectedIcon = (value) => {
      emit('update:modelValue', value);
    };

    return {
      selectedIcon,
      iconOptions,
      updateSelectedIcon,
    };
  },
});
</script>
