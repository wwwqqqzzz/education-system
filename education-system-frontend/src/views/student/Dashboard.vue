<template>
    <div>
      <h1>学生主页</h1>
      <el-button @click="logout">退出登录</el-button>
    </div>
  </template>
  
  <script>
  export default {
    name: "StudentDashboard",
    methods: {
      logout() {
        localStorage.clear();
        this.$router.push("/login");
      },
    },
  };
  </script>
  