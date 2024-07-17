<template>
  <div>
    <h1>日志管理</h1>
    <a-table :columns="columns" :dataSource="data" rowKey="id" />
  </div>
</template>

<script setup>
import { ref } from 'vue';

const columns = [
  {
    title: '操作用户',
    dataIndex: 'user',
    key: 'user'
  },
  {
    title: '操作类型',
    dataIndex: 'action',
    key: 'action'
  },
  {
    title: '时间',
    dataIndex: 'time',
    key: 'time'
  }
];

const data = ref([
  {
    id: '1',
    user: 'admin',
    action: '登录',
    time: '2023-07-15 12:34:56'
  },
  {
    id: '2',
    user: 'user1',
    action: '修改密码',
    time: '2023-07-15 13:34:56'
  }
]);
</script>
