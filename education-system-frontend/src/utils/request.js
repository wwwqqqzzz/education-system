import axios from 'axios'
import { Message } from 'element-ui'
import router from '@/router'

const request = axios.create({
    baseURL: 'http://localhost:9099',
    timeout: 5000,
    withCredentials: true
})

// 请求拦截器
request.interceptors.request.use(
    config => {
        // 添加必要的请求头
        config.headers['Content-Type'] = 'application/json'
        return config
    },
    error => {
        console.error('请求错误:', error)
        return Promise.reject(error)
    }
)

// 响应拦截器
request.interceptors.response.use(
    response => {
        // 添加响应数据的日志
        console.log('API Response:', response.config.url, response.data)
        return response
    },
    error => {
        console.error('响应错误:', error)
        if (error.response) {
            switch (error.response.status) {
                case 401:
                    Message.error('未登录或登录已过期')
                    router.push('/login')
                    break
                case 403:
                    Message.error('没有权限访问')
                    break
                case 404:
                    Message.error('请求的资源不存在')
                    break
                case 500:
                    Message.error('服务器错误')
                    break
                default:
                    Message.error(error.response.data.message || '未知错误')
            }
        } else {
            Message.error('网络错误，请检查您的网络连接')
        }
        return Promise.reject(error)
    }
)

export default request 