<template>
  <el-container class="dashboard-container">
    <el-aside width="200px">
      <el-menu
        :router="true"
        background-color="#304156"
        text-color="#fff"
        active-text-color="#409EFF">
        <el-menu-item index="/teacher/profile">
          <i class="el-icon-user"></i>
          <span>个人信息</span>
        </el-menu-item>
        <el-menu-item index="/teacher/courses">
          <i class="el-icon-reading"></i>
          <span>我的课程</span>
        </el-menu-item>
        <el-menu-item index="/teacher/grades">
          <i class="el-icon-edit"></i>
          <span>成绩管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-right">
          <span>欢迎，{{ username }}</span>
          <el-button type="text" @click="handleLogout">退出登录</el-button>
        </div>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'TeacherDashboard',
  data() {
    return {
      username: localStorage.getItem('username')
    }
  },
  methods: {
    handleLogout() {
      localStorage.clear();
      this.$router.push('/login');
      this.$message.success('已退出登录');
    }
  }
}
</script>

<style scoped>
.dashboard-container {
  height: 100vh;
}

.el-aside {
  background-color: #304156;
  color: #fff;
}

.el-header {
  background-color: #fff;
  border-bottom: 1px solid #dcdfe6;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding: 0 20px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>
  