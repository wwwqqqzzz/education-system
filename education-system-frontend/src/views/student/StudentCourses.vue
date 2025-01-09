<template>
  <div class="courses-container">
    <!-- 已选课程 -->
    <div class="glass-card">
      <div class="header">
        <div class="title">已选课程</div>
      </div>
      <el-table 
        :data="selectedCourses" 
        border 
        stripe
        class="custom-table"
        v-loading="loading">
        <el-table-column prop="course_id" label="课程ID" width="120" align="center"></el-table-column>
        <el-table-column prop="course_name" label="课程名称" min-width="200" align="center"></el-table-column>
        <el-table-column prop="teacher_name" label="授课教师" width="150" align="center"></el-table-column>
        <el-table-column prop="grade" label="成绩" width="120" align="center">
          <template slot-scope="scope">
            {{ scope.row.grade || '暂无' }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120" align="center">
          <template slot-scope="scope">
            <el-button 
              size="mini" 
              type="danger"
              @click="dropCourse(scope.row)">
              退课
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 可选课程 -->
    <div class="glass-card">
      <div class="header">
        <div class="title">可选课程</div>
      </div>
      <el-table 
        :data="availableCourses" 
        border 
        stripe
        class="custom-table"
        v-loading="loading">
        <el-table-column prop="course_id" label="课程ID" width="120" align="center"></el-table-column>
        <el-table-column prop="course_name" label="课程名称" width="200" align="center"></el-table-column>
        <el-table-column prop="description" label="课程描述" min-width="300" align="center"></el-table-column>
        <el-table-column prop="teacher_name" label="授课教师" width="150" align="center"></el-table-column>
        <el-table-column label="操作" width="120" align="center">
          <template slot-scope="scope">
            <el-button 
              size="mini" 
              type="primary"
              @click="selectCourse(scope.row)"
              :disabled="isSelected(scope.row.course_id)">
              {{ isSelected(scope.row.course_id) ? '已选' : '选课' }}
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
  name: 'StudentCourses',
  data() {
    return {
      availableCourses: [],
      selectedCourses: [],
      coursesMap: {},  // 用于存储所有课程的完整信息
      loading: false
    }
  },
  created() {
    this.initData()
  },
  methods: {
    async initData() {
      this.loading = true
      try {
        // 先获取可选课程
        await this.fetchAvailableCourses()
        // 再获取已选课程并合并课程信息
        await this.fetchSelectedCourses()
      } finally {
        this.loading = false
      }
    },
    async fetchAvailableCourses() {
      try {
        const response = await request.get('/student/available-courses')
        console.log('可选课程数据:', response.data)
        this.availableCourses = response.data
        
        // 创建课程信息映射
        this.coursesMap = response.data.reduce((map, course) => {
          map[course.course_id] = course
          return map
        }, {})
      } catch (error) {
        console.error('获取可选课程失败:', error)
        this.$message.error('获取可选课程失败')
      }
    },
    async fetchSelectedCourses() {
      try {
        const response = await request.get('/student/courses')
        console.log('原始已选课程数据:', response.data)
        
        // 使用课程映射补充已选课程的信息
        this.selectedCourses = response.data.map(selectedCourse => {
          const fullCourseInfo = this.coursesMap[selectedCourse.course_id]
          if (fullCourseInfo) {
            return {
              ...selectedCourse,
              description: fullCourseInfo.description,
              course_name: fullCourseInfo.course_name || selectedCourse.course_name,
              teacher_name: fullCourseInfo.teacher_name || selectedCourse.teacher_name
            }
          }
          return {
            ...selectedCourse,
            description: '暂无描述'
          }
        })
        
        console.log('处理后的已选课程数据:', this.selectedCourses)
      } catch (error) {
        console.error('获取已选课程失败:', error)
        this.$message.error('获取已选课程失败')
      }
    },
    isSelected(courseId) {
      return this.selectedCourses.some(course => course.course_id === courseId)
    },
    async selectCourse(course) {
      try {
        await request.post(`/student/courses/${course.course_id}/select`)
        this.$message.success('选课成功')
        // 选课后重新获取数据
        await this.initData()
      } catch (error) {
        console.error('选课失败:', error)
        this.$message.error('选课失败')
      }
    },
    async dropCourse(course) {
      try {
        await this.$confirm('确认退选该课程?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await request.delete(`/student/courses/${course.course_id}/drop`)
        this.$message.success('退课成功')
        // 退课后重新获取数据
        await this.initData()
      } catch (error) {
        if (error !== 'cancel') {
          console.error('退课失败:', error)
          this.$message.error('退课失败')
        }
      }
    }
  }
}
</script>

<style scoped>
.courses-container {
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
  margin-bottom: 20px;
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

:deep(.dark-theme .header) {
  border-bottom-color: #4c4c4c;
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
  color: #E6E6E6;
}

:deep(.dark-theme .el-table--striped .el-table__body tr.el-table__row--striped td) {
  background: rgba(255, 255, 255, 0.05);
}

:deep(.dark-theme .el-table--enable-row-hover .el-table__body tr:hover > td) {
  background: rgba(64, 158, 255, 0.1);
}

/* 按钮样式 */
:deep(.el-button--primary) {
  color: #fff !important;
}

:deep(.el-button--danger) {
  color: #fff !important;
}

:deep(.dark-theme .el-button--primary),
:deep(.dark-theme .el-button--danger) {
  color: #fff !important;
}

:deep(.dark-theme .el-button.is-disabled) {
  color: rgba(255, 255, 255, 0.5) !important;
}

/* 消息框样式 */
:deep(.dark-theme .el-message-box) {
  background: rgba(30, 30, 30, 0.95) !important;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

:deep(.dark-theme .el-message-box__title) {
  color: #E6E6E6;
}

:deep(.dark-theme .el-message-box__content) {
  color: #E6E6E6;
}

:deep(.dark-theme .el-message-box__container) {
  background: rgba(30, 30, 30, 0.95);
}
</style>
