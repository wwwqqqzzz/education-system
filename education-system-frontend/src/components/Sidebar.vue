<template>
    <div class="sidebar">
      <ul>
        <li v-for="item in menuItems" :key="item.path">
          <router-link :to="item.path">{{ item.name }}</router-link>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        menuItems: [],
      };
    },
    created() {
      const role = localStorage.getItem("userRole");
      if (role === "admin") {
        this.menuItems = [
          { name: "管理学生", path: "/admin/students" },
          { name: "管理课程", path: "/admin/courses" },
        ];
      } else if (role === "teacher") {
        this.menuItems = [
          { name: "我的课程", path: "/teacher/courses" },
          { name: "学生名单", path: "/teacher/students" },
        ];
      } else if (role === "student") {
        this.menuItems = [
          { name: "可选课程", path: "/student/courses" },
          { name: "已选课程", path: "/student/enrollments" },
        ];
      }
    },
  };
  </script>
  
  <style>
  .sidebar {
    width: 200px;
    background-color: #f5f5f5;
    padding: 20px;
  }
  </style>
  