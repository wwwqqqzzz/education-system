<template>
  <div class="profile-container">
    <el-card>
      <div slot="header">
        <span>个人信息</span>
      </div>
      
      <el-form :model="profile" label-width="100px" class="profile-form">
        <el-form-item label="学号">
          <span>{{ profile.id }}</span>
        </el-form-item>
        <el-form-item label="用户名">
          <span>{{ profile.username }}</span>
        </el-form-item>
        <el-form-item label="姓名">
          <span>{{ profile.name }}</span>
        </el-form-item>
        <el-form-item label="入学年份">
          <span>{{ profile.enrollment_year }}</span>
        </el-form-item>
        <el-form-item label="专业">
          <span>{{ profile.major }}</span>
        </el-form-item>
        <el-form-item label="联系电话">
          <span>{{ profile.phone }}</span>
        </el-form-item>
        <el-form-item label="邮箱">
          <span>{{ profile.email }}</span>
        </el-form-item>
      </el-form>
    </el-card>
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
.profile-container {
  padding: 20px;
}

.profile-form {
  max-width: 600px;
}
</style>
