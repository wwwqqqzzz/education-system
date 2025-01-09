<template>
  <div class="grades-container">
    <div class="glass-card">
      <div class="header">
        <div class="title">成绩查询</div>
      </div>

      <el-table 
        :data="grades" 
        border 
        stripe
        class="custom-table"
        v-loading="loading">
        <el-table-column 
          prop="course_id" 
          label="课程ID" 
          width="120"
          align="center">
        </el-table-column>
        <el-table-column 
          prop="course_name" 
          label="课程名称" 
          min-width="200"
          align="center">
        </el-table-column>
        <el-table-column 
          prop="teacher_name" 
          label="授课教师" 
          width="150"
          align="center">
        </el-table-column>
        <el-table-column 
          prop="grade" 
          label="成绩" 
          width="120"
          align="center">
          <template slot-scope="scope">
            {{ scope.row.grade || '暂无成绩' }}
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'StudentGrades',
  data() {
    return {
      grades: [],
      loading: false
    }
  },
  created() {
    this.fetchGrades()
  },
  methods: {
    async fetchGrades() {
      this.loading = true
      try {
        const response = await request.get('/student/courses')
        this.grades = response.data
      } catch (error) {
        console.error('获取成绩失败:', error)
        this.$message.error('获取成绩失败')
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.grades-container {
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

/* 表格样式 */
.custom-table {
  margin: 20px;
  border-radius: 8px;
  overflow: hidden;
  background: rgba(255, 255, 255, 0.5);
}

:deep(.el-table) {
  background-color: transparent !important;
}

:deep(.el-table th) {
  background-color: rgba(250, 250, 250, 0.8) !important;
  color: #606266;
  font-weight: 600;
  border-bottom: 1px solid #dcdfe6;
}

:deep(.el-table td) {
  border-bottom: 1px solid #ebeef5;
}

:deep(.el-table--striped .el-table__body tr.el-table__row--striped td) {
  background: rgba(250, 250, 250, 0.4);
}

:deep(.el-table--enable-row-hover .el-table__body tr:hover > td) {
  background: rgba(236, 245, 255, 0.4);
}

/* 深色模式适配 */
:deep(.dark-theme .glass-card) {
  background: rgba(30, 30, 30, 0.8) !important;
  border-color: rgba(255, 255, 255, 0.1) !important;
}

:deep(.dark-theme .title) {
  color: #E6E6E6;
}

:deep(.dark-theme .el-table) {
  background-color: transparent !important;
  color: #E6E6E6;
}

:deep(.dark-theme .el-table th) {
  background-color: rgba(45, 45, 45, 0.8) !important;
  color: #E6E6E6;
  border-bottom-color: #4c4c4c;
}

:deep(.dark-theme .el-table td) {
  border-bottom-color: #4c4c4c;
}

:deep(.dark-theme .el-table--striped .el-table__body tr.el-table__row--striped td) {
  background: rgba(255, 255, 255, 0.05);
}

:deep(.dark-theme .el-table--enable-row-hover .el-table__body tr:hover > td) {
  background: rgba(64, 158, 255, 0.1);
}

:deep(.dark-theme .header) {
  border-bottom-color: #4c4c4c;
}
</style>
