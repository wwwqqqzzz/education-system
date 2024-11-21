<template>
    <div>
      <h2>选课页面</h2>
      <ul>
        <li v-for="course in courses" :key="course.id">
          {{ course.name }}
          <button @click="selectCourse(course.id)">选课</button>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        courses: [],
      };
    },
    created() {
      // 示例接口请求
      this.$http.get('/api/student/courses').then((response) => {
        this.courses = response.data;
      });
    },
    methods: {
      selectCourse(courseId) {
        this.$http.post('/api/student/select-course', { courseId }).then(() => {
          alert('选课成功！');
        });
      },
    },
  };
  </script>
  