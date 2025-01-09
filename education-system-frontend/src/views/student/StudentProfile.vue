<template>
  <div class="student-profile">
    <div class="glass-card">
      <div class="header">
        <div class="title">个人信息</div>
      </div>
      <el-form 
        ref="form" 
        :model="profile" 
        label-width="100px"
        class="profile-form">
        <el-form-item label="学号">
          <el-input v-model="profile.student_id" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="profile.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="profile.gender" disabled></el-input>
        </el-form-item>
        <el-form-item label="院系">
          <el-input v-model="profile.department" disabled></el-input>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="profile.major" disabled></el-input>
        </el-form-item>
        <el-form-item label="班级">
          <el-input v-model="profile.class" disabled></el-input>
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="profile.phone"></el-input>
        </el-form-item>
        <el-form-item label="电子邮箱">
          <el-input v-model="profile.email"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateProfile">保存修改</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'StudentProfile',
  data() {
    return {
      profile: {
        id: '',
        username: '',
        name: '',
        enrollment_year: '',
        major: '',
        phone: '',
        email: ''
      }
    }
  },
  created() {
    this.fetchProfile()
  },
  methods: {
    async fetchProfile() {
      try {
        const response = await request.get('/student/profile')
        this.profile = response.data
      } catch (error) {
        console.error('获取个人信息失败:', error)
        this.$message.error('获取个人信息失败')
      }
    }
  }
}
</script>

<style scoped>
.student-profile {
  padding: 20px;
}

/* 玻璃拟态卡片效果 */
.glass-card {
  background: rgba(255, 255, 255, 0.8) !important;
  backdrop-filter: blur(10px) !important;
  border: 1px solid rgba(255, 255, 255, 0.3) !important;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1) !important;
  border-radius: 16px !important;
  overflow: hidden !important;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #ebeef5;
}

.title {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-color, #303133);
}

.profile-form {
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
}

/* 深色模式适配 */
:deep(.dark-theme .glass-card) {
  background: rgba(30, 30, 30, 0.8) !important;
  border-color: rgba(255, 255, 255, 0.1) !important;
}

:deep(.dark-theme .title) {
  color: #E6E6E6;
}

:deep(.dark-theme .header) {
  border-bottom-color: #4c4c4c;
}

:deep(.dark-theme .el-form-item__label) {
  color: #E6E6E6;
}

:deep(.dark-theme .el-input__inner) {
  background-color: rgba(45, 45, 45, 0.8);
  border-color: #4c4c4c;
  color: #E6E6E6;
}

:deep(.dark-theme .el-input.is-disabled .el-input__inner) {
  background-color: rgba(35, 35, 35, 0.8);
  border-color: #4c4c4c;
  color: #909399;
}

:deep(.dark-theme .el-button--primary) {
  background-color: #409EFF;
  border-color: #409EFF;
}

:deep(.dark-theme .el-button--primary:hover) {
  background-color: #66b1ff;
  border-color: #66b1ff;
}
</style>
