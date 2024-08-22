<template>
<div>
  用户信息展示
  <a-button @click="onButtonClick">
    测试
  </a-button>
</div>
</template>

<script setup>
import {listUser} from "@/api/system/user";

const onButtonClick = () => {
  listUser().then(res => {
    console.log(res.data)
  })
}
</script>

<style scoped>

</style>