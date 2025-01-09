<template>
  <div class="grades-management">
    <el-card class="glass-card">
      <div slot="header" class="header">
        <span class="title">成绩管理</span>
      </div>

      <!-- 课程选择 -->
      <el-form :inline="true" class="filter-form">
        <el-form-item label="选择课程">
          <el-select
            v-model="selectedCourse"
            placeholder="请选择课程"
            @change="handleCourseChange"
            class="custom-select">
            <el-option
              v-for="course in courses"
              :key="course.id"
              :label="course.name"
              :value="course.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <!-- 成绩表格 -->
      <el-table
        :data="students"
        border
        stripe
        fit
        v-loading="loading"
        class="custom-table">
        <el-table-column
          prop="id"
          label="学号"
          min-width="120"
          align="center">
        </el-table-column>

        <el-table-column
          prop="name"
          label="姓名"
          min-width="120"
          align="center">
        </el-table-column>

        <el-table-column
          label="成绩"
          min-width="120"
          align="center">
          <template slot-scope="scope">
            <el-input-number
              v-model="scope.row.grade"
              :min="0"
              :max="100"
              :precision="1"
              :controls="false"
              size="mini"
              class="grade-input">
            </el-input-number>
          </template>
        </el-table-column>

        <el-table-column
          label="最后更新时间"
          min-width="180"
          align="center">
          <template slot-scope="scope">
            {{ scope.row.created_at ? new Date(scope.row.created_at).toLocaleString() : '未录入' }}
          </template>
        </el-table-column>

        <el-table-column
          label="操作"
          min-width="120"
          align="center"
          fixed="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="saveGrade(scope.row)"
              class="save-button">
              保存
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'TeacherGrades',
  data() {
    return {
      courses: [],
      students: [],
      selectedCourse: this.$route.query.courseId || null  // 从路由参数获取课程ID
    }
  },
  created() {
    this.fetchCourses()
    // 如果有课程ID，直接加载学生列表
    if (this.selectedCourse) {
      this.handleCourseChange(this.selectedCourse)
    }
  },
  methods: {
    // 获取教师的课程列表
    async fetchCourses() {
      try {
        const response = await request.get('/teacher/courses')
        this.courses = response.data
      } catch (error) {
        console.error('获取课程列表失败:', error)
        this.$message.error('获取课程列表失败')
      }
    },

    // 处理课程选择变化
    async handleCourseChange(courseId) {
      if (courseId) {
        try {
          const response = await request.get(`/teacher/courses/${courseId}/students`)
          console.log('获取到的学生数据:', response.data)  // 添加调试日志
          this.students = response.data
        } catch (error) {
          console.error('获取学生列表失败:', error)
          this.$message.error('获取学生列表失败')
        }
      } else {
        this.students = []
      }
    },

    // 保存学生成绩
    async saveGrade(student) {
      try {
        if (student.grade === null || student.grade === undefined) {
          this.$message.warning('请输入有效的成绩')
          return
        }

        await request.post('/teacher/grades', {
          studentId: student.id,
          courseId: this.selectedCourse,
          grade: student.grade
        })

        this.$message.success('成绩保存成功')
        // 重新加载学生列表以更新显示
        this.handleCourseChange(this.selectedCourse)
      } catch (error) {
        console.error('保存成绩失败:', error)
        this.$message.error('保存成绩失败')
      }
    }
  }
}
</script>

<style scoped>
.grades-management {
  padding: 20px;
}

.glass-card {
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  padding: 20px;
  margin-bottom: 20px;
}

/* 修复标题颜色 */
.title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;  /* 浅色模式下的颜色 */
}

/* 深色模式适配 */
.dark-theme .title {
  color: var(--text-color) !important;
}

.filter-form {
  margin: 20px 0;
  padding: 15px;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 8px;
  backdrop-filter: blur(5px);
}

/* Select 组件样式 */
.custom-select {
  width: 220px;
}

:deep(.el-select .el-input__inner) {
  border-radius: 8px;
}

/* 表格样式 */
.custom-table {
  margin-top: 20px;
  border-radius: 8px;
  overflow: hidden;
  width: 100%;
}

:deep(.el-table) {
  width: 100% !important;
}

:deep(.el-table__header),
:deep(.el-table__body) {
  width: 100% !important;
}

:deep(.el-table .cell) {
  padding: 0 10px;
  white-space: nowrap;
}

/* 深色模式适配 */
:deep(.dark-theme .glass-card) {
  background: rgba(30, 30, 30, 0.8) !important;
  border-color: rgba(255, 255, 255, 0.1) !important;
}

:deep(.dark-theme .filter-form) {
  background: rgba(0, 0, 0, 0.2);
}

:deep(.dark-theme .title) {
  color: #E6E6E6;
}

:deep(.dark-theme .el-form-item__label) {
  color: #E6E6E6;
}

/* 成绩输入区域样式 */
.grade-input {
  width: 90px !important;
}

/* 数字输入框样式 */
:deep(.el-input-number) {
  width: 90px;
  border-radius: 4px;
  overflow: hidden;
}

:deep(.el-input-number .el-input__inner) {
  text-align: center;
  padding: 0 5px;
  height: 28px;
  line-height: 28px;
}

.save-button {
  padding: 5px 12px;
}

/* 深色模式下的输入框和按钮样式 */
:deep(.dark-theme .el-input-number) {
  background: rgba(0, 0, 0, 0.3);
  border-color: rgba(255, 255, 255, 0.1);
}

:deep(.dark-theme .el-input-number .el-input__inner) {
  background: transparent;
  color: #E6E6E6;
}

:deep(.dark-theme .el-button--mini) {
  background: rgba(64, 158, 255, 0.1);
  border-color: rgba(64, 158, 255, 0.3);
  color: #409EFF;
}

:deep(.dark-theme .el-button--mini:hover) {
  background: rgba(64, 158, 255, 0.2);
  border-color: rgba(64, 158, 255, 0.4);
}

:deep(.dark-theme .el-button--primary) {
  background: #409EFF;
  border-color: #409EFF;
  color: #fff;
}

:deep(.dark-theme .el-button--primary:hover) {
  background: #66b1ff;
  border-color: #66b1ff;
}
</style>
