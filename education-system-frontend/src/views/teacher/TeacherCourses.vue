<template>
  <div class="courses-management">
    <!-- 课程列表 -->
    <div class="glass-card">
      <div class="header">
        <div class="title">我的课程</div>
        <el-button 
          type="primary" 
          icon="el-icon-plus"
          @click="showAddCourseDialog">
          新增课程
        </el-button>
      </div>
      <el-table 
        :data="courses" 
        border 
        stripe
        v-loading="loading"
        class="course-table">
        <el-table-column 
          prop="id" 
          label="课程ID" 
          width="100" 
          align="center">
        </el-table-column>
        <el-table-column 
          prop="name" 
          label="课程名称" 
          width="180" 
          align="center">
        </el-table-column>
        <el-table-column 
          prop="description" 
          label="课程描述" 
          min-width="200" 
          align="center">
        </el-table-column>
        <el-table-column 
          label="创建时间" 
          width="180" 
          align="center">
          <template slot-scope="scope">
            {{ formatDate(scope.row.createdAt) }}
          </template>
        </el-table-column>
        <el-table-column 
          label="操作" 
          width="250" 
          align="center">
          <template slot-scope="scope">
            <el-button 
              size="mini" 
              type="primary" 
              @click="viewStudents(scope.row)">
              查看学生
            </el-button>
            <el-button 
              size="mini" 
              type="success" 
              @click="manageGrades(scope.row)">
              成绩管理
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 学生列表对话框 -->
    <el-dialog 
      title="课程学生列表" 
      :visible.sync="studentsDialogVisible"
      width="70%">
      <el-table 
        :data="courseStudents" 
        border 
        stripe
        v-loading="studentsLoading">
        <el-table-column prop="id" label="学号" width="120"></el-table-column>
        <el-table-column prop="name" label="姓名" width="120"></el-table-column>
        <el-table-column prop="major" label="专业"></el-table-column>
        <el-table-column prop="grade" label="成绩" width="100"></el-table-column>
        <el-table-column label="操作" width="250" align="center">
          <template slot-scope="scope">
            <el-button 
              size="mini" 
              type="danger" 
              @click="handleRemoveStudent(scope.row)">
              移除学生
            </el-button>
            <el-button 
              size="mini" 
              type="warning" 
              @click="handleDropCourse(scope.row)">
              退课
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <!-- 添加新增课程对话框 -->
    <el-dialog
      title="新增课程"
      :visible.sync="addCourseDialogVisible"
      width="50%">
      <el-form 
        :model="courseForm" 
        :rules="courseRules"
        ref="courseForm"
        label-width="100px">
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="courseForm.name" placeholder="请输入课程名称"></el-input>
        </el-form-item>
        <el-form-item label="课程描述" prop="description">
          <el-input 
            type="textarea"
            v-model="courseForm.description"
            placeholder="请输入课程描述"
            :rows="3">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addCourseDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleAddCourse">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'TeacherCourses',
  data() {
    return {
      loading: false,
      courses: [],
      studentsDialogVisible: false,
      studentsLoading: false,
      courseStudents: [],
      currentCourseId: null,
      studentCounts: {}, // 用于存储每个课程的学生数量
      addCourseDialogVisible: false,
      courseForm: {
        name: '',
        description: ''
      },
      courseRules: {
        name: [
          { required: true, message: '请输入课程名称', trigger: 'blur' },
          { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入课程描述', trigger: 'blur' },
          { max: 500, message: '最多输入500个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.fetchCourses()
  },
  methods: {
    async fetchCourses() {
      try {
        this.loading = true
        const response = await request.get('/teacher/courses')
        this.courses = response.data

        // 获取每个课程的学生数量
        for (const course of this.courses) {
          const studentsResponse = await request.get(`/teacher/courses/${course.id}/students`)
          // 直接保存到 studentCounts 对象中
          this.studentCounts[course.id] = studentsResponse.data.length
        }
      } catch (error) {
        console.error('获取课程列表失败:', error)
        this.$message.error('获取课程列表失败')
      } finally {
        this.loading = false
      }
    },
    async viewStudents(course) {
      try {
        this.studentsLoading = true
        this.currentCourseId = course.id
        this.studentsDialogVisible = true
        const response = await request.get(`/teacher/courses/${course.id}/students`)
        this.courseStudents = response.data
        // 保存学生数量
        this.studentCounts[course.id] = this.courseStudents.length
      } catch (error) {
        console.error('获取学生列表失败:', error)
        this.$message.error('获取学生列表失败')
      } finally {
        this.studentsLoading = false
      }
    },
    manageGrades(course) {
      this.$router.push({
        path: '/teacher/grades',
        query: { courseId: course.id }
      })
    },
    async handleRemoveStudent(student) {
      try {
        await this.$confirm('确认移除该学生？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await request.delete(`/teacher/courses/${this.currentCourseId}/students/${student.id}`)
        this.$message.success('移除成功')
        this.viewStudents({ id: this.currentCourseId }) // 刷新学生列表
      } catch (error) {
        if (error !== 'cancel') {
          console.error('移除学生失败:', error)
          this.$message.error('移除学生失败')
        }
      }
    },
    // 获取指定课程的学生数量
    getStudentCount(courseId) {
      return this.studentCounts[courseId] || 0
    },
    formatDate(dateStr) {
      if (!dateStr) return '暂无数据'
      try {
        const date = new Date(dateStr)
        return date.toLocaleString('zh-CN', {
          year: 'numeric',
          month: '2-digit',
          day: '2-digit',
          hour: '2-digit',
          minute: '2-digit'
        })
      } catch (error) {
        return '无效日期'
      }
    },
    // 显示新增课程对话框
    showAddCourseDialog() {
      this.addCourseDialogVisible = true
      this.courseForm = {
        name: '',
        description: ''
      }
    },
    // 处理新增课程
    async handleAddCourse() {
      try {
        await this.$refs.courseForm.validate()
        
        await request.post('/teacher/courses', this.courseForm)
        
        this.$message.success('课程添加成功')
        this.addCourseDialogVisible = false
        
        // 重新获取课程列表
        await this.fetchCourses()
      } catch (error) {
        if (error === false) {
          // 表单验证失败
          return
        }
        console.error('添加课程失败:', error)
        this.$message.error('添加课程失败')
      }
    },
    // 添加退课方法
    async handleDropCourse(student) {
      try {
        await this.$confirm('确认让该学生退出课程?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        
        await request.delete(`/teacher/courses/${this.currentCourseId}/students/${student.id}/drop`)
        this.$message.success('退课成功')
        // 重新获取学生列表
        await this.viewStudents({ id: this.currentCourseId })
        // 更新课程列表（因为学生数量可能发生变化）
        await this.fetchCourses()
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
/* 基础样式 */
.courses-management {
  padding: 20px;
  min-height: calc(100vh - 120px);
  transition: all 0.3s;
}

/* 玻璃拟态卡片效果 - 移除重复的深色模式样式 */
.glass-card {
  backdrop-filter: blur(10px) !important;
  border-radius: 16px !important;
  overflow: hidden !important;
  padding: 20px;
  transition: all 0.3s;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 10px;
  margin-bottom: 20px;
}

.title {
  font-size: 18px;
  font-weight: 600;
}

/* 保留一些特殊的自定义样式 */
:deep(.el-table) {
  border-radius: 8px;
  overflow: hidden;
}

:deep(.el-button) {
  padding: 8px 16px;
}

:deep(.el-dialog) {
  border-radius: 12px;
  overflow: hidden;
}

/* 添加新增按钮样式 */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

/* 深色模式适配 */
.dark-theme :deep(.el-dialog__body) {
  background: var(--bg-color);
  color: var(--text-color);
}

.dark-theme :deep(.el-dialog__header) {
  background: var(--bg-color-light);
  color: var(--text-color);
}

.dark-theme :deep(.el-dialog__footer) {
  background: var(--bg-color-light);
  border-top: 1px solid var(--border-color);
}
</style> 