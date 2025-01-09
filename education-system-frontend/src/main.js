import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import store from './store'

// 先引入 element-ui 的样式
import './styles/element-ui.css'
// 再引入表格样式，确保能覆盖默认样式
import './styles/table.css'
import './styles/dark-theme.css'  // 添加深色主题样式

Vue.config.productionTip = false

Vue.use(ElementUI, {
  size: 'medium'
})

// 配置 axios
axios.defaults.baseURL = 'http://localhost:9099'
axios.defaults.withCredentials = true // 允许跨域携带 cookie
Vue.prototype.$axios = axios

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
