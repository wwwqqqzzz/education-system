import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import router from './router'
import axios from 'axios'
import store from './store'

Vue.config.productionTip = false

// 使用 ElementUI
Vue.use(ElementUI, { size: 'medium' })

// 配置 axios
axios.defaults.baseURL = 'http://localhost:9099'
axios.defaults.withCredentials = true // 允许跨域携带 cookie
Vue.prototype.$axios = axios

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
