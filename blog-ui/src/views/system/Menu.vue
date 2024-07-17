<!-- src/views/SystemManagement/MenuManagement.vue -->
<template>
  <div>
    <h1>菜单管理</h1>
    <a-table :columns="columns" :dataSource="data" rowKey="id" />
  </div>
</template>

<script setup>
import { ref } from 'vue';

const columns = [
  {
    title: '菜单名称',
    dataIndex: 'name',
    key: 'name'
  },
  {
    title: '路径',
    dataIndex: 'path',
    key: 'path'
  },
  {
    title: '操作',
    key: 'action',
    // eslint-disable-next-line no-unused-vars
  }
];

const data = ref([
  {
    id: '1',
    name: '首页',
    path: '/'
  },
  {
    id: '2',
    name: '用户管理',
    path: '/system-management/user-management'
  }
]);
</script>
