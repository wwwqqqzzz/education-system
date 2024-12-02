<script>
import axios from 'axios';

export default {
  name: "Login",
  data() {
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
    };
  },
  methods: {
    async handleLogin() {
      this.$refs.loginForm.validate(async (valid) => {
        if (valid) {
          try {
            const response = await axios.post('/auth/login', {
              username: this.loginForm.username,
              password: this.loginForm.password,
              role: this.loginForm.role
            });

            if (response.data.message === "Login successful!") {
              this.$message.success("登录成功！");
              localStorage.setItem("role", response.data.role);
              localStorage.setItem("username", this.loginForm.username);
              
              // 根据角色跳转到不同的页面
              switch(response.data.role) {
                case "admin":
                  this.$router.push("/admin/dashboard");
                  break;
                case "teacher":
                  this.$router.push("/teacher/dashboard");
                  break;
                case "student":
                  this.$router.push("/student/dashboard");
                  break;
              }
            } else {
              this.$message.error("用户名或密码错误！");
            }
          } catch (error) {
            console.error('Login error:', error);
            this.$message.error("登录失败，请稍后再试！");
          }
        }
      });
    }
  },
};
</script>



<template>
    <div class="login-container">
      <el-card class="login-card">
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
            <el-button type="primary" @click="handleLogin">登录</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </template>
  

  
  <style scoped>
  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f5f5f5;
  }
  .login-card {
    width: 400px;
    padding: 20px;
  }
  .login-title {
    text-align: center;
    margin-bottom: 20px;
    font-weight: bold;
  }
  </style>
  

