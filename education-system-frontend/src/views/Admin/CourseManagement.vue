<template>
  <div class="course-management">
    <div class="operation-bar">
      <el-button type="primary" @click="handleAdd">添加课程</el-button>
    </div>
    
    <el-table :data="courses" border style="width: 100%">
      <el-table-column prop="id" label="课程ID" width="80"></el-table-column>
      <el-table-column prop="name" label="课程名称" width="180"></el-table-column>
      <el-table-column prop="description" label="课程描述"></el-table-column>
      <el-table-column label="任课教师" width="120">
        <template slot-scope="scope">
          {{ getTeacherName(scope.row.teacher_id) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑课程对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
      <el-form :model="form" :rules="rules" ref="form" label-width="100px">
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
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
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
        console.log('获取到的教师列表:', this.teachers);
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
</style> 