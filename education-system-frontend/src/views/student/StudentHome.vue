<template>
  <div class="student-home">
    <!-- 欢迎区域 -->
    <div class="welcome-section glass-card">
      <div class="welcome-header">
        <div class="welcome-text">
          <h2>欢迎回来，{{ username }}</h2>
          <p>{{ greeting }}</p>
        </div>
        <div class="welcome-time">
          <div class="date">{{ currentDate }}</div>
          <div class="time">{{ currentTime }}</div>
        </div>
      </div>
    </div>

    <!-- 数据概览 -->
    <div class="stats-container">
      <el-row :gutter="20">
        <el-col :span="8">
          <div class="stats-card glass-card">
            <div class="stats-icon course-icon">
              <i class="el-icon-reading"></i>
            </div>
            <div class="stats-info">
              <div class="stats-title">已选课程</div>
              <div class="stats-number">{{ stats.courseCount }}</div>
              <div class="stats-desc">当前学期课程数量</div>
            </div>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="stats-card glass-card">
            <div class="stats-icon grade-icon">
              <i class="el-icon-s-data"></i>
            </div>
            <div class="stats-info">
              <div class="stats-title">平均成绩</div>
              <div class="stats-number">{{ stats.averageGrade }}</div>
              <div class="stats-desc">所有课程平均分</div>
            </div>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="stats-card glass-card">
            <div class="stats-icon credit-icon">
              <i class="el-icon-collection-tag"></i>
            </div>
            <div class="stats-info">
              <div class="stats-title">已修学分</div>
              <div class="stats-number">{{ stats.totalCredits }}</div>
              <div class="stats-desc">累计获得学分</div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 快捷操作区 -->
    <div class="quick-access glass-card">
      <h3>快捷操作</h3>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-button type="primary" icon="el-icon-plus" @click="$router.push('/student/courses')">
            选择课程
          </el-button>
        </el-col>
        <el-col :span="8">
          <el-button type="success" icon="el-icon-view" @click="$router.push('/student/grades')">
            查看成绩
          </el-button>
        </el-col>
        <el-col :span="8">
          <el-button type="warning" icon="el-icon-document" @click="exportTranscript">
            导出成绩单
          </el-button>
        </el-col>
      </el-row>
    </div>

    <!-- 本学期课表 -->
    <div class="glass-card">
      <h3>本学期课表</h3>
      <el-table :data="recentCourses" stripe>
        <el-table-column prop="course_id" label="课程ID" width="100" align="center"></el-table-column>
        <el-table-column prop="name" label="课程名称" width="180" align="center"></el-table-column>
        <el-table-column prop="description" label="课程描述" min-width="200" align="center"></el-table-column>
        <el-table-column prop="teacher" label="授课教师" width="120" align="center"></el-table-column>
        <el-table-column prop="created_at" label="选课时间" width="180" align="center">
          <template slot-scope="scope">
            {{ formatDate(scope.row.created_at) }}
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'StudentHome',
  data() {
    return {
      username: localStorage.getItem('username'),
      currentDate: '',
      currentTime: '',
      greeting: '',
      stats: {
        courseCount: 0,
        averageGrade: '0.0',
        totalCredits: 0
      },
      recentCourses: []
    }
  },
  created() {
    this.updateTime()
    this.setGreeting()
    this.fetchDashboardData()
    setInterval(this.updateTime, 1000)
  },
  methods: {
    updateTime() {
      const now = new Date();
      this.currentDate = now.toLocaleDateString('zh-CN', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        weekday: 'long'
      });
      this.currentTime = now.toLocaleTimeString('zh-CN', {
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
      });
    },
    setGreeting() {
      const hour = new Date().getHours();
      if (hour < 6) {
        this.greeting = '夜深了，请注意休息';
      } else if (hour < 9) {
        this.greeting = '早上好，新的一天开始了';
      } else if (hour < 12) {
        this.greeting = '上午好，学习愉快';
      } else if (hour < 14) {
        this.greeting = '中午好，记得休息';
      } else if (hour < 18) {
        this.greeting = '下午好，继续加油';
      } else if (hour < 22) {
        this.greeting = '晚上好，记得复习今天的内容';
      } else {
        this.greeting = '夜深了，请注意休息';
      }
    },
    async fetchDashboardData() {
      try {
        const response = await request.get('/student/dashboard')
        const { stats, recentCourses } = response.data

        // 更新统计数据
        this.stats = {
          courseCount: stats.courseCount || 0,
          averageGrade: stats.averageGrade?.toFixed(1) || '0.0',
          totalCredits: stats.totalCredits || 0
        }

        // 更新最近课程
        this.recentCourses = recentCourses || []
      } catch (error) {
        console.error('获取数据失败:', error)
        this.$message.error('获取数据失败')
      }
    },
    async exportTranscript() {
      try {
        // 暂时使用提示替代实际功能
        this.$message.success('成绩单导出功能开发中...');
        // const response = await this.$http.get('/student/transcript', {
        //   responseType: 'blob'
        // });
        // const url = window.URL.createObjectURL(new Blob([response.data]));
        // const link = document.createElement('a');
        // link.href = url;
        // link.setAttribute('download', '成绩单.pdf');
        // document.body.appendChild(link);
        // link.click();
        // document.body.removeChild(link);
      } catch (error) {
        console.error('导出成绩单失败:', error);
        this.$message.error('导出成绩单失败');
      }
    },
    formatDate(dateStr) {
      if (!dateStr) return '暂无数据';
      const date = new Date(dateStr);
      return date.toLocaleString('zh-CN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
      });
    }
  }
}
</script>

<style scoped>
.glass-card {
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.3);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin-bottom: 20px;
  transition: all 0.3s;
}

.welcome-section {
  margin-bottom: 24px;
}

.welcome-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.welcome-text h2 {
  font-size: 24px;
  color: #303133;
  margin: 0;
}

.welcome-text p {
  color: #606266;
  margin: 8px 0 0;
}

.welcome-time {
  text-align: right;
}

.welcome-time .date {
  color: #909399;
  font-size: 14px;
}

.welcome-time .time {
  color: #303133;
  font-size: 24px;
  font-weight: bold;
  margin-top: 4px;
}

.stats-container {
  margin-bottom: 24px;
}

.stats-card {
  display: flex;
  align-items: center;
  padding: 20px;
}

.stats-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 16px;
  color: #fff;
  font-size: 24px;
}

.course-icon {
  background: linear-gradient(135deg, #1890ff, #36cfc9);
}

.grade-icon {
  background: linear-gradient(135deg, #52c41a, #73d13d);
}

.credit-icon {
  background: linear-gradient(135deg, #F56C6C, #ff7875);
}

.stats-info {
  flex: 1;
}

.stats-title {
  color: #909399;
  font-size: 14px;
}

.stats-number {
  color: #303133;
  font-size: 24px;
  font-weight: bold;
  margin: 4px 0;
}

.stats-desc {
  color: #909399;
  font-size: 12px;
}

.quick-access {
  margin-bottom: 24px;
}

.quick-access h3,
.recent-courses h3 {
  color: #303133;
  margin: 0 0 16px;
  font-size: 18px;
}

/* 深色模式适配 */
.dark-theme .glass-card {
  background: rgba(45, 45, 45, 0.8);
  border-color: rgba(255, 255, 255, 0.1);
}

.dark-theme .welcome-text h2,
.dark-theme .welcome-time .time,
.dark-theme .stats-number,
.dark-theme .quick-access h3,
.dark-theme .recent-courses h3 {
  color: #E6E6E6;
}

.dark-theme .welcome-text p,
.dark-theme .welcome-time .date,
.dark-theme .stats-title,
.dark-theme .stats-desc {
  color: #909399;
}

.dark-theme :deep(.el-table) {
  background-color: transparent;
  color: #E6E6E6;
}

.dark-theme :deep(.el-table th),
.dark-theme :deep(.el-table tr) {
  background-color: transparent;
}

.dark-theme :deep(.el-table--striped .el-table__body tr.el-table__row--striped td) {
  background: rgba(255, 255, 255, 0.05);
}
</style>
