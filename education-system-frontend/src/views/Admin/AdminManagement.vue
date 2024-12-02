<template>
  <div class="admin-management">
    <div class="operation-bar">
      <el-button type="primary" @click="handleAdd">添加管理员</el-button>
    </div>
    
    <el-table :data="admins" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="username" label="用户名" width="120"></el-table-column>
      <el-table-column prop="name" label="姓名" width="120"></el-table-column>
      <el-table-column prop="phone" label="电话" width="120"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
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

    <!-- 添加/编辑管理员对话框 -->
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

const api = axios.create({
  baseURL: 'http://localhost:9099',
  timeout: 5000,
  withCredentials: true
});

// 添加请求拦截器
api.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  config.headers['Content-Type'] = 'application/json';
  return config;
}, error => {
  return Promise.reject(error);
});

// 添加响应拦截器
api.interceptors.response.use(response => {
  return response;
}, error => {
  if (error.response) {
    switch (error.response.status) {
      case 401:
        this.$message.error('未授权，请重新登录');
        localStorage.removeItem('token');
        this.$router.push('/login');
        break;
      case 403:
        this.$message.error('没有权限访问');
        break;
      default:
        this.$message.error(error.response.data.message || '请求失败');
    }
  } else {
    this.$message.error('网络错误，请稍后重试');
  }
  return Promise.reject(error);
});

export default {
  name: 'AdminManagement',
  data() {
    return {
      admins: [],
      dialogVisible: false,
      dialogTitle: '添加管理员',
      form: {
        username: '',
        password: '',
        name: '',
        phone: '',
        email: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ],
        password: [
          { required: !this.isEdit, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        email: [
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ],
        phone: [
          { pattern: /^[0-9]{11}$/, message: '请输入正确的手机号码', trigger: 'blur' }
        ]
      },
      isEdit: false,
      currentId: null
    };
  },
  created() {
    this.fetchAdmins();
  },
  methods: {
    async fetchAdmins() {
      try {
        const response = await api.get('/admin/admins');
        if (response.data && Array.isArray(response.data)) {
          this.admins = response.data;
        } else {
          this.$message.error('获取数据失败：数据格式错误');
        }
      } catch (error) {
        console.error('获取管理员列表失败:', error);
        this.$message.error('获取管理员列表失败: ' + (error.response?.data?.message || error.message));
      }
    },
    handleAdd() {
      this.isEdit = false;
      this.dialogTitle = '添加管理员';
      this.form = {
        username: '',
        password: '',
        name: '',
        phone: '',
        email: ''
      };
      this.dialogVisible = true;
    },
    handleEdit(row) {
      this.isEdit = true;
      this.currentId = row.id;
      this.dialogTitle = '编辑管理员';
      this.form = {
        username: row.username,
        name: row.name,
        phone: row.phone,
        email: row.email
      };
      this.dialogVisible = true;
    },
    async handleDelete(row) {
      try {
        await this.$confirm('确认删除该管理员?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        });
        await api.delete(`/admin/admins/${row.id}`);
        this.$message.success('删除成功');
        this.fetchAdmins();
      } catch (error) {
        if (error !== 'cancel') {
          console.error('删除失败:', error.response?.data || error.message);
          this.$message.error('删除失败: ' + (error.response?.data?.message || error.message));
        }
      }
    },
    async submitForm() {
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          try {
            if (this.isEdit) {
              const updateData = {
                username: this.form.username,
                name: this.form.name,
                phone: this.form.phone,
                email: this.form.email
              };
              await api.put(`/admin/admins/${this.currentId}`, updateData);
              this.$message.success('更新成功');
              this.dialogVisible = false;
              this.fetchAdmins();
            } else {
              await api.post('/admin/admins', this.form);
              this.$message.success('添加成功');
              this.dialogVisible = false;
              this.fetchAdmins();
            }
          } catch (error) {
            console.error('提交表单失败:', error.response?.data || error.message);
            this.$message.error(this.isEdit ? 
              '更新失败: ' + (error.response?.data?.message || error.message) : 
              '添加失败: ' + (error.response?.data?.message || error.message)
            );
          }
        }
      });
    }
  }
};
</script>

<style scoped>
.admin-management {
  padding: 20px;
}
.operation-bar {
  margin-bottom: 20px;
}
</style> 