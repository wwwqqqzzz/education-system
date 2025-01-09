<template>
  <div class="student-management">
    <div class="operation-bar">
      <el-button type="primary" icon="el-icon-plus" @click="handleAdd">添加学生</el-button>
    </div>

    <el-table
      :data="students"
      border
      stripe
      fit
      style="width: 100%"
      v-loading="loading">
      <el-table-column
        prop="id"
        label="学生ID"
        min-width="80"
        fixed
        align="center">
      </el-table-column>

      <el-table-column
        prop="username"
        label="用户名"
        min-width="120"
        align="center">
      </el-table-column>

      <el-table-column
        prop="name"
        label="姓名"
        min-width="100"
        align="center">
      </el-table-column>

      <el-table-column
        prop="phone"
        label="电话"
        min-width="120"
        align="center">
      </el-table-column>

      <el-table-column
        prop="email"
        label="邮箱"
        min-width="200"
        align="center"
        show-overflow-tooltip>
      </el-table-column>

      <el-table-column
        prop="enrollmentYear"
        label="入学年份"
        min-width="100"
        align="center">
      </el-table-column>

      <el-table-column
        prop="major"
        label="专业"
        min-width="150"
        align="center"
        show-overflow-tooltip>
      </el-table-column>

      <el-table-column
        label="创建时间"
        min-width="180"
        align="center">
        <template slot-scope="scope">
          {{ formatDate(scope.row.createdAt) }}
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
      :title="isEdit ? '编辑学生' : '添加学生'"
      :visible.sync="dialogVisible"
      width="50%">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
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
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
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
        const response = await request.get('/admin/students');
        console.log('原始学生数据:', response.data);
        this.students = response.data;
        console.log('处理后的学生数据:', this.students);
      } catch (error) {
        console.error('获取学生列表失败:', error);
        this.$message.error('获取学生列表失败');
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
        enrollmentYear: row.enrollmentYear ? row.enrollmentYear.toString() : '',
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
              enrollment_year: this.form.enrollmentYear
            };

            if (this.isEdit) {
              await request.put(`/admin/students/${this.currentId}`, formData);
              this.$message.success('更新成功');
            } else {
              await request.post('/admin/students', formData);
              this.$message.success('添加成功');
            }
            this.dialogVisible = false;
            this.fetchStudents();
          } catch (error) {
            console.error('提交失败:', error);
            this.$message.error(this.isEdit ? '更新失败' : '添加失败');
          }
        }
      });
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
.management-container {
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.operation-bar {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-table {
  margin-top: 20px;
  border-radius: 8px;
  overflow: hidden;
  table-layout: fixed !important;
}

.el-table__body {
  width: 100% !important;
}

.el-table__header {
  width: 100% !important;
}

.el-table .cell {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.el-button {
  border-radius: 6px;
  padding: 8px 16px;

  &--mini {
    padding: 6px 12px;
  }
}

.el-dialog {
  border-radius: 8px;

  .el-dialog__header {
    padding: 20px;
    border-bottom: 1px solid #e4e7ed;
  }

  .el-dialog__body {
    padding: 30px 20px;
  }

  .el-dialog__footer {
    padding: 20px;
    border-top: 1px solid #e4e7ed;
  }
}

.el-form-item {
  margin-bottom: 22px;
}
</style>
