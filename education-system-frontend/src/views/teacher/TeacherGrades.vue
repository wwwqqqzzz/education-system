<template>
  <div class="grades-management">
    <el-card>
      <div slot="header">
        <span>成绩管理</span>
      </div>
      
      <!-- 课程选择 -->
      <el-form :inline="true" class="filter-form">
        <el-form-item label="选择课程">
          <el-select v-model="selectedCourse" placeholder="请选择课程" @change="handleCourseChange">
            <el-option
              v-for="course in courses"
              :key="course.id"
              :label="course.name"
              :value="course.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <!-- 学生成绩表格 -->
      <el-table v-if="selectedCourse" :data="students" border style="width: 100%">
        <el-table-column prop="id" label="学号" width="100"></el-table-column>
        <el-table-column prop="name" label="姓名" width="120"></el-table-column>
        <el-table-column label="成绩" width="200">
          <template slot-scope="scope">
            <el-input-number
              v-model="scope.row.grade"
              :min="0"
              :max="100"
              :precision="1"
              :controls="false"
              size="mini"
              style="width: 100px">
            </el-input-number>
            <el-button
              size="mini"
              type="primary"
              @click="saveGrade(scope.row)"
              style="margin-left: 10px">
              保存
            </el-button>
          </template>
        </el-table-column>
        <el-table-column label="最后更新时间" width="180">
          <template slot-scope="scope">
            {{ scope.row.created_at ? new Date(scope.row.created_at).toLocaleString() : '未录入' }}
          </template>
        </el-table-column>
      </el-table>

      <!-- 未选择课程时的提示 -->
      <el-empty v-else description="请选择课程"></el-empty>
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

.filter-form {
  margin-bottom: 20px;
}

.el-input-number {
  width: 130px;
}

.el-table {
  margin-top: 20px;
}
</style> 