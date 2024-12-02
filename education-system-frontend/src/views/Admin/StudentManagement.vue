<template>
  <div class="student-management">
    <div class="operation-bar">
      <el-button type="primary" @click="handleAdd">添加学生</el-button>
    </div>
    
    <el-table :data="students" border style="width: 100%">
      <el-table-column prop="id" label="学号" width="80"></el-table-column>
      <el-table-column prop="username" label="用户名" width="120"></el-table-column>
      <el-table-column prop="name" label="姓名" width="120"></el-table-column>
      <el-table-column prop="phone" label="电话" width="120"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column label="入学年份" width="120">
        <template slot-scope="scope">
          {{ scope.row.enrollmentYear || scope.row.enrollment_year }}
        </template>
      </el-table-column>
      <el-table-column prop="major" label="专业" width="120"></el-table-column>
      <el-table-column label="创建时间" width="180">
        <template slot-scope="scope">
          {{ new Date(scope.row.created_at).toLocaleString() }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑学生对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
      <el-form :model="form" :rules="rules" ref="form" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" :disabled="isEdit"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="!isEdit">
          <el-input v-model="form.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="入学年份" prop="enrollmentYear">
          <el-date-picker
            v-model="form.enrollmentYear"
            type="year"
            placeholder="选择年份"
            value-format="yyyy"
            format="yyyy">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="form.major"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email"></el-input>
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
import request from '@/utils/request'

export default {
  name: 'StudentManagement',
  data() {
    return {
      students: [],
      dialogVisible: false,
      dialogTitle: '添加学生',
      form: {
        username: '',
        password: '',
        name: '',
        enrollmentYear: '',
        major: '',
        phone: '',
        email: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        email: [
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ],
        phone: [
          { pattern: /^[0-9]{11}$/, message: '请输入正确的手机号码', trigger: 'blur' }
        ],
        enrollmentYear: [
          { required: true, message: '请选择入学年份', trigger: 'change' }
        ],
        major: [
          { required: true, message: '请输入专业', trigger: 'blur' }
        ]
      },
      isEdit: false,
      currentId: null
    };
  },
  created() {
    this.fetchStudents();
  },
  methods: {
    async fetchStudents() {
      try {
        const response = await request.get('/admin/students')
        this.students = response.data.map(student => ({
          ...student,
          enrollmentYear: student.enrollment_year
        }));
      } catch (error) {
        console.error('获取学生列表失败:', error)
        this.$message.error('获取学生列表失败')
      }
    },
    handleAdd() {
      this.isEdit = false;
      this.dialogTitle = '添加学生';
      this.form = {
        username: '',
        password: '',
        name: '',
        enrollmentYear: '',
        major: '',
        phone: '',
        email: ''
      };
      this.dialogVisible = true;
    },
    handleEdit(row) {
      this.isEdit = true;
      this.currentId = row.id;
      this.dialogTitle = '编辑学生';
      this.form = {
        username: row.username,
        name: row.name,
        enrollmentYear: row.enrollment_year ? row.enrollment_year.toString() : '',
        major: row.major,
        phone: row.phone,
        email: row.email
      };
      this.dialogVisible = true;
    },
    async handleDelete(row) {
      try {
        await this.$confirm('确认删除该学生?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        });
        await request.delete(`/admin/students/${row.id}`);
        this.$message.success('删除成功');
        this.fetchStudents();
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
              ...this.form,
              enrollmentYear: parseInt(this.form.enrollmentYear)
            };
            
            if (this.isEdit) {
              const updateData = {
                username: formData.username,
                name: formData.name,
                enrollmentYear: formData.enrollmentYear,
                major: formData.major,
                phone: formData.phone,
                email: formData.email
              };
              await request.put(`/admin/students/${this.currentId}`, updateData);
              this.$message.success('更新成功');
            } else {
              await request.post('/admin/students', formData);
              this.$message.success('添加成功');
            }
            this.dialogVisible = false;
            this.fetchStudents();
          } catch (error) {
            this.$message.error(this.isEdit ? '更新失败' : '添加失败');
          }
        }
      });
    }
  }
};
</script>

<style scoped>
.student-management {
  padding: 20px;
}
.operation-bar {
  margin-bottom: 20px;
}

.el-table {
  margin-top: 20px;
}

/* 美化表格样式 */
:deep(.el-table) {
  border-radius: 4px;
  overflow: hidden;
}

:deep(.el-table th) {
  background-color: #f5f7fa;
  color: #606266;
  font-weight: 500;
  text-align: center;
}

:deep(.el-table td) {
  padding: 8px 0;
  text-align: center;
}

:deep(.el-button--mini) {
  padding: 6px 12px;
}

/* 确保操作列的按钮对齐 */
.el-button + .el-button {
  margin-left: 8px;
}
</style> 