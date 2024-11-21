<template>
  <div class="login-container">
    <h1>教育系统登录</h1>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="username">用户名</label>
        <input 
          type="text" 
          v-model="username" 
          id="username" 
          placeholder="请输入用户名" 
          @input="clearErrorMessage"
        />
      </div>
      <div class="form-group">
        <label for="password">密码</label>
        <input 
          type="password" 
          v-model="password" 
          id="password" 
          placeholder="请输入密码" 
          @input="clearErrorMessage"
        />
      </div>
      <div class="form-group">
        <label for="userType">用户类型</label>
        <select v-model="userType" id="userType">
          <option value="admin">管理员</option>
          <option value="teacher">教师</option>
          <option value="student">学生</option>
        </select>
      </div>
      <button type="submit">登录</button>
    </form>
    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
  </div>
</template>

<script>
import axios from '@/api/axios';

export default {
  data() {
    return {
      username: '',
      password: '',
      userType: 'admin', // 默认用户类型
      errorMessage: '', // 错误信息
    };
  },
  methods: {
    async handleLogin() {
      if (!this.username || !this.password) {
        this.errorMessage = '用户名或密码不能为空！';
        return;
      }

      try {
        // 动态设置登录接口
        const url = `/${this.userType}/login`;

        // 发送登录请求
        const response = await axios.post(url, {
          username: this.username,
          password: this.password,
        });

        // 登录成功后处理
        alert(response.message || '登录成功');
        localStorage.setItem('token', response.token || ''); // 保存 token
        this.$router.push(`/${this.userType}`); // 跳转到对应用户类型的主页
      } catch (error) {
        // 处理错误信息
        this.errorMessage = error.message || '登录失败，请检查用户名和密码！';
        console.error('登录错误:', error);
      }
    },
    clearErrorMessage() {
      this.errorMessage = '';
    },
  },
};
</script>

<style>
/* 样式保持不变 */
</style>
