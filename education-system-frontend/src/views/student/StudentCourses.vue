<template>
  <div class="courses-container">
    <el-card>
      <div slot="header">
        <span>选课管理</span>
      </div>

      <!-- 可选课程列表 -->
      <el-table :data="availableCourses" border style="width: 100%">
        <el-table-column prop="course_id" label="课程ID" width="100"></el-table-column>
        <el-table-column prop="course_name" label="课程名称" width="180"></el-table-column>
        <el-table-column prop="description" label="课程描述"></el-table-column>
        <el-table-column prop="teacher_name" label="授课教师" width="120"></el-table-column>
        <el-table-column label="操作" width="120" fixed="right">
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

      <!-- 已选课程列表 -->
      <div class="selected-courses" style="margin-top: 20px;">
        <h3>已选课程</h3>
        <el-table :data="selectedCourses" border style="width: 100%">
          <el-table-column prop="course_id" label="课程ID" width="100"></el-table-column>
          <el-table-column prop="course_name" label="课程名称" width="180"></el-table-column>
          <el-table-column prop="description" label="课程描述"></el-table-column>
          <el-table-column prop="teacher_name" label="授课教师" width="120"></el-table-column>
          <el-table-column label="操作" width="120" fixed="right">
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
    </el-card>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'StudentCourses',
  data() {
    return {
      availableCourses: [],
      selectedCourses: []
    }
  },
  created() {
    this.fetchAvailableCourses()
    this.fetchSelectedCourses()
  },
  methods: {
    async fetchAvailableCourses() {
      try {
        const response = await request.get('/student/available-courses')
        console.log('可选课程数据:', response.data)
        this.availableCourses = response.data
      } catch (error) {
        console.error('获取可选课程失败:', error)
        this.$message.error('获取可选课程失败')
      }
    },
    async fetchSelectedCourses() {
      try {
        const response = await request.get('/student/courses')
        console.log('已选课程数据:', response.data)
        this.selectedCourses = response.data
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
        await this.fetchSelectedCourses()
        await this.fetchAvailableCourses()
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
        await this.fetchSelectedCourses()
        await this.fetchAvailableCourses()
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

.selected-courses {
  margin-top: 20px;
}
</style>
