<template>
  <div class="course-management">
    <div class="operation-bar">
      <el-button type="primary" icon="el-icon-plus" @click="handleAdd">添加课程</el-button>
    </div>

    <el-table
      :data="courses"
      border
      stripe
      fit
      style="width: 100%"
      v-loading="loading">
      <el-table-column
        prop="id"
        label="课程ID"
        min-width="80"
        fixed
        align="center">
      </el-table-column>

      <el-table-column
        prop="name"
        label="课程名称"
        min-width="120"
        align="center">
      </el-table-column>

      <el-table-column
        prop="description"
        label="课程描述"
        min-width="200"
        align="center"
        show-overflow-tooltip>
      </el-table-column>

      <el-table-column
        label="任课教师"
        min-width="120"
        align="center">
        <template slot-scope="scope">
          {{ scope.row.teacherName || '未分配' }}
        </template>
      </el-table-column>

      <el-table-column
        label="操作"
        min-width="150"
        fixed="right"
        align="center">
        <template slot-scope="scope">
          <div class="table-operations">
            <el-button
              size="mini"
              type="primary"
              icon="el-icon-edit"
              @click="handleEdit(scope.row)">
              编辑
            </el-button>
            <el-button
              size="mini"
              type="danger"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)">
              删除
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      :title="isEdit ? '编辑课程' : '添加课程'"
      :visible.sync="dialogVisible"
      width="50%">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="课程描述" prop="description">
          <el-input type="textarea" v-model="form.description"></el-input>
        </el-form-item>
        <el-form-item label="任课教师" prop="teacher_id">
          <el-select
            v-model="form.teacher_id"
            placeholder="请选择教师"
            @change="handleTeacherSelect">
            <el-option
              v-for="teacher in teachers"
              :key="teacher.id"
              :label="teacher.name"
              :value="teacher.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'CourseManagement',
  data() {
    return {
      courses: [],
      teachers: [],
      dialogVisible: false,
      dialogTitle: '添加课程',
      form: {
        name: '',
        description: '',
        teacher_id: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入课程名称', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入课程描述', trigger: 'blur' }
        ],
        teacher_id: [
          { required: true, message: '请选择任课教师', trigger: 'change' }
        ]
      },
      isEdit: false,
      currentId: null
    };
  },
  created() {
    this.fetchCourses();
    this.fetchTeachers();
  },
  methods: {
    async fetchCourses() {
      try {
        const response = await axios.get('/admin/courses');
        this.courses = response.data;
      } catch (error) {
        this.$message.error('获取课程列表失败');
      }
    },
    async fetchTeachers() {
      try {
        const response = await axios.get('/admin/teachers');
        this.teachers = response.data;
        console.log('获取的教师列表:', this.teachers);
      } catch (error) {
        console.error('获取教师列表失败:', error);
        this.$message.error('获取教师列表失败');
      }
    },
    getTeacherName(teacherId) {
      if (!teacherId) return '未分配';
      const teacher = this.teachers.find(t => t.id === parseInt(teacherId));
      return teacher ? teacher.name : '未分配';
    },
    handleAdd() {
      this.isEdit = false;
      this.dialogTitle = '添加课程';
      this.form = {
        name: '',
        description: '',
        teacher_id: ''
      };
      this.dialogVisible = true;
    },
    handleEdit(row) {
      this.isEdit = true;
      this.currentId = row.id;
      this.dialogTitle = '编辑课程';
      this.form = {
        name: row.name,
        description: row.description,
        teacher_id: row.teacher_id ? row.teacher_id.toString() : ''
      };
      this.dialogVisible = true;
    },
    async handleDelete(row) {
      try {
        await this.$confirm('确认删除该课程?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        });
        await axios.delete(`/admin/courses/${row.id}`);
        this.$message.success('删除成功');
        this.fetchCourses();
      } catch (error) {
        if (error !== 'cancel') {
          this.$message.error('删除失败');
        }
      }
    },
    async submitForm() {
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          try {
            const formData = {
              name: this.form.name,
              description: this.form.description,
              teacherId: parseInt(this.form.teacher_id)
            };

            console.log('提交的数据:', formData);
            console.log('选中的教师ID:', this.form.teacher_id);
            console.log('可选教师列表:', this.teachers);

            if (this.isEdit) {
              await axios.put(`/admin/courses/${this.currentId}`, formData);
              this.$message.success('更新成功');
            } else {
              await axios.post('/admin/courses', formData);
              this.$message.success('添加成功');
            }
            this.dialogVisible = false;
            await this.fetchCourses();
          } catch (error) {
            console.error('提交失败:', error.response?.data || error.message);
            this.$message.error(
              `${this.isEdit ? '更新' : '添加'}失败: ${error.response?.data || error.message}`
            );
          }
        }
      });
    },
    handleTeacherSelect(value) {
      console.log('选择的教师ID:', value);
      this.form.teacher_id = parseInt(value);
    },
    formatDate(dateStr) {
      if (!dateStr) return '暂无数据';
      try {
        const date = new Date(dateStr);
        if (isNaN(date.getTime())) {
          return '无效日期';
        }
        return date.toLocaleString('zh-CN', {
          year: 'numeric',
          month: '2-digit',
          day: '2-digit',
          hour: '2-digit',
          minute: '2-digit',
          second: '2-digit'
        });
      } catch (error) {
        return '无效日期';
      }
    }
  }
};
</script>

<style scoped>
.course-management {
  padding: 20px;
}

.operation-bar {
  margin-bottom: 20px;
}

.dialog-footer {
  text-align: right;
}
</style>
