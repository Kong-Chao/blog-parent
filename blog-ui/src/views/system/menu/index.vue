<template>
  <div>
    <a-form @finish="handleSubmit">
      <a-form-item label="菜单名称">
        <a-input v-model:value="menuForm.name" placeholder="请输入菜单名称" />
      </a-form-item>
      <a-form-item label="路由路径">
        <a-input v-model:value="menuForm.path" placeholder="请输入路由路径" />
      </a-form-item>
      <a-form-item label="选择图标">
        <!-- 使用图标选择组件 -->
        <IconSelector v-model="menuForm.icon" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit" @click="handleSubmit">提交</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import { defineComponent, reactive } from 'vue';
import IconSelector from '@/components/IconSelect/index.vue';

export default defineComponent({
  components: { IconSelector },
  setup() {
    const menuForm = reactive({
      name: '',
      path: '',
      icon: '',
    });

    const handleSubmit = () => {
      console.log('Submitted Menu Form:', menuForm);
      // 处理提交逻辑，例如保存到后端
    };

    return {
      menuForm,
      handleSubmit,
    };
  },
});
</script>
