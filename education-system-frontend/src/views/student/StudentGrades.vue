<template>
  <div class="grades-container">
    <el-card>
      <div slot="header">
        <span>成绩查询</span>
      </div>

      <el-table :data="grades" border style="width: 100%">
        <el-table-column prop="course_id" label="课程ID" width="100"></el-table-column>
        <el-table-column prop="course_name" label="课程名称" width="180"></el-table-column>
        <el-table-column prop="teacher_name" label="授课教师" width="120"></el-table-column>
        <el-table-column prop="grade" label="成绩" width="100">
          <template slot-scope="scope">
            {{ scope.row.grade !== null ? scope.row.grade : '暂无成绩' }}
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'StudentGrades',
  data() {
    return {
      grades: []
    }
  },
  created() {
    this.fetchGrades()
  },
  methods: {
    async fetchGrades() {
      try {
        const response = await request.get('/student/courses')
        this.grades = response.data
      } catch (error) {
        console.error('获取成绩失败:', error)
        this.$message.error('获取成绩失败')
      }
    }
  }
}
</script>

<style scoped>
.grades-container {
  padding: 20px;
}
</style>
