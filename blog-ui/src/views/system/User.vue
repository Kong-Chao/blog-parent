<template>
  <div>
    <h1>用户管理</h1>
    <a-table :columns="columns" :dataSource="data" rowKey="id" />
  </div>
</template>

<script setup>
import { ref } from 'vue';

const columns = [
  {
    title: '用户名',
    dataIndex: 'username',
    key: 'username'
  },
  {
    title: '邮箱',
    dataIndex: 'email',
    key: 'email'
  },
  {
    title: '角色',
    dataIndex: 'role',
    key: 'role'
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
    username: 'user1',
    email: 'user1@example.com',
    role: '管理员'
  },
  {
    id: '2',
    username: 'user2',
    email: 'user2@example.com',
    role: '普通用户'
  }
]);
</script>
