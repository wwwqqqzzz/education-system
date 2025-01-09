<template>
  <div class="teacher-home">
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
              <div class="stats-title">我的课程</div>
              <div class="stats-number">{{ stats.courseCount }}</div>
              <div class="stats-desc">当前教授课程数量</div>
            </div>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="stats-card glass-card">
            <div class="stats-icon student-icon">
              <i class="el-icon-user"></i>
            </div>
            <div class="stats-info">
              <div class="stats-title">学生总数</div>
              <div class="stats-number">{{ stats.studentCount }}</div>
              <div class="stats-desc">所有课程学生总数</div>
            </div>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="stats-card glass-card">
            <div class="stats-icon grade-icon">
              <i class="el-icon-edit"></i>
            </div>
            <div class="stats-info">
              <div class="stats-title">平均成绩</div>
              <div class="stats-number">{{ stats.averageGrade }}</div>
              <div class="stats-desc">所有课程平均分</div>
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
          <el-button type="primary" icon="el-icon-plus" @click="$router.push('/teacher/courses')">
            添加课程
          </el-button>
        </el-col>
        <el-col :span="8">
          <el-button type="success" icon="el-icon-edit" @click="$router.push('/teacher/grades')">
            成绩录入
          </el-button>
        </el-col>
        <el-col :span="8">
          <el-button type="warning" icon="el-icon-document" @click="exportData">
            导出成绩
          </el-button>
        </el-col>
      </el-row>
    </div>

    <!-- 最近课程 -->
    <div class="recent-courses glass-card">
      <h3>最近课程</h3>
      <el-table :data="recentCourses" stripe style="width: 100%">
        <el-table-column prop="name" label="课程名称"></el-table-column>
        <el-table-column prop="time" label="上课时间"></el-table-column>
        <el-table-column prop="location" label="上课地点"></el-table-column>
        <el-table-column prop="studentCount" label="学生人数"></el-table-column>
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button 
              size="mini" 
              type="primary"
              @click="$router.push('/teacher/grades')">
              录入成绩
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'TeacherHome',
  data() {
    return {
      username: localStorage.getItem('username'),
      currentDate: '',
      currentTime: '',
      greeting: '',
      stats: {
        courseCount: 0,
        studentCount: 0,
        averageGrade: 0
      },
      courses: [],
      recentCourses: []
    }
  },
  created() {
    this.updateTime()
    this.updateGreeting()
    setInterval(this.updateTime, 1000)
    this.fetchCourses()
  },
  methods: {
    updateTime() {
      const now = new Date()
      this.currentDate = now.toLocaleDateString('zh-CN', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        weekday: 'long'
      })
      this.currentTime = now.toLocaleTimeString('zh-CN')
    },
    updateGreeting() {
      const hour = new Date().getHours()
      if (hour < 12) {
        this.greeting = '早上好，开始新的一天'
      } else if (hour < 18) {
        this.greeting = '下午好，继续加油'
      } else {
        this.greeting = '晚上好，注意休息'
      }
    },
    async fetchCourses() {
      try {
        // 获取所有课程
        const response = await request.get('/teacher/courses')
        this.courses = response.data
        this.recentCourses = response.data // 最近课程就是所有课程

        // 更新课程数量
        this.stats.courseCount = this.courses.length

        // 获取每个课程的学生数量和成绩
        let totalStudents = 0
        let totalGrades = 0
        let totalGradedStudents = 0

        for (const course of this.courses) {
          // 获取每个课程的学生列表
          const studentsResponse = await request.get(`/teacher/courses/${course.id}/students`)
          const students = studentsResponse.data

          // 更新课程的学生数量
          course.studentCount = students.length
          totalStudents += students.length

          // 计算平均成绩
          const grades = students.map(s => s.grade).filter(g => g != null)
          if (grades.length > 0) {
            totalGrades += grades.reduce((a, b) => a + b, 0)
            totalGradedStudents += grades.length
          }
        }

        // 更新统计信息
        this.stats.studentCount = totalStudents
        this.stats.averageGrade = totalGradedStudents > 0 
          ? Math.round(totalGrades / totalGradedStudents * 10) / 10 
          : 0
      } catch (error) {
        console.error('获取数据失败:', error)
        this.$message.error('获取数据失败')
      }
    }
  }
}
</script>

<style scoped>
.teacher-home {
  padding: 20px;
}

.glass-card {
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.3);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin-bottom: 20px;
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
  margin: 0 0 8px 0;
}

.welcome-text p {
  font-size: 16px;
  color: #606266;
  margin: 0;
}

.welcome-time {
  text-align: right;
}

.welcome-time .date {
  font-size: 16px;
  color: #606266;
  margin-bottom: 4px;
}

.welcome-time .time {
  font-size: 24px;
  color: #303133;
  font-weight: 600;
}

.stats-container {
  margin-bottom: 24px;
}

.stats-card {
  display: flex;
  align-items: center;
  padding: 20px;
  height: 120px;
}

.stats-icon {
  width: 64px;
  height: 64px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 16px;
}

.stats-icon i {
  font-size: 32px;
  color: #fff;
}

.course-icon {
  background: linear-gradient(135deg, #409EFF, #36cfc9);
}

.student-icon {
  background: linear-gradient(135deg, #67C23A, #95de64);
}

.grade-icon {
  background: linear-gradient(135deg, #722ED1, #9254DE);
}

.stats-info {
  flex: 1;
}

.stats-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 8px;
}

.stats-number {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 4px;
}

.stats-desc {
  font-size: 12px;
  color: #909399;
}

.quick-access {
  padding: 24px;
}

.quick-access h3 {
  margin: 0 0 20px 0;
  font-size: 18px;
  color: #303133;
}

.quick-access .el-button {
  width: 100%;
  height: 40px;
  border-radius: 8px;
}

.recent-courses {
  margin-top: 20px;
}

.recent-courses h3 {
  margin: 0 0 20px 0;
  font-size: 18px;
  color: #303133;
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