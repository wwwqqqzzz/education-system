<template>
    <div>
      <Navbar />
      <h1>学生后台</h1>
      <router-view /> <!-- 子页面渲染 -->
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
  