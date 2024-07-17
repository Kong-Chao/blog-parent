<!-- src/views/SystemManagement/PermissionManagement.vue -->
<template>
  <div>
    <h1>权限管理</h1>
    <a-table :columns="columns" :dataSource="data" rowKey="id" />
  </div>
</template>

<script setup>
import { ref } from 'vue';

const columns = [
  {
    title: '权限名称',
    dataIndex: 'name',
    key: 'name'
  },
  {
    title: '描述',
    dataIndex: 'description',
    key: 'description'
  },
  {
    title: '操作',
    key: 'action',
  }
];

const data = ref([
  {
    id: '1',
    name: '查看用户',
    description: '允许查看用户信息'
  },
  {
    id: '2',
    name: '编辑用户',
    description: '允许编辑用户信息'
  }
]);
</script>
