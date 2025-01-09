<template>
  <el-container class="dashboard-container">
    <el-aside :width="isCollapse ? '64px' : '200px'">
      <router-link to="/admin/dashboard" class="logo" :class="{ 'collapsed': isCollapse }">
        <span v-show="!isCollapse">教育管理系统</span>
        <span v-show="isCollapse">EMS</span>
      </router-link>
      <div class="collapse-trigger" @click="toggleCollapse">
        <i :class="isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'"></i>
      </div>
      <el-menu
        :router="true"
        :default-active="$route.path"
        class="el-menu-vertical"
        :collapse="isCollapse"
        background-color="#304156"
        text-color="#fff"
        active-text-color="#409EFF">
        <el-menu-item index="/admin/dashboard">
          <i class="el-icon-s-home"></i>
          <span slot="title">系统首页</span>
        </el-menu-item>
        <el-menu-item index="/admin/teachers">
          <i class="el-icon-user"></i>
          <span slot="title">教师管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/students">
          <i class="el-icon-user"></i>
          <span slot="title">学生管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/courses">
          <i class="el-icon-reading"></i>
          <span slot="title">课程管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/admins">
          <i class="el-icon-s-custom"></i>
          <span slot="title">管理员管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-left">
          <span class="current-route">{{ getCurrentRouteName }}</span>
          <div class="quick-actions">
            <div class="system-time">
              <i class="el-icon-time"></i>
              <span>{{ currentTime }}</span>
            </div>
            <el-tooltip content="刷新页面" placement="bottom">
              <el-button icon="el-icon-refresh" circle @click="refreshPage"></el-button>
            </el-tooltip>
            <el-tooltip :content="isFullscreen ? '退出全屏' : '全屏'" placement="bottom">
              <el-button 
                :icon="isFullscreen ? 'el-icon-close' : 'el-icon-full-screen'" 
                circle 
                @click="toggleFullScreen">
              </el-button>
            </el-tooltip>
            <el-tooltip :content="isDark ? '浅色模式' : '深色模式'" placement="bottom">
              <el-button 
                :icon="isDark ? 'el-icon-sunny' : 'el-icon-moon'" 
                circle 
                @click="toggleTheme">
              </el-button>
            </el-tooltip>
            <el-tooltip content="帮助文档" placement="bottom">
              <el-button icon="el-icon-question" circle @click="showHelp"></el-button>
            </el-tooltip>
          </div>
        </div>
        <div class="header-right">
          <el-dropdown trigger="click" @command="handleCommand">
            <span class="el-dropdown-link">
              <div class="avatar-wrapper">
                {{ username ? username.charAt(0).toUpperCase() : 'A' }}
              </div>
              <span style="margin-left: 8px">{{ username }}</span>
              <i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="profile">角色：管理员</el-dropdown-item>
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>

      <!-- 添加帮助对话框 -->
      <el-dialog
        title="系统使用帮助"
        :visible.sync="helpDialogVisible"
        width="60%"
        custom-class="help-dialog">
        <div class="help-content">
          <el-collapse v-model="activeNames">
            <el-collapse-item title="系统概述" name="1">
              <div class="help-section glass-effect">
                <h4>教育管理系统功能介绍</h4>
                <p>本系统提供以下主要功能模块：</p>
                <ul>
                  <li>教师管理：管理教师信息和课程分配</li>
                  <li>学生管理：管理学生信息和选课</li>
                  <li>课程管理：管理所有课程信息</li>
                  <li>管理员管理：管理系统管理员账号</li>
                </ul>
              </div>
            </el-collapse-item>

            <el-collapse-item title="教师管理" name="2">
              <div class="help-section glass-effect">
                <h4>教师管理功能</h4>
                <p>主要功能：</p>
                <ul>
                  <li>添加新教师：创建教师账号</li>
                  <li>编辑教师信息：修改教师基本信息</li>
                  <li>分配课程：为教师分配教授课程</li>
                  <li>查看教师课程：查看教师所授课程</li>
                </ul>
              </div>
            </el-collapse-item>

            <el-collapse-item title="学生管理" name="3">
              <div class="help-section glass-effect">
                <h4>学生管理功能</h4>
                <p>主要功能：</p>
                <ul>
                  <li>添加新学生：创建学生账号</li>
                  <li>编辑学生信息：修改学生基本信息</li>
                  <li>查看选课情况：查看学生选课记录</li>
                  <li>查看成绩：查看学生各科成绩</li>
                </ul>
              </div>
            </el-collapse-item>

            <el-collapse-item title="课程管理" name="4">
              <div class="help-section glass-effect">
                <h4>课程管理功能</h4>
                <p>主要功能：</p>
                <ul>
                  <li>添加新课程：创建新的课程</li>
                  <li>编辑课程信息：修改课程信息</li>
                  <li>分配教师：为课程分配教师</li>
                  <li>查看选课学生：查看课程的学生名单</li>
                </ul>
              </div>
            </el-collapse-item>

            <el-collapse-item title="管理员管理" name="5">
              <div class="help-section glass-effect">
                <h4>管理员管理功能</h4>
                <p>主要功能：</p>
                <ul>
                  <li>添加管理员：创建新的管理员账号</li>
                  <li>修改权限：调整管理员权限</li>
                  <li>重置密码：重置管理员密码</li>
                </ul>
              </div>
            </el-collapse-item>
          </el-collapse>
        </div>
      </el-dialog>

      <!-- 添加 footer -->
      <el-footer height="50px" class="app-footer">
        <div class="footer-content">
          <div class="copyright">
            <span>© 2024 教育管理系统</span>
            <span class="divider">|</span>
            <span>作者：王起哲</span>
          </div>
          <div class="version">
            <span>Version 1.0.0</span>
          </div>
        </div>
      </el-footer>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'Dashboard',
  data() {
    return {
      username: localStorage.getItem('username'),
      currentTime: '',
      isCollapse: false,
      isFullscreen: false,
      isDark: false,
      helpDialogVisible: false,
      activeNames: ['1']
    }
  },
  computed: {
    getCurrentRouteName() {
      const routes = {
        '/admin/dashboard': '系统首页',
        '/admin/teachers': '教师管理',
        '/admin/students': '学生管理',
        '/admin/courses': '课程管理',
        '/admin/admins': '管理员管理'
      }
      return routes[this.$route.path] || '系统首页'
    }
  },
  methods: {
    updateTime() {
      const now = new Date()
      this.currentTime = now.toLocaleTimeString('zh-CN')
    },
    toggleCollapse() {
      this.isCollapse = !this.isCollapse
    },
    refreshPage() {
      location.reload()
    },
    toggleFullScreen() {
      if (!document.fullscreenElement) {
        document.documentElement.requestFullscreen()
        this.isFullscreen = true
      } else {
        document.exitFullscreen()
        this.isFullscreen = false
      }
    },
    toggleTheme() {
      this.isDark = !this.isDark
      document.body.classList.toggle('dark-theme', this.isDark)
    },
    showHelp() {
      this.helpDialogVisible = true
    },
    handleCommand(command) {
      if (command === 'logout') {
        this.$confirm('确认退出登录？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          localStorage.clear()
          this.$router.push('/login')
        }).catch(() => {})
      }
    }
  },
  created() {
    this.updateTime()
    setInterval(this.updateTime, 1000)
  },
  beforeDestroy() {
    clearInterval(this.updateTime)
  }
}
</script>

<style scoped>
.dashboard-container {
  height: 100vh;
  background-color: #f0f2f5;
  transition: all 0.3s;
}

.el-aside {
  background-color: #304156;
  transition: width 0.3s;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 18px;
  border-bottom: 1px solid #1f2d3d;
  white-space: nowrap;
  transition: all 0.3s;
}

.logo.collapsed {
  font-size: 24px;
}

.collapse-trigger {
  padding: 12px;
  text-align: center;
  color: #a0aec0;
  cursor: pointer;
  transition: all 0.3s;
}

.collapse-trigger:hover {
  color: #fff;
  background-color: rgba(255, 255, 255, 0.1);
}

.dark-theme .collapse-trigger {
  color: #a0aec0;
}

.dark-theme .collapse-trigger:hover {
  color: #fff;
  background-color: rgba(255, 255, 255, 0.05);
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
  height: calc(100vh - 120px);
  overflow-y: auto;
  transition: all 0.3s;
}

.el-header {
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 20px;
}

.current-route {
  font-size: 20px;
  color: #303133;
}

.quick-actions {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-left: 20px;
}

.system-time {
  background: rgba(0,0,0,0.04);
  padding: 8px 16px;
  border-radius: 20px;
  color: #606266;
  margin-right: 8px;
}

.header-right {
  margin-left: auto;
}

.user-wrapper {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 0 8px;
}

.avatar-wrapper {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: #409EFF;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  margin-right: 8px;
}

.username {
  font-size: 14px;
  color: #606266;
  margin-right: 4px;
}

/* 深色模式适配 */
.dark-theme .dashboard-container {
  background-color: var(--bg-color);
}

.dark-theme .el-aside {
  background-color: #1f1f1f;
  border-right: 1px solid #3a3a3a;
}

.dark-theme .el-header {
  background: var(--bg-color-light);
  border-color: var(--border-color);
  box-shadow: 0 1px 4px rgba(0,0,0,0.15);
}

.dark-theme .current-route {
  color: var(--text-color);
}

.dark-theme .system-time {
  background: rgba(255,255,255,0.05);
  color: var(--text-color);
}

.dark-theme .username {
  color: var(--text-color);
}

.dark-theme .el-main {
  background-color: var(--bg-color);
}

.dark-theme .logo {
  border-bottom-color: var(--border-color);
}

/* 侧边栏样式 */
:deep(.el-menu) {
  border-right: none;
  background-color: transparent !important;
}

:deep(.el-menu-item) {
  height: 50px;
  line-height: 50px;
  margin: 4px 0;
}

:deep(.el-menu-item.is-active) {
  background: linear-gradient(90deg, #1890ff 0%, rgba(24,144,255,0.15) 100%) !important;
  border-radius: 4px;
}

:deep(.el-menu-item:hover) {
  background-color: rgba(255, 255, 255, 0.1) !important;
  border-radius: 4px;
}

/* 深色模式侧边栏适配 */
.dark-theme :deep(.el-menu) {
  background-color: #1f1f1f !important;
}

.dark-theme :deep(.el-menu-item) {
  color: #a0aec0 !important;
}

.dark-theme :deep(.el-menu-item.is-active) {
  color: #fff !important;
  background: linear-gradient(90deg, #1890ff 0%, rgba(24,144,255,0.15) 100%) !important;
}

.dark-theme :deep(.el-menu-item:hover) {
  background-color: rgba(255, 255, 255, 0.05) !important;
}

.el-dropdown-link {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 0 8px;
}

.avatar-wrapper {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: #409EFF;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  font-weight: 500;
}

/* 深色模式适配 */
.dark-theme .avatar-wrapper {
  background: var(--primary-color);
  color: #fff;
}

/* 帮助对话框样式 */
.help-dialog {
  border-radius: 12px;
  overflow: hidden;
}

.help-content {
  max-height: 70vh;
  overflow-y: auto;
  padding-right: 10px;
}

.help-section {
  padding: 15px;
  margin-bottom: 10px;
  border-radius: 8px;
  background: rgba(255, 255, 255, 0.7);
  border: 1px solid #e4e7ed;
}

.help-section h4 {
  margin-top: 0;
  margin-bottom: 10px;
  color: #303133;
  font-weight: 600;
}

.help-section p {
  color: #303133;
  line-height: 1.6;
  margin: 10px 0;
}

.help-section ul {
  padding-left: 20px;
  margin: 10px 0;
}

.help-section li {
  margin-bottom: 5px;
  color: #606266;
  line-height: 1.6;
}

/* 深色模式适配 */
.dark-theme .help-section {
  background: rgba(0, 0, 0, 0.2);
  border-color: var(--border-color);
}

.dark-theme .help-section h4,
.dark-theme .help-section p,
.dark-theme .help-section li {
  color: var(--text-color);
}

.dark-theme .help-dialog {
  background: var(--bg-color);
}

.dark-theme .el-collapse {
  border-color: var(--border-color);
  background: transparent;
}

.dark-theme .el-collapse-item__header {
  background: var(--bg-color-light);
  color: var(--text-color);
  border-color: var(--border-color);
}

.dark-theme .el-collapse-item__content {
  background: var(--bg-color);
  color: var(--text-color);
  border-color: var(--border-color);
}
</style>
  