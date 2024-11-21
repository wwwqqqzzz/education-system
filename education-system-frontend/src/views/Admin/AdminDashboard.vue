<template>
    <div>
      <Navbar />
      <h1>管理员后台</h1>
      <router-view /> <!-- 用于渲染子路由 -->
    </div>
  </template>
  
  <script>
  import Navbar from '@/components/Navbar.vue';
  
  export default {
    components: {
      Navbar,
    },
  };
  </script>
  