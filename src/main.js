import axios from 'axios'
import router from './router'

axios.defaults.baseURL = 'http://localhost:8080'
axios.defaults.timeout = 5000
axios.defaults.headers.common['Content-Type'] = 'application/json'

// 添加请求拦截器
axios.interceptors.request.use(config => {
  // 不拦截登录请求
  if (config.url === '/auth/login') {
    return config;
  }
  
  const token = localStorage.getItem('token')
  if (token) {
    config.headers['Authorization'] = 'Bearer ' + token
  }
  return config
}, error => {
  return Promise.reject(error)
})

// 添加响应拦截器
axios.interceptors.response.use(response => {
  return response
}, error => {
  if (error.response) {
    switch (error.response.status) {
      case 401:
        // token 过期或无效，跳转到登录页
        localStorage.clear()
        router.push('/login')
        break
      case 403:
        console.error('没有权限访问该资源')
        break
      case 404:
        console.error('请求的资源不存在')
        break
      default:
        console.error('服务器错误:', error.response.data)
    }
  }
  return Promise.reject(error)
}) 