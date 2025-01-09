<script>
import request from '@/utils/request'

export default {
  name: "Login",
  data() {
    const validateConfirmPassword = (rule, value, callback) => {
      if (value !== this.registerForm.password) {
        callback(new Error('两次输入的密码不一致'));
      } else {
        callback();
      }
    };
    
    const validateResetConfirmPassword = (rule, value, callback) => {
      if (value !== this.resetPasswordForm.newPassword) {
        callback(new Error('两次输入的密码不一致'));
      } else {
        callback();
      }
    };
    
    return {
      loginForm: {
        username: "",
        password: "",
        role: "",
      },
      rules: {
        username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        role: [{ required: true, message: "请选择角色", trigger: "change" }],
      },
      registerDialogVisible: false,
      registerForm: {
        username: '',
        password: '',
        confirmPassword: '',
        role: '',
        email: ''
      },
      registerRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: validateConfirmPassword, trigger: 'blur' }
        ],
        role: [
          { required: true, message: '请选择角色', trigger: 'change' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ]
      },
      resetPasswordDialogVisible: false,
      resetPasswordForm: {
        username: '',
        newPassword: '',
        confirmPassword: '',
        role: ''
      },
      resetPasswordRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入新密码', trigger: 'blur' },
          { validator: validateResetConfirmPassword, trigger: 'blur' }
        ],
        role: [
          { required: true, message: '请选择角色', trigger: 'change' }
        ]
      },
      currentForm: 'login',
    };
  },
  methods: {
    async handleLogin() {
      try {
        const response = await request.post(`/${this.loginForm.role}/login`, {
          username: this.loginForm.username,
          password: this.loginForm.password
        });

        if (response.data.success) {
          this.$message.success('登录成功');
          localStorage.setItem('role', this.loginForm.role);
          localStorage.setItem('username', this.loginForm.username);

          switch(this.loginForm.role) {
            case 'admin':
              this.$router.push('/admin/dashboard');
              break;
            case 'teacher':
              this.$router.push('/teacher/dashboard');
              break;
            case 'student':
              this.$router.push('/student/dashboard');
              break;
          }
        } else {
          this.$message.error(response.data.message || '登录失败');
        }
      } catch (error) {
        console.error('Login error:', error);
        this.$message.error('登录失败: ' + (error.response?.data?.message || error.message));
      }
    },
    showRegisterDialog() {
      this.registerDialogVisible = true;
    },
    showResetPasswordDialog() {
      this.resetPasswordDialogVisible = true;
    },
    async handleRegister() {
      try {
        await this.$refs.registerForm.validate();
        const response = await request.post('/auth/register', {
          username: this.registerForm.username,
          password: this.registerForm.password,
          role: this.registerForm.role,
          email: this.registerForm.email
        });
        
        if (response.data.success) {
          this.$message.success('注册成功,请登录');
          this.$refs.registerForm.resetFields();
          this.loginForm.username = this.registerForm.username;
          this.loginForm.role = this.registerForm.role;
          this.currentForm = 'login';
        } else {
          this.$message.error(response.data.message || '注册失败');
        }
      } catch (error) {
        console.error('Registration error:', error);
        this.$message.error('注册失败: ' + (error.response?.data?.message || error.message));
      }
    },
    async handleResetPassword() {
      try {
        await this.$refs.resetPasswordForm.validate();
        const response = await request.post('/auth/reset-password', {
          username: this.resetPasswordForm.username,
          newPassword: this.resetPasswordForm.newPassword,
          role: this.resetPasswordForm.role
        });
        
        if (response.data.success) {
          this.$message.success('密码重置成功,请使用新密码登录');
          this.currentForm = 'login';
        } else {
          this.$message.error(response.data.message || '密码重置失败');
        }
      } catch (error) {
        console.error('Reset password error:', error);
        this.$message.error('密码重置失败: ' + (error.response?.data?.message || error.message));
      }
    }
  },
};
</script>



<template>
  <div class="login-container">
    <el-card class="login-card">
      <!-- 登录表单 -->
      <div v-if="currentForm === 'login'">
        <h2 class="login-title">教育管理系统 - 登录</h2>
        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="80px">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="loginForm.username" placeholder="请输入用户名" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" />
          </el-form-item>
          <el-form-item label="角色" prop="role">
            <el-select v-model="loginForm.role" placeholder="请选择角色">
              <el-option label="管理员" value="admin" />
              <el-option label="教师" value="teacher" />
              <el-option label="学生" value="student" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <div class="button-container">
              <el-button type="primary" @click="handleLogin">登录</el-button>
            </div>
          </el-form-item>
          <div class="form-footer">
            <span>还没有账号？<el-link type="primary" @click="currentForm = 'register'">立即注册</el-link></span>
            <el-link type="primary" @click="currentForm = 'reset'" class="forgot-password">忘记密码？</el-link>
          </div>
        </el-form>
      </div>

      <!-- 注册表单 -->
      <div v-if="currentForm === 'register'">
        <div class="form-header">
          <el-page-header @back="currentForm = 'login'" title="返回登录" />
          <h2 class="login-title">注册新账号</h2>
        </div>
        <el-form :model="registerForm" :rules="registerRules" ref="registerForm" label-width="100px">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="registerForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="registerForm.password"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input type="password" v-model="registerForm.confirmPassword"></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="role">
            <el-select v-model="registerForm.role" placeholder="请选择角色">
              <el-option label="学生" value="student"></el-option>
              <el-option label="教师" value="teacher"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="registerForm.email"></el-input>
          </el-form-item>
        </el-form>
        <div class="form-footer">
          <div class="button-container">
            <el-button type="primary" @click="handleRegister">注册</el-button>
          </div>
        </div>
      </div>

      <!-- 重置密码表单 -->
      <div v-if="currentForm === 'reset'">
        <div class="form-header">
          <el-page-header @back="currentForm = 'login'" title="返回登录" />
          <h2 class="login-title">重置密码</h2>
        </div>
        <el-form :model="resetPasswordForm" :rules="resetPasswordRules" ref="resetPasswordForm" label-width="100px">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="resetPasswordForm.username"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword">
            <el-input type="password" v-model="resetPasswordForm.newPassword"></el-input>
          </el-form-item>
          <el-form-item label="确认新密码" prop="confirmPassword">
            <el-input type="password" v-model="resetPasswordForm.confirmPassword"></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="role">
            <el-select v-model="resetPasswordForm.role" placeholder="请选择角色">
              <el-option label="学生" value="student"></el-option>
              <el-option label="教师" value="teacher"></el-option>
              <el-option label="管理员" value="admin"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div class="form-footer">
          <div class="button-container">
            <el-button type="primary" @click="handleResetPassword">重置密码</el-button>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>



  <style scoped>
  body, html {
    margin: 0;
    padding: 0;
    height: 100%;
    overflow: hidden;
  }

  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-image: linear-gradient(to right, pink, skyblue, lightseagreen, lightcoral);
    backdrop-filter: blur(100px);
    animation: gradientAnimation 100s ease infinite;
    background-size: 400% 400%;
  }

  @keyframes gradientAnimation {
    0% {
      background-position: 0% 50%;
    }
    50% {
      background-position: 100% 50%;
    }
    100% {
      background-position: 0% 50%;
    }
  }


  .login-card {
    width: 400px;
    padding: 30px;
    border-radius: 15px;
    box-shadow: 0 8px 20px rgba(0,0,0,0.1);
    background: rgba(255, 255, 255, 0.55);
  }

  .login-title {
    text-align: center;
    margin-bottom: 30px;
    font-size: 24px;
    color: #2c3e50;
    font-weight: 600;
  }

  .el-form-item {
    margin-bottom: 25px;
  }


  .stylish-button:hover {
    background-color: rgba( 55,  0,179,0.55); /* 悬停时的背景色 */
    transform: translateY(-2px); /* 悬停时的上移效果 */
  }

  .stylish-button:active {
    transform: translateY(1px); /* 点击时的下移效果 */
  }

  .el-input, .el-select {
    .el-input__inner {
      border-radius: 8px;
      height: 40px;
    }
  }

  .form-header {
    margin-bottom: 20px;
  }

  .form-footer {
    margin-top: 20px;
    text-align: center;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .forgot-password {
    font-size: 14px;
  }

  .button-container {
    display: flex;
    justify-content: center;
    width: 100%;
  }

  .el-button {
    width: 200px;  /* 设置按钮固定宽度 */
    padding: 15px 30px;
    font-size: 16px;
    color: white;
    background-color: rgba( 98,  0,234,0.65); /* 按钮背景色 */
    border: none;
    border-radius: 25px;
    box-shadow: 0 4px 15px rgba(98, 0, 234, 0.3);
    cursor: pointer;
    transition: background-color 0.3s, transform 0.2s;
  }
  </style>

