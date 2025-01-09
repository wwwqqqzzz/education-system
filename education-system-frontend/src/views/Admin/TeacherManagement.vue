<template>
  <div class="teacher-management">
    <div class="operation-bar">
      <el-button type="primary" icon="el-icon-plus" @click="handleAdd">添加教师</el-button>
    </div>
    
    <el-table 
      :data="teachers" 
      border 
      stripe
      fit
      style="width: 100%"
      v-loading="loading">
      <el-table-column 
        prop="id" 
        label="教师ID" 
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
        prop="department" 
        label="院系" 
        min-width="150"
        align="center"
        show-overflow-tooltip>
      </el-table-column>
      
      <el-table-column 
        label="入职时间" 
        min-width="180"
        align="center">
        <template slot-scope="scope">
          {{ formatDate(scope.row.hireDate) }}
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
      :title="isEdit ? '编辑教师' : '添加教师'"
      :visible.sync="dialogVisible"
      width="50%">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" :disabled="isEdit" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="!isEdit">
          <el-input v-model="form.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入电话号码"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱地址"></el-input>
        </el-form-item>
        <el-form-item label="所属院系" prop="department">
          <el-input v-model="form.department" placeholder="请输入所属院系"></el-input>
        </el-form-item>
        <el-form-item label="入职时间" prop="hireDate">
          <el-date-picker
            v-model="form.hireDate"
            type="date"
            placeholder="请选择入职时间"
            value-format="yyyy-MM-dd">
          </el-date-picker>
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
  name: 'TeacherManagement',
  data() {
    return {
      loading: false,
      teachers: [],
      dialogVisible: false,
      isEdit: false,
      currentId: null,
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
      }
    }
  },
  created() {
    this.fetchTeachers()
  },
  methods: {
    async fetchTeachers() {
      try {
        this.loading = true
        const response = await request.get('/admin/teachers')
        console.log('获取到的教师数据:', response.data)
        this.teachers = response.data
      } catch (error) {
        console.error('获取教师列表失败:', error)
        this.$message.error('获取教师列表失败')
      } finally {
        this.loading = false
      }
    },
    handleAdd() {
      this.isEdit = false
      this.form = {
        username: '',
        password: '',
        name: '',
        phone: '',
        email: '',
        department: '',
        hireDate: ''
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.isEdit = true
      this.currentId = row.id
      this.form = { ...row }
      this.dialogVisible = true
    },
    async handleDelete(row) {
      try {
        await this.$confirm('确认删除该教师?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await request.delete(`/admin/teachers/${row.id}`)
        this.$message.success('删除成功')
        this.fetchTeachers()
      } catch (error) {
        if (error !== 'cancel') {
          console.error('删除失败:', error)
          this.$message.error('删除失败')
        }
      }
    },
    async submitForm() {
      this.$refs.form.validate(async valid => {
        if (valid) {
          try {
            const url = this.isEdit ? `/admin/teachers/${this.currentId}` : '/admin/teachers'
            const method = this.isEdit ? 'put' : 'post'
            const response = await request[method](url, this.form)
            console.log('提交响应:', response)
            this.$message.success(this.isEdit ? '更新成功' : '添加成功')
            this.dialogVisible = false
            this.fetchTeachers()
          } catch (error) {
            console.error(this.isEdit ? '更新失败:' : '添加失败:', error)
            this.$message.error(this.isEdit ? '更新失败' : '添加失败')
          }
        }
      })
    },
    formatDate(dateStr) {
      if (!dateStr) return '暂无数据'
      try {
        const date = new Date(dateStr)
        if (isNaN(date.getTime())) {
          return '无效日期'
        }
        return date.toLocaleString('zh-CN', {
          year: 'numeric',
          month: '2-digit',
          day: '2-digit',
          hour: '2-digit',
          minute: '2-digit'
        })
      } catch (error) {
        return '无效日期'
      }
    }
  }
}
</script>

<style scoped>
.teacher-management {
  padding: 20px;
}

.operation-bar {
  margin-bottom: 20px;
}

.dialog-footer {
  text-align: right;
}
</style>