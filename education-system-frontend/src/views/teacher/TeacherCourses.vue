<template>
  <div class="courses-management">
    <el-card>
      <div slot="header">
        <span>我的课程</span>
      </div>

      <!-- 课程列表 -->
      <el-table :data="courses" border style="width: 100%">
        <el-table-column prop="id" label="课程ID" width="100"></el-table-column>
        <el-table-column prop="name" label="课程名称" width="180"></el-table-column>
        <el-table-column prop="description" label="课程描述"></el-table-column>
        <el-table-column label="选课人数" width="120">
          <template slot-scope="scope">
            {{ studentCounts[scope.row.id] || 0 }}
          </template>
        </el-table-column>
        <el-table-column label="创建时间" width="180">
          <template slot-scope="scope">
            {{ new Date(scope.row.created_at).toLocaleString() }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
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
              管理成绩
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 学生列表对话框 -->
    <el-dialog title="选课学生列表" :visible.sync="dialogVisible" width="70%">
      <el-table :data="students" border>
        <el-table-column prop="id" label="学号" width="100"></el-table-column>
        <el-table-column prop="name" label="姓名" width="120"></el-table-column>
        <el-table-column prop="major" label="专业"></el-table-column>
        <el-table-column prop="enrollment_year" label="入学年份" width="120"></el-table-column>
        <el-table-column prop="phone" label="电话"></el-table-column>
        <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column label="成绩" width="100">
          <template slot-scope="scope">
            {{ scope.row.grade || '未录入' }}
          </template>
        </el-table-column>
        <el-table-column label="最后更新时间" width="180">
          <template slot-scope="scope">
            {{ scope.row.created_at ? new Date(scope.row.created_at).toLocaleString() : '-' }}
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'TeacherCourses',
  data() {
    return {
      courses: [],
      students: [],
      studentCounts: {},
      dialogVisible: false
    }
  },
  created() {
    this.fetchCourses()
  },
  methods: {
    // 获取教师的课程列表
    async fetchCourses() {
      try {
        const response = await request.get('/teacher/courses')
        this.courses = response.data
        // 获取每个课程的选课人数
        this.courses.forEach(course => {
          this.fetchStudentCount(course.id)
        })
      } catch (error) {
        console.error('获取课程列表失败:', error)
        this.$message.error('获取课程列表失败')
      }
    },

    // 获取课程选课人数
    async fetchStudentCount(courseId) {
      try {
        const response = await request.get(`/teacher/courses/${courseId}/student-count`)
        this.$set(this.studentCounts, courseId, response.data)
      } catch (error) {
        console.error('获取选课人数失败:', error)
        this.$set(this.studentCounts, courseId, 0)
      }
    },

    // 查看选课学生
    async viewStudents(course) {
      try {
        const response = await request.get(`/teacher/courses/${course.id}/students`)
        console.log('获取到的学生数据:', response.data)
        this.students = response.data
        this.dialogVisible = true
      } catch (error) {
        console.error('获取学生列表失败:', error)
        this.$message.error('获取学生列表失败')
      }
    },

    // 跳转到成绩管理页面
    manageGrades(course) {
      this.$router.push({
        name: 'TeacherGrades',
        query: { courseId: course.id }
      })
    }
  }
}
</script>

<style scoped>
.courses-management {
  padding: 20px;
}

.el-table {
  margin-top: 20px;
}

.operation-buttons {
  display: flex;
  gap: 10px;
}
</style> 