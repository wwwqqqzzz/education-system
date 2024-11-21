import axios from 'axios';

// 创建 Axios 实例
const instance = axios.create({
  baseURL: 'http://localhost:9099', // 后端接口基础地址
  timeout: 5000, // 请求超时时间
});

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token'); // 从本地存储获取 token
    if (token) {
      config.headers.Authorization = `Bearer ${token}`; // 添加 token 到请求头
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器
instance.interceptors.response.use(
  (response) => {
    return response.data; // 直接返回响应数据
  },
  (error) => {
    if (error.response) {
      // 针对特定的状态码进行处理
      if (error.response.status === 401) {
        alert('登录已过期，请重新登录！');
        window.location.href = '/login'; // 跳转到登录页面
      }
      return Promise.reject(error.response.data);
    }
    return Promise.reject({ message: '网络错误，请稍后再试' });
  }
);

export default instance;
