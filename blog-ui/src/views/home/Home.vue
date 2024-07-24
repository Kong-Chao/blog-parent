<template>
<div>首页 123 <a-button @click="ok">狗狗</a-button></div>
</template>

<script setup>
import {useRouter} from "vue-router";

const router = useRouter()
const ok = ()=>{
  router.push("/login")
}

</script>

<style scoped>

</style>