<template>
  <div class="teacher-management">
    <div class="operation-bar">
      <el-button type="primary" @click="handleAdd">添加教师</el-button>
    </div>
    
    <el-table :data="teachers" border style="width: 100%">
      <el-table-column prop="id" label="工号" width="80"></el-table-column>
      <el-table-column prop="username" label="用户名" width="120"></el-table-column>
      <el-table-column prop="name" label="姓名" width="120"></el-table-column>
      <el-table-column prop="phone" label="电话" width="120"></el-table-column>
      <el-table-column prop="email" label="邮箱" width="180"></el-table-column>
      <el-table-column prop="department" label="所属院系"></el-table-column>
      <el-table-column prop="hireDate" label="入职时间" width="180">
        <template slot-scope="scope">
          {{ scope.row.hireDate ? new Date(scope.row.hireDate).toLocaleDateString() : '未设置' }}
        </template>
      </el-table-column>
      <el-table-column prop="created_at" label="创建时间" width="180">
        <template slot-scope="scope">
          {{ new Date(scope.row.created_at).toLocaleString() }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/编辑教师对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
      <el-form :model="form" :rules="rules" ref="form" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" :disabled="isEdit"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="!isEdit">
          <el-input v-model="form.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="所属院系" prop="department">
          <el-input v-model="form.department"></el-input>
        </el-form-item>
        <el-form-item label="入职时间" prop="hireDate">
          <el-date-picker
            v-model="form.hireDate"
            type="date"
            placeholder="选择入职时间"
            value-format="yyyy-MM-dd">
          </el-date-picker>
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
  name: 'TeacherManagement',
  data() {
    return {
      teachers: [], // 教师列表数据
      dialogVisible: false, // 对话框显示状态
      dialogTitle: '添加教师',
      form: {
        username: '',
        password: '',
        name: '',
        phone: '',
        email: '',
        department: '',
        hireDate: ''
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
        department: [
          { required: true, message: '请输入所属院系', trigger: 'blur' }
        ]
      },
      isEdit: false,
      currentId: null
    };
  },
  created() {
    this.fetchTeachers();
  },
  methods: {
    // 获取教师列表
    async fetchTeachers() {
      try {
        const response = await axios.get('/admin/teachers');
        this.teachers = response.data;
      } catch (error) {
        console.error('获取教师列表失败:', error);
        this.$message.error('获取教师列表失败');
      }
    },
    // 点击添加按钮
    handleAdd() {
      this.isEdit = false;
      this.dialogTitle = '添加教师';
      this.form = {
        username: '',
        password: '',
        name: '',
        phone: '',
        email: '',
        department: '',
        hireDate: ''
      };
      this.dialogVisible = true;
    },
    // 点击编辑按钮
    handleEdit(row) {
      this.isEdit = true;
      this.currentId = row.id;
      this.dialogTitle = '编辑教师';
      this.form = {
        username: row.username,
        name: row.name,
        department: row.department,
        phone: row.phone,
        email: row.email,
        hireDate: row.hireDate ? row.hireDate.substring(0, 10) : ''
      };
      this.dialogVisible = true;
    },
    // 点击删除按钮
    async handleDelete(row) {
      try {
        await this.$confirm('确认删除该教师?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        });
        const response = await axios.delete(`/admin/teachers/${row.id}`);
        if (response.data === "Teacher deleted successfully!") {
          this.$message.success('删除成功');
          this.fetchTeachers();
        } else {
          this.$message.error('删除失败');
        }
      } catch (error) {
        if (error !== 'cancel') {
          console.error('删除教师失败:', error);
          this.$message.error('删除失败');
        }
      }
    },
    // 提交表单
    async submitForm() {
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          try {
            const formData = {
              ...(this.isEdit && { id: this.currentId }),
              username: this.form.username,
              name: this.form.name,
              department: this.form.department,
              phone: this.form.phone,
              email: this.form.email,
              hireDate: this.form.hireDate || null
            };

            if (!this.isEdit) {
              formData.password = this.form.password;
            }

            const url = this.isEdit ? 
              `/admin/teachers/${this.currentId}` : 
              '/admin/teachers';
            const method = this.isEdit ? 'put' : 'post';

            const response = await axios[method](url, formData);
            
            if (response.data.includes("successfully")) {
              this.$message.success(this.isEdit ? '更新成功' : '添加成功');
              this.dialogVisible = false;
              await this.fetchTeachers();
            } else {
              this.$message.error(this.isEdit ? '更新失败' : '添加失败');
            }
          } catch (error) {
            console.error('提交失败:', error.response?.data || error.message);
            this.$message.error(
              `${this.isEdit ? '更新' : '添加'}失败: ${error.response?.data || error.message}`
            );
          }
        }
      });
    }
  }
};
</script>

<style scoped>
.teacher-management {
  padding: 20px;
}
.operation-bar {
  margin-bottom: 20px;
}
</style>