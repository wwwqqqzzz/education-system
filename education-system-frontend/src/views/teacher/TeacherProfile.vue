<template>
  <div class="teacher-profile">
    <el-card class="profile-card">
      <div slot="header">
        <span>个人信息</span>
      </div>
      <el-form label-width="100px" class="profile-form">
        <el-form-item label="工号">
          <span>{{ profile.id }}</span>
        </el-form-item>
        <el-form-item label="用户名">
          <span>{{ profile.username }}</span>
        </el-form-item>
        <el-form-item label="姓名">
          <span>{{ profile.name }}</span>
        </el-form-item>
        <el-form-item label="院系">
          <span>{{ profile.department }}</span>
        </el-form-item>
        <el-form-item label="电话">
          <span>{{ profile.phone }}</span>
        </el-form-item>
        <el-form-item label="邮箱">
          <span>{{ profile.email }}</span>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="courses-card">
      <div slot="header">
        <span>我的课程</span>
      </div>
      <el-table :data="courses" border style="width: 100%">
        <el-table-column prop="id" label="课程ID" width="100"></el-table-column>
        <el-table-column prop="name" label="课程名称"></el-table-column>
        <el-table-column prop="description" label="课程描述"></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'TeacherProfile',
  data() {
    return {
      profile: {},
      courses: []
    }
  },
  created() {
    this.fetchProfile()
    this.fetchCourses()
  },
  methods: {
    async fetchProfile() {
      try {
        const response = await request.get('/teacher/profile')
        this.profile = response.data
      } catch (error) {
        console.error('获取个人信息失败:', error)
        if (error.response?.status === 401) {
          this.$router.push('/login')
        }
        this.$message.error('获取个人信息失败')
      }
    },
    async fetchCourses() {
      try {
        const response = await request.get('/teacher/courses')
        this.courses = response.data
      } catch (error) {
        console.error('获取课程信息失败:', error)
        if (error.response?.status === 401) {
          this.$router.push('/login')
        }
        this.$message.error('获取课程信息失败')
      }
    }
  }
}
</script>

<style scoped>
.teacher-profile {
  max-width: 1000px;
  margin: 0 auto;
}
.profile-card {
  margin-bottom: 20px;
}
.profile-form {
  max-width: 600px;
}
.el-form-item {
  margin-bottom: 15px;
}
</style> 