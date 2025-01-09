<template>
  <div class="teacher-profile">
    <el-card class="glass-card">
      <div slot="header" class="header">
        <span class="title">个人信息</span>
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

    <el-card class="glass-card">
      <div slot="header" class="header">
        <span class="title">我的课程</span>
      </div>
      <el-table 
        :data="courses" 
        border 
        stripe
        class="custom-table">
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
.profile-container {
  padding: 20px;
}

.profile-card {
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  padding: 20px;
  margin-bottom: 20px;
}

/* 修复表单标签颜色 */
:deep(.el-form-item__label) {
  color: #303133;  /* 浅色模式下的颜色 */
}

/* 深色模式适配 */
.dark-theme :deep(.el-form-item__label) {
  color: var(--text-color) !important;
}

:deep(.el-input__inner) {
  background-color: rgba(35, 35, 35, 0.8) !important;
  border-color: var(--border-color) !important;
  color: var(--text-color) !important;
}

:deep(.el-input__inner:hover),
:deep(.el-input__inner:focus) {
  border-color: #409EFF !important;
}

/* 其他深色模式样式... */
</style> 