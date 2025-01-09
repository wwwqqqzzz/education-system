<template>
  <el-container class="dashboard-container">
    <el-aside :width="isCollapse ? '64px' : '200px'">
      <router-link to="/teacher/dashboard" class="logo" :class="{ 'collapsed': isCollapse }">
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
        <el-menu-item index="/teacher/dashboard">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </el-menu-item>
        <el-menu-item index="/teacher/profile">
          <i class="el-icon-user"></i>
          <span slot="title">个人信息</span>
        </el-menu-item>
        <el-menu-item index="/teacher/courses">
          <i class="el-icon-reading"></i>
          <span slot="title">我的课程</span>
        </el-menu-item>
        <el-menu-item index="/teacher/grades">
          <i class="el-icon-edit"></i>
          <span slot="title">成绩管理</span>
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
                <span>角色：教师</span>
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

    <!-- 帮助对话框 -->
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
                <li>个人信息：查看和管理个人基本信息</li>
                <li>课程管理：管理您的教学课程</li>
                <li>成绩管理：管理学生成绩</li>
              </ul>
            </div>
          </el-collapse-item>

          <el-collapse-item title="个人信息" name="2">
            <div class="help-section glass-effect">
              <h4>个人信息管理</h4>
              <p>主要功能：</p>
              <ul>
                <li>查看个人基本信息：工号、姓名、系等</li>
                <li>查看所授的课程列表</li>
              </ul>
              <p>注意事项：</p>
              <ul>
                <li>如需修改个人信息，请联系管理员</li>
                <li>请确保联系方式信息准确</li>
              </ul>
            </div>
          </el-collapse-item>

          <el-collapse-item title="课程管理" name="3">
            <div class="help-section glass-effect">
              <h4>课程管理功能</h4>
              <p>主要功能：</p>
              <ul>
                <li>添加新课程：创建新的教学课程</li>
                <li>管理学生：查看和管理选课学生</li>
                <li>课程信息：维护课程基本信息</li>
                <li>结课操作：结束已完成的课程</li>
              </ul>
              <p>注意事项：</p>
              <ul>
                <li>添加课程时请填写完整信息</li>
                <li>结课前请确保所有成绩已录入</li>
                <li>结课操作不可撤销，请谨慎操作</li>
              </ul>
            </div>
          </el-collapse-item>

          <el-collapse-item title="成绩管理" name="4">
            <div class="help-section glass-effect">
              <h4>成绩管理功能</h4>
              <p>主要功能：</p>
              <ul>
                <li>选择课程：切换不同课程的成绩管理</li>
                <li>录入成绩：为学生录入课程成绩</li>
                <li>修改成绩：修改已录入的成绩</li>
                <li>查看记录：查看成绩更新历史</li>
              </ul>
              <p>注意事项：</p>
              <ul>
                <li>成绩范围：0-100分</li>
                <li>请及时保存成绩更改</li>
                <li>成绩录入后会自动记录时间</li>
              </ul>
            </div>
          </el-collapse-item>

          <el-collapse-item title="常见问题" name="5">
            <div class="help-section glass-effect">
              <h4>系统使用FAQ</h4>
              <ul>
                <li>如何切换深色/浅色主题？
                  <p>点击右上角的主题按钮即可切换</p>
                </li>
                <li>如何全屏显示？
                  <p>点击右上角的全屏按钮可切换</p>
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
  name: 'TeacherDashboard',
  data() {
    return {
      username: localStorage.getItem('username'),
      currentTime: '',
      isFullscreen: false,
      isDark: false,
      helpDialogVisible: false,
      activeNames: ['1'],
      isCollapse: false
    }
  },
  computed: {
    getCurrentRouteName() {
      const routePath = this.$route.path;
      const routeMap = {
        '/teacher/profile': '个人信息',
        '/teacher/courses': '我的课程',
        '/teacher/grades': '成绩管理'
      };
      return routeMap[routePath] || '首页';
    }
  },
  created() {
    this.updateTime();
    setInterval(this.updateTime, 1000);
    
    // 初始化主题
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
      this.isDark = true;
      document.body.classList.add('dark-theme');
    }
    
    // 监听全屏变化
    document.addEventListener('fullscreenchange', () => {
      this.isFullscreen = !!document.fullscreenElement;
    });
  },
  methods: {
    handleLogout() {
      localStorage.clear();
      this.$router.push('/login');
      this.$message.success('已退出登录');
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
      this.helpDialogVisible = true
    },
    toggleCollapse() {
      this.isCollapse = !this.isCollapse
    }
  }
}
</script>

<style>
/* 深色主题变量定义 - 放在最顶部 */
:root {
  --bg-color: #141414;
  --bg-color-light: #1f1f1f;
  --text-color: #E6E6E6;
  --text-color-light: #B0B0B0;
  --border-color: #303030;
  --table-header-bg: rgba(25, 25, 25, 0.8);
  --table-stripe-bg: rgba(35, 35, 35, 0.8);
}

/* 深色主题样式 */
.dark-theme {
  /* 背景色 */
  --bg-color: #1f1f1f;
  --bg-color-light: #2d2d2d;
  /* 文字颜色 */
  --text-color: #ffffff;
  --text-color-light: #e0e0e0;
  /* 边框颜色 */
  --border-color: #3a3a3a;
  /* 主题色 */
  --primary-color: #409EFF;
  /* 表格相关 */
  --table-header-bg: rgba(45, 45, 45, 0.9);
  --table-stripe-bg: rgba(45, 45, 45, 0.5);
  --table-hover: rgba(64, 158, 255, 0.1);
}

/* 应用深色主题 */
.dark-theme .dashboard-container {
  background-color: var(--bg-color);
}

.dark-theme .el-header {
  background: var(--bg-color-light);
  border-color: var(--border-color);
}

/* 帮助文档深色模式补充样式 */
.dark-theme .help-dialog {
  background: var(--bg-color-light) !important;
}

.dark-theme .help-dialog .el-dialog__title {
  color: var(--text-color) !important;
}

.dark-theme .help-dialog .el-dialog__body {
  background: var(--bg-color) !important;
}

.dark-theme .el-collapse {
  border-color: var(--border-color);
  background: transparent;
}

.dark-theme .el-collapse-item__header {
  background: var(--bg-color-light);
  color: var(--text-color);
  border-bottom-color: var(--border-color);
}

.dark-theme .el-collapse-item__content {
  background: var(--bg-color);
  color: var(--text-color-light);
}

.dark-theme .help-section {
  background: var(--bg-color-light);
  border-color: var(--border-color);
}

.dark-theme .help-section:hover {
  background: var(--table-hover);
}

.dark-theme .help-section h4 {
  color: var(--text-color);
}

.dark-theme .help-section p,
.dark-theme .help-section li {
  color: var(--text-color-light);
}

/* 语言切换按钮样式 */
.quick-actions .el-dropdown {
  margin-right: 8px;
}

.el-dropdown-menu__item i {
  margin-right: 8px;
  color: #67C23A;
}

/* 深色模式拉菜单适配 */
.dark-theme .el-dropdown-menu {
  background-color: var(--bg-color-light);
  border-color: var(--border-color);
}

.dark-theme .el-dropdown-menu__item {
  color: var(--text-color);
}

.dark-theme .el-dropdown-menu__item:hover {
  background-color: var(--table-hover);
}

/* 下拉框样式补充 */
.dark-theme .el-collapse-item__header {
  background: rgba(45, 45, 45, 0.9) !important;
  color: var(--text-color) !important;
  border-bottom: 1px solid var(--border-color) !important;
  font-size: 15px !important;
  font-weight: 500 !important;
  padding: 0 20px !important;
  height: 50px !important;
  line-height: 50px !important;
  transition: all 0.3s ease !important;
}

.dark-theme .el-collapse-item__header:hover {
  background: rgba(64, 158, 255, 0.1) !important;
}

.dark-theme .el-collapse-item__arrow {
  color: var(--text-color-light) !important;
  transition: transform 0.3s !important;
}

.dark-theme .el-collapse-item__wrap {
  background: transparent !important;
  border-bottom: 1px solid var(--border-color) !important;
}

.dark-theme .el-collapse {
  border: 1px solid var(--border-color) !important;
  border-radius: 8px !important;
  background: transparent !important;
}

/* 帮助内容区域滚动条样式 */
.help-content {
  max-height: 70vh;
  overflow-y: auto;
  padding-right: 10px;
}

.help-content::-webkit-scrollbar {
  width: 6px;
}

.help-content::-webkit-scrollbar-thumb {
  background: rgba(144, 147, 153, 0.3);
  border-radius: 3px;
}

.dark-theme .help-content::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.2);
}

.help-content::-webkit-scrollbar-track {
  background: transparent;
}
</style>

<style scoped>
.dashboard-container {
  height: 100vh;
  display: flex;
}

.el-aside {
  background-color: #304156;
  transition: width 0.3s;
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
  height: calc(100vh - 120px);
  overflow-y: auto;
  transition: all 0.3s;
}

/* 深色模式适配 */
.dark-theme .el-main {
  background-color: var(--bg-color, #141414);
}

.el-header {
  background-color: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.el-footer {
  background-color: #fff;
  border-top: 1px solid #e6e6e6;
}

/* Logo样式 */
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 20px;
  font-weight: 600;
  color: #fff;
  border-bottom: 1px solid #1f2d3d;
  white-space: nowrap;
  overflow: hidden;
  transition: all 0.3s;
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
}

.collapse-trigger:hover {
  background: rgba(0, 0, 0, 0.3);
}

.collapse-trigger i {
  font-size: 18px;
}

/* 深色模式适配 */
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

/* 侧边栏样式 */
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

/* 隐藏滚动条但持功能 */
.el-menu::-webkit-scrollbar {
  width: 0;
  height: 0;
}

.el-menu-item {
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

.dark-theme .el-menu-item {
  color: #a0aec0 !important;
  
  &:hover, &.is-active {
    background: linear-gradient(90deg, #1890ff 0%, rgba(24,144,255,0.15) 100%);
    color: #fff !important;
  }
}

.dashboard-container {
  height: 100vh;
  background-color: #f5f7fa;
}

.el-header {
  background: #fff;
  padding: 0 20px;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-left {
  display: flex;
  align-items: center;
}

.current-route {
  font-size: 20px;
  font-weight: 600;
  color: #1f2f3d;
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

/* 深色模式适配 */
.dark-theme .dashboard-container {
  background-color: var(--bg-color);
}

.dark-theme .el-header {
  background: var(--bg-color-light);
  border-color: var(--border-color);
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

/* 深色模式适配 */
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
:deep(.help-dialog) {
  border-radius: 20px !important;
  overflow: hidden !important;
}

:deep(.help-dialog .el-dialog__header) {
  background: rgba(255, 255, 255, 0.85) !important;
  backdrop-filter: blur(10px) !important;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1) !important;
  padding: 20px 24px !important;
}

:deep(.help-dialog .el-dialog__body) {
  padding: 24px !important;
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
}

.help-section {
  background: rgba(255, 255, 255, 0.7) !important;
  backdrop-filter: blur(8px) !important;
  border-radius: 12px !important;
  padding: 24px !important;
  margin: 16px 0 !important;
  box-shadow: 
    0 4px 15px rgba(0, 0, 0, 0.05),
    inset 0 0 0 1px rgba(255, 255, 255, 0.3) !important;
  border: 1px solid rgba(255, 255, 255, 0.4) !important;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1) !important;
}

/* 深色模式帮助文档适配 */
:deep(.dark-theme .help-dialog) {
  background: rgba(31, 31, 31, 0.95) !important;
}

:deep(.dark-theme .help-dialog .el-dialog__header) {
  background: rgba(45, 45, 45, 0.95) !important;
}

:deep(.dark-theme .help-dialog .el-dialog__body) {
  background: rgba(31, 31, 31, 0.95) !important;
}

:deep(.dark-theme .help-section) {
  background: rgba(45, 45, 45, 0.8) !important;
  border-color: rgba(255, 255, 255, 0.05) !important;
}

:deep(.dark-theme .el-collapse-item__header) {
  background: rgba(45, 45, 45, 0.9) !important;
  color: var(--text-color) !important;
}

:deep(.dark-theme .el-collapse-item__content) {
  background: transparent !important;
}

/* 帮助文档标题样式补充 */
.help-section h4 {
  color: #303133;
  margin: 15px 0 10px;
  font-size: 16px;
  font-weight: 600;
  position: relative;
  padding-left: 12px;
}

.help-section h4::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 16px;
  background: #409EFF;
  border-radius: 2px;
}

.dark-theme .help-section h4 {
  color: var(--text-color);
}

.dark-theme .help-section h4::before {
  background: #409EFF;
  box-shadow: 0 0 10px rgba(64, 158, 255, 0.5);
}

.help-section p,
.help-section li {
  color: #606266;
  line-height: 1.8;
  margin: 8px 0;
}

.dark-theme .help-section p,
.dark-theme .help-section li {
  color: var(--text-color-light);
}
</style>
  