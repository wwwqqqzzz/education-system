<template>
  <el-container class="dashboard-container">
    <el-aside :width="isCollapse ? '64px' : '200px'">
      <router-link to="/student/dashboard" class="logo" :class="{ 'collapsed': isCollapse }">
        <span v-show="!isCollapse">教育管理系统</span>
        <span v-show="isCollapse">EMS</span>
      </router-link>
      <div class="collapse-trigger" @click="toggleCollapse">
        <i :class="isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'"></i>
      </div>
      <el-menu
        :router="true"
        :default-active="$route.path"
        :collapse="isCollapse"
        background-color="#304156"
        text-color="#fff"
        active-text-color="#409EFF">
        <el-menu-item index="/student/dashboard">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </el-menu-item>
        <el-menu-item index="/student/profile">
          <i class="el-icon-user"></i>
          <span slot="title">个人信息</span>
        </el-menu-item>
        <el-menu-item index="/student/courses">
          <i class="el-icon-reading"></i>
          <span slot="title">选课管理</span>
        </el-menu-item>
        <el-menu-item index="/student/grades">
          <i class="el-icon-s-data"></i>
          <span slot="title">成绩查询</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-left">
          <span class="current-route">{{ getCurrentRouteName }}</span>
        </div>
        <div class="header-center">
          <div class="system-time">
            <i class="el-icon-time"></i>
            <span>{{ currentTime }}</span>
          </div>
          <div class="quick-actions">
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
          <el-dropdown trigger="click">
            <span class="user-wrapper">
              <div class="avatar-wrapper">
                {{ username ? username.charAt(0).toUpperCase() : 'U' }}
              </div>
              <span class="username">{{ username }}</span>
              <i class="el-icon-caret-bottom"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <span>角色：学生</span>
              </el-dropdown-item>
              <el-dropdown-item divided @click.native="handleLogout">
                <i class="el-icon-switch-button"></i>
                <span>退出登录</span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
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
    
    <!-- 帮助对话框 -->
    <el-dialog
      title="使用帮助"
      :visible.sync="helpDialogVisible"
      width="60%"
      custom-class="help-dialog"
      :class="{ 'dark-theme': isDark }">
      <div class="help-container">
        <el-collapse v-model="activeNames">
          <el-collapse-item title="个人信息" name="1">
            <div class="help-section glass-effect">
              <h4>个人信息管理</h4>
              <p>主要功能：</p>
              <ul>
                <li>查看个人基本信息</li>
                <li>修改联系方式</li>
                <li>更新个人密码</li>
              </ul>
              <p>注意事项：</p>
              <ul>
                <li>请确保联系方式准确有效</li>
                <li>密码修改后需要重新登录</li>
              </ul>
            </div>
          </el-collapse-item>

          <el-collapse-item title="选课管理" name="2">
            <div class="help-section glass-effect">
              <h4>选课功能</h4>
              <p>主要功能：</p>
              <ul>
                <li>查看可选课程列表</li>
                <li>选择新课程</li>
                <li>查看已选课程</li>
                <li>退选课程</li>
              </ul>
              <p>注意事项：</p>
              <ul>
                <li>请在选课时间内完成选课</li>
                <li>已有成绩的课程无法退选</li>
                <li>选课前请仔细阅读课程描述</li>
              </ul>
            </div>
          </el-collapse-item>

          <el-collapse-item title="成绩查询" name="3">
            <div class="help-section glass-effect">
              <h4>成绩管理</h4>
              <p>主要功能：</p>
              <ul>
                <li>查看所有课程成绩</li>
                <li>查看课程平均分</li>
                <li>导出成绩单</li>
              </ul>
              <p>注意事项：</p>
              <ul>
                <li>成绩更新可能有延迟</li>
                <li>如有疑问请联系任课教师</li>
              </ul>
            </div>
          </el-collapse-item>

          <el-collapse-item title="常见问题" name="4">
            <div class="help-section glass-effect">
              <h4>FAQ</h4>
              <ul>
                <li>如何选课？
                  <p>在选课管理页面点击"选课"按钮即可</p>
                </li>
                <li>如何查看成绩？
                  <p>在成绩查询页面可以查看所有课程成绩</p>
                </li>
                <li>忘记密码怎么办？
                  <p>请联系系统管理员重置密码</p>
                </li>
              </ul>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
    </el-dialog>
  </el-container>
</template>

<script>
export default {
  name: 'StudentDashboard',
  data() {
    return {
      isCollapse: false,
      username: localStorage.getItem('username'),
      currentTime: '',
      isFullscreen: false,
      isDark: false,
      helpDialogVisible: false,
      activeNames: ['1']
    }
  },
  created() {
    this.updateTime();
    setInterval(this.updateTime, 1000);
    
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
      this.isDark = true;
      document.body.classList.add('dark-theme');
    }
    
    document.addEventListener('fullscreenchange', () => {
      this.isFullscreen = !!document.fullscreenElement;
    });
  },
  computed: {
    getCurrentRouteName() {
      const routePath = this.$route.path;
      const routeMap = {
        '/student/dashboard': '首页',
        '/student/profile': '个人信息',
        '/student/courses': '选课管理',
        '/student/grades': '成绩查询'
      };
      return routeMap[routePath] || '首页';
    }
  },
  methods: {
    toggleCollapse() {
      this.isCollapse = !this.isCollapse;
    },
    updateTime() {
      this.currentTime = new Date().toLocaleString('zh-CN', {
        hour12: false,
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
      });
    },
    refreshPage() {
      window.location.reload();
    },
    async toggleFullScreen() {
      if (!document.fullscreenElement) {
        await document.documentElement.requestFullscreen();
      } else {
        if (document.exitFullscreen) {
          await document.exitFullscreen();
        }
      }
    },
    toggleTheme() {
      this.isDark = !this.isDark;
      document.body.classList.toggle('dark-theme');
      localStorage.setItem('theme', this.isDark ? 'dark' : 'light');
    },
    showHelp() {
      this.helpDialogVisible = true;
    },
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
  position: relative;
  background-color: #304156;
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
  transition: width 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.el-menu {
  border: none;
  flex: 1;
  overflow-y: auto;
}

/* 隐藏滚动条但保持功能 */
.el-menu::-webkit-scrollbar {
  width: 0;
  height: 0;
}

:deep(.el-menu-item) {
  height: 56px;
  line-height: 56px;
  margin: 4px 0;
  
  &:hover, &.is-active {
    background: linear-gradient(90deg, #1890ff 0%, rgba(24,144,255,0.1) 100%);
    color: #fff !important;
  }
  
  i {
    margin-right: 10px;
    font-size: 18px;
  }
}

/* Logo样式 */
.logo {
  text-decoration: none;
  color: #fff;
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 20px;
  font-weight: 600;
  border-bottom: 1px solid #1f2d3d;
  white-space: nowrap;
  overflow: hidden;
  transition: all 0.3s;
  display: block;
}

.logo:hover {
  background: rgba(255, 255, 255, 0.05);
}

.logo.collapsed {
  font-size: 16px;
  padding: 0;
}

/* 折叠按钮样式 */
.collapse-trigger {
  position: relative;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  cursor: pointer;
  transition: all 0.3s;
  background: rgba(0, 0, 0, 0.15);
  border-bottom: 1px solid #1f2d3d;
  z-index: 1;
}

.collapse-trigger:hover {
  background: rgba(0, 0, 0, 0.3);
}

.collapse-trigger i {
  font-size: 18px;
  transition: transform 0.3s;
}

/* 深色模式适配 */
.dark-theme .el-aside {
  background-color: #1f1f1f;
  border-right: 1px solid #3a3a3a;
}

.dark-theme .logo {
  border-bottom-color: #3a3a3a;
}

.dark-theme .el-menu {
  background-color: #1f1f1f !important;
}

.dark-theme :deep(.el-menu-item) {
  color: #a0aec0 !important;
  
  &:hover, &.is-active {
    background: linear-gradient(90deg, #1890ff 0%, rgba(24,144,255,0.15) 100%);
    color: #fff !important;
  }
}

.dark-theme .collapse-trigger {
  background: rgba(255, 255, 255, 0.05);
  border-bottom-color: #3a3a3a;
}

.dark-theme .collapse-trigger:hover {
  background: rgba(255, 255, 255, 0.1);
}

/* 菜单项过渡动画 */
.el-menu-vertical:not(.el-menu--collapse) {
  width: 200px;
}

:deep(.el-menu--collapse) {
  width: 64px;
}

:deep(.el-menu-item) {
  transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
}

.el-header {
  background: linear-gradient(to right, #ffffff, #f8f9fa, #ffffff);
  box-shadow: 
    0 1px 4px rgba(0,21,41,.08),
    inset 0 0 0 1px rgba(255,255,255,0.9);
  display: grid;
  grid-template-columns: 200px 1fr 200px;
  align-items: center;
  padding: 0 20px;
  height: 60px;
  position: relative;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  margin: 8px;
}

.el-header::before {
  content: '';
  position: absolute;
  top: -2px;
  left: -2px;
  right: -2px;
  bottom: -2px;
  background: linear-gradient(45deg, #f1f1f1, #ffffff, #f1f1f1);
  border-radius: 6px;
  z-index: -1;
}

.header-left .current-route {
  font-size: 20px;
  font-weight: 600;
  color: #303133;
  text-shadow: 1px 1px 1px rgba(0,0,0,0.05);
  position: relative;
  padding-left: 15px;
  transition: color 0.3s ease;
}

.header-center {
  display: flex;
  align-items: center;
  gap: 20px;
}

.system-time {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 0 12px;
  height: 32px;
  background: rgba(0,0,0,0.025);
  border-radius: 16px;
  color: #606266;
}

.quick-actions {
  display: flex;
  gap: 8px;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-wrapper {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 0 8px;
  height: 100%;
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

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}

/* 深色模式适配 */
.dark-theme .el-header {
  background: var(--bg-color-light);
  border-color: var(--border-color);
  box-shadow: 0 1px 4px rgba(0,0,0,0.15);
}

.dark-theme .header-left .current-route {
  color: var(--text-color);
  text-shadow: 1px 1px 1px rgba(0,0,0,0.2);
}

.dark-theme .el-main {
  background-color: #141414;
}

.dark-theme .collapse-trigger:hover {
  background: rgba(255, 255, 255, 0.1);
}

/* Footer 样式 */
.app-footer {
  background: linear-gradient(to right, #ffffff, #f8f9fa, #ffffff);
  border-top: 1px solid #e4e7ed;
  box-shadow: 0 -1px 4px rgba(0,21,41,.08);
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

.footer-content {
  width: 100%;
  max-width: 1200px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  font-size: 14px;
  color: #606266;
}

.copyright {
  display: flex;
  align-items: center;
  gap: 8px;
}

.divider {
  color: #dcdfe6;
}

.version {
  color: #909399;
  font-size: 12px;
}

/* 深色模式补充适配 */
.dark-theme .system-time {
  background: rgba(255,255,255,0.05);
  color: var(--text-color);
}

.dark-theme .username {
  color: var(--text-color);
}

.dark-theme .app-footer {
  background: var(--bg-color-light);
  border-color: var(--border-color);
}

.dark-theme .footer-content {
  color: var(--text-color-light);
}

.dark-theme .divider {
  color: var(--border-color);
}

.dark-theme .version {
  color: var(--text-color-light);
}

/* 帮助文档样式 */
.help-container {
  padding: 20px;
}

.help-section {
  padding: 20px;
  border-radius: 8px;
  background: rgba(255, 255, 255, 0.8);
  margin-bottom: 16px;
}

.help-section h4 {
  margin: 0 0 16px;
  color: #303133;
  font-size: 16px;
}

.help-section p {
  color: #606266;
  margin: 12px 0;
}

.help-section ul {
  padding-left: 20px;
  margin: 12px 0;
}

.help-section li {
  color: #606266;
  margin: 8px 0;
}

/* 深色模式适配 */
.dark-theme .help-section {
  background: rgba(45, 45, 45, 0.8);
}

.dark-theme .help-section h4 {
  color: #E6E6E6;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
}

.dark-theme .help-section p,
.dark-theme .help-section li {
  color: #B0B0B0;
}

.dark-theme :deep(.el-collapse-item__header) {
  background: rgba(45, 45, 45, 0.8) !important;
  color: #E6E6E6 !important;
  border-bottom-color: rgba(255, 255, 255, 0.05) !important;
}

.dark-theme :deep(.el-collapse-item__header:hover) {
  background: rgba(45, 45, 45, 0.9) !important;
}

.dark-theme :deep(.el-collapse-item__wrap) {
  background: transparent !important;
}

.dark-theme :deep(.el-collapse-item__content) {
  background: transparent !important;
  color: #E6E6E6;
}
</style>
  