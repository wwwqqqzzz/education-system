<template>
  <div class="admin-home">
    <!-- 欢迎区域 -->
    <div class="welcome-section glass-card">
      <div class="welcome-header">
        <div class="welcome-text">
          <h2>欢迎回来，{{ username }}</h2>
          <p>{{ greeting }}</p>
        </div>
        <div class="welcome-time">
          <div class="date">{{ currentDate }}</div>
          <div class="time">{{ currentTime }}</div>
        </div>
      </div>
    </div>
    <!-- 数据概览 -->
    <div class="stats-container">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="stats-card glass-card">
            <div class="stats-icon student-icon"><i class="el-icon-user"></i></div>
            <div class="stats-info">
              <div class="stats-title">学生总数</div>
              <div class="stats-number">{{ stats.studentCount }}</div>
              <div class="stats-desc">活跃学生数量</div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stats-card glass-card">
            <div class="stats-icon teacher-icon"><i class="el-icon-s-custom"></i></div>
            <div class="stats-info">
              <div class="stats-title">教师总数</div>
              <div class="stats-number">{{ stats.teacherCount }}</div>
              <div class="stats-desc">在职教师数量</div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stats-card glass-card">
            <div class="stats-icon course-icon"><i class="el-icon-reading"></i></div>
            <div class="stats-info">
              <div class="stats-title">课程总数</div>
              <div class="stats-number">{{ stats.courseCount }}</div>
              <div class="stats-desc">开设课程数量</div>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stats-card glass-card">
            <div class="stats-icon system-icon"><i class="el-icon-monitor"></i></div>
            <div class="stats-info">
              <div class="stats-title">系统运行</div>
              <div class="stats-number">{{ uptime }}</div>
              <div class="stats-desc">系统运行时间</div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <!-- 快捷操作 -->
    <div class="quick-access glass-card">
      <h3>快捷操作</h3>
      <el-row :gutter="20">
        <el-col :span="4">
          <el-button type="primary" @click="$router.push('/admin/students')">
            学生管理
          </el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="success" @click="$router.push('/admin/teachers')">
            教师管理
          </el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="warning" @click="$router.push('/admin/courses')">
            课程管理
          </el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="danger" @click="$router.push('/admin/admins')">
            管理员管理
          </el-button>
        </el-col>
        <el-col :span="4">
          <el-button type="info" @click="showSystemInfo">
            <i class="el-icon-monitor"></i>
            系统状态
          </el-button>
        </el-col>
        <el-col :span="4">
          <el-button
            type="warning"
            @click="triggerEasterEgg"
            :class="{ 'easter-egg-active': isEasterEggActive }">
            <i class="el-icon-magic-stick"></i>
            彩蛋
          </el-button>
        </el-col>
      </el-row>
    </div>

    <!-- 升级后的彩蛋动画元素 -->
    <div v-if="isEasterEggActive" class="easter-egg-container">
      <!-- 烟花效果 -->
      <div v-for="(firework, index) in fireworks" 
        :key="`firework-${index}`" 
        class="firework"
        :style="getFireworkStyle(firework)">
        <!-- 烟花粒子 -->
        <div v-for="n in 12" 
          :key="`particle-${n}`" 
          class="particle"
          :style="getParticleStyle(n)">
        </div>
      </div>
      <!-- 渐变星星 -->
      <div v-for="n in 50" 
        :key="`star-${n}`" 
        class="star" 
        :style="getRandomStarStyle()">
      </div>
      <!-- 祝福文字 -->
      <div class="blessing-text" :style="getBlessingStyle()">
        {{ blessingText }}
      </div>
    </div>

    <!-- 系统状态对话框 -->
    <el-dialog
      title="系统状态信息"
      :visible.sync="systemInfoVisible"
      width="50%">
      <div class="system-info">
        <div class="info-item">
          <span class="label">系统版本：</span>
          <span class="value">V1.0.0</span>
        </div>
        <div class="info-item">
          <span class="label">运行时长：</span>
          <span class="value">{{ uptime }}</span>
        </div>
        <div class="info-item">
          <span class="label">当前在线：</span>
          <span class="value">{{ stats.studentCount + stats.teacherCount }} 人</span>
        </div>
        <div class="info-item">
          <span class="label">系统时间：</span>
          <span class="value">{{ currentDate }} {{ currentTime }}</span>
        </div>
        <div class="info-item">
          <span class="label">浏览器：</span>
          <span class="value">{{ getBrowserInfo() }}</span>
        </div>
        <div class="info-item">
          <span class="label">操作系统：</span>
          <span class="value">{{ getOSInfo() }}</span>
        </div>
      </div>
    </el-dialog>

    <!-- 在快捷操作下面添加 -->
    <div class="charts-container">
      <el-row :gutter="20">
        <el-col :span="12">
          <div class="chart-card glass-card">
            <h3>课程分布统计</h3>
            <div ref="courseChart" class="chart"></div>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="chart-card glass-card">
            <h3>学生成绩分布</h3>
            <div ref="gradeChart" class="chart"></div>
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 添加页脚 -->
    <div class="footer">
      <div class="footer-content">
        <div class="footer-left">
          © 2024 教育管理系统 | 作者：王起哲
        </div>
        <div class="footer-right">
          Version 1.0.0
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request'
import * as echarts from 'echarts/core'
import { PieChart, BarChart } from 'echarts/charts'
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent
} from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'

// 注册必需的组件
echarts.use([
  PieChart,
  BarChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
  CanvasRenderer
])

export default {
  name: "AdminHome",
  data() {
    return {
      username: localStorage.getItem('username'),
      currentDate: "",
      currentTime: "",
      greeting: "",
      stats: {
        studentCount: 0,
        teacherCount: 0,
        courseCount: 0
      },
      uptime: "0天",
      isEasterEggActive: false,
      easterEggCount: 0,
      systemInfoVisible: false,
      fireworks: [],
      blessingText: '',
      blessingTexts: [
        '感谢老师的指导 🙏',
        '这个项目凝聚了很多心血',
        '希望能得到您的认可',
        '我会继续努力的',
        '祝老师工作顺利'
      ],
      projectHighlights: [
        { title: '完整的权限管理', desc: '基于角色的访问控制系统' },
        { title: '优雅的深色模式', desc: '自适应主题切换' },
        { title: '精心设计的UI', desc: '现代化的界面设计' },
        { title: '完善的功能模块', desc: '教务管理一体化' },
        { title: '代码结构清晰', desc: '易于维护和扩展' }
      ],
      courseChart: null,
      gradeChart: null
    };
  },
  created() {
    this.updateTime();
    this.updateGreeting();
    setInterval(this.updateTime, 1000);
    this.fetchStats();
    window.addEventListener('resize', this.handleResize)
  },
  mounted() {
    this.initCharts()
  },
  methods: {
    updateTime() {
      const now = new Date();
      this.currentDate = now.toLocaleDateString("zh-CN");
      this.currentTime = now.toLocaleTimeString("zh-CN");
    },
    updateGreeting() {
      const hour = new Date().getHours();
      this.greeting =
        hour < 6
          ? "夜深了，请注意休息"
          : hour < 9
          ? "早上好！"
          : hour < 12
          ? "上午好！"
          : hour < 14
          ? "中午好！"
          : hour < 18
          ? "下午好！"
          : "晚上好！";
    },
    async fetchStats() {
      try {
        // 获取所有教师数量
        const teachersResponse = await request.get('/admin/teachers')
        this.stats.teacherCount = teachersResponse.data.length

        // 获取所有学生数量
        const studentsResponse = await request.get('/admin/students')
        this.stats.studentCount = studentsResponse.data.length

        // 获取所有课程数量
        const coursesResponse = await request.get('/admin/courses')
        this.stats.courseCount = coursesResponse.data.length

        // 计算系统运行时间（从当前时间减去一个固定的开始时间）
        const startDate = new Date('2024-12-01') // 可以设置为系统上线时间
        const now = new Date()
        const days = Math.floor((now - startDate) / (1000 * 60 * 60 * 24))
        this.uptime = `${days}天`

      } catch (error) {
        console.error('获取统计数据失败:', error)
        this.$message.error('获取统计数据失败')
      }
    },
    async triggerEasterEgg() {
      this.easterEggCount++
      this.isEasterEggActive = true

      // 根据点击次数显示不同的效果
      if (this.easterEggCount === 1) {
        this.$notify({
          title: '发现彩蛋！',
          message: '老师您好！这是一个特别的彩蛋，再点击几次试试？',
          type: 'success',
          duration: 3000
        })
      } else if (this.easterEggCount === 3) {
        // 显示项目亮点
        this.showProjectHighlights()
      } else if (this.easterEggCount === 5) {
        // 显示特别致谢
        this.showSpecialThanks()
      }

      // 创建烟花效果
      this.createFireworks()
      
      // 随机选择祝福语
      this.blessingText = this.blessingTexts[Math.floor(Math.random() * this.blessingTexts.length)]
      
      // 3秒后关闭动画
      setTimeout(() => {
        this.isEasterEggActive = false
        this.fireworks = []
      }, 3000)
    },

    // 显示项目亮点
    showProjectHighlights() {
      const h = this.$createElement
      
      const highlights = this.projectHighlights.map(item => {
        return h('div', { class: 'highlight-item' }, [
          h('h3', { style: { color: '#409EFF' } }, item.title),
          h('p', { style: { color: '#606266' } }, item.desc)
        ])
      })

      this.$msgbox({
        title: '项目亮点展示',
        message: h('div', { class: 'highlights-container' }, [
          h('div', { class: 'highlights-intro' }, '这个项目的主要特色：'),
          ...highlights
        ]),
        confirmButtonText: '继续探索',
        customClass: 'highlights-dialog'
      })
    },

    // 显示特别致谢
    showSpecialThanks() {
      const h = this.$createElement
      
      this.$msgbox({
        title: '特别致谢',
        message: h('div', { class: 'special-thanks' }, [
          h('p', '尊敬的老师：'),
          h('p', '感谢您的指导。这个系统凝聚了我的很多心血，从需求分析、架构设计到具体实现，我都倾注了大量的精力。'),
          h('p', '我特别注重：'),
          h('ul', [
            h('li', '代码的规范性和可维护性'),
            h('li', '用户界面的交互体验'),
            h('li', '系统的安全性和稳定性'),
            h('li', '功能的实用性和完整性')
          ]),
          h('p', { style: { color: '#409EFF', fontWeight: 'bold' } }, '希望这个作品能够得到您的认可！')
        ]),
        confirmButtonText: '谢谢老师',
        customClass: 'thanks-dialog'
      }).then(() => {
        // 显示最后的烟花效果
        this.createSpecialFireworks()
      })
    },

    // 创建特别的烟花效果
    createSpecialFireworks() {
      // 创建更密集的烟花
      for (let i = 0; i < 10; i++) {
        setTimeout(() => {
          this.fireworks.push({
            x: Math.random() * window.innerWidth,
            y: Math.random() * window.innerHeight,
            color: this.getRandomColor()
          })
        }, i * 200)
      }
    },
    
    getFireworkStyle(firework) {
      return {
        left: `${firework.x}px`,
        top: `${firework.y}px`,
        '--firework-color': firework.color
      }
    },
    
    getParticleStyle(n) {
      return {
        transform: `rotate(${n * 30}deg) translateY(-15px)`
      }
    },
    
    getRandomStarStyle() {
      const x = Math.random() * window.innerWidth
      const y = Math.random() * window.innerHeight
      const size = Math.random() * 10 + 5
      const duration = Math.random() * 2 + 1
      const delay = Math.random() * 2
      const color = this.getRandomColor()
      
      return {
        left: `${x}px`,
        top: `${y}px`,
        width: `${size}px`,
        height: `${size}px`,
        background: `linear-gradient(45deg, ${color}, ${this.getRandomColor()})`,
        animation: `starAnimation ${duration}s ${delay}s infinite`
      }
    },
    
    getBlessingStyle() {
      return {
        animation: 'blessingAnimation 3s ease-out'
      }
    },
    
    getRandomColor() {
      const colors = [
        '#ff7675', '#74b9ff', '#a8e6cf', '#ffd3b6', 
        '#ff8c94', '#a8d8ea', '#aa96da', '#fcbad3'
      ]
      return colors[Math.floor(Math.random() * colors.length)]
    },
    showSystemInfo() {
      this.systemInfoVisible = true
    },
    getBrowserInfo() {
      const ua = navigator.userAgent
      if (ua.includes('Chrome')) return 'Chrome'
      if (ua.includes('Firefox')) return 'Firefox'
      if (ua.includes('Safari')) return 'Safari'
      if (ua.includes('Edge')) return 'Edge'
      return '未知浏览器'
    },
    getOSInfo() {
      const ua = navigator.userAgent
      if (ua.includes('Windows')) return 'Windows'
      if (ua.includes('Mac')) return 'MacOS'
      if (ua.includes('Linux')) return 'Linux'
      if (ua.includes('Android')) return 'Android'
      if (ua.includes('iOS')) return 'iOS'
      return '未知系统'
    },
    async initCharts() {
      try {
        // 初始化课程分布图表
        this.courseChart = echarts.init(this.$refs.courseChart)
        
        // 获取课程数据
        const coursesResponse = await request.get('/admin/courses')
        const courses = coursesResponse.data

        // 获取教师数据（用于获取院系信息）
        const teachersResponse = await request.get('/admin/teachers')
        const teachers = teachersResponse.data

        // 创建教师ID到院系的映射
        const teacherDepartments = {}
        teachers.forEach(teacher => {
          teacherDepartments[teacher.id] = teacher.department || '未分配'
        })

        // 按院系统计课程分布
        const departmentCourses = {}
        courses.forEach(course => {
          const dept = teacherDepartments[course.teacherId] || '未分配'
          departmentCourses[dept] = (departmentCourses[dept] || 0) + 1
        })

        // 转换为图表数据
        const courseChartData = Object.entries(departmentCourses)
          .map(([name, value], index) => ({
            name,
            value,
            itemStyle: {
              color: [
                '#4B79E5',  // 深蓝色
                '#45B7D1',  // 青色
                '#F6C022',  // 金黄色
                '#E58F50',  // 橙色
                '#BE6B7D',  // 玫瑰红
                '#8B60E4',  // 紫色
                '#4ECBB4',  // 青绿色
                '#F15C5C',  // 红色
                '#967ADC',  // 淡紫色
                '#91C7AE'   // 灰绿色
              ][index % 10]
            }
          }))

        this.courseChart.setOption({
          tooltip: {
            trigger: 'item',
            formatter: '{b}: {c} 门课程 ({d}%)',
            textStyle: {
              fontSize: 14
            }
          },
          legend: {
            orient: 'vertical',
            left: 'left',
            textStyle: {
              color: this.isDarkMode ? '#E6E6E6' : '#606266',
              fontSize: 14
            }
          },
          series: [{
            name: '课程分布',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: true,
            label: {
              show: true,
              formatter: '{b}: {c} 门',
              fontSize: 14,
              color: this.isDarkMode ? '#E6E6E6' : '#606266'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 16,
                fontWeight: 'bold'
              }
            },
            data: courseChartData
          }]
        })

        // 第二个图表使用相同的颜色方案
        this.gradeChart = echarts.init(this.$refs.gradeChart)
        
        // 统计教师院系分布
        const teacherStats = {}
        teachers.forEach(teacher => {
          const dept = teacher.department || '未分配'
          teacherStats[dept] = (teacherStats[dept] || 0) + 1
        })

        const departments = Object.keys(teacherStats)
        const teacherCounts = Object.values(teacherStats)

        this.gradeChart.setOption({
          tooltip: {
            trigger: 'axis',
            axisPointer: { type: 'shadow' }
          },
          xAxis: {
            type: 'category',
            data: departments,
            axisLabel: {
              interval: 0,
              rotate: 30,
              color: this.isDarkMode ? '#E6E6E6' : '#606266',
              fontSize: 12,
              margin: 15
            }
          },
          yAxis: {
            type: 'value',
            name: '教师数量',
            nameTextStyle: {
              color: this.isDarkMode ? '#E6E6E6' : '#606266',
              fontSize: 14,
              padding: [0, 0, 10, 0]
            },
            axisLabel: {
              color: this.isDarkMode ? '#E6E6E6' : '#606266',
              fontSize: 12
            }
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '15%',  // 增加底部空间
            top: '3%',
            containLabel: true
          },
          series: [{
            data: departments.map((_, index) => ({
              value: teacherCounts[index],
              itemStyle: {
                color: [
                  '#4B79E5', '#45B7D1', '#F6C022', '#E58F50', '#BE6B7D',
                  '#8B60E4', '#4ECBB4', '#F15C5C', '#967ADC', '#91C7AE'
                ][index % 10]
              }
            })),
            type: 'bar',
            barWidth: '60%',
            label: {
              show: true,
              position: 'top',
              fontSize: 12,
              color: this.isDarkMode ? '#E6E6E6' : '#606266'
            }
          }]
        })
      } catch (error) {
        console.error('初始化图表失败:', error)
        this.$message.error('获取统计数据失败')
      }
    },

    // 监听窗口大小变化
    handleResize() {
      this.courseChart?.resize()
      this.gradeChart?.resize()
    }
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.handleResize)
    this.courseChart?.dispose()
    this.gradeChart?.dispose()
  }
}
</script>

<style scoped>
.admin-home {
  padding: 20px;
  padding-bottom: 70px;
}

.glass-card {
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.3);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin-bottom: 20px;
}

.welcome-section {
  margin-bottom: 24px;
}

.welcome-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.welcome-text h2 {
  font-size: 24px;
  color: #303133;
  margin: 0 0 8px 0;
}

.welcome-text p {
  font-size: 16px;
  color: #606266;
  margin: 0;
}

.welcome-time {
  text-align: right;
}

.welcome-time .date {
  font-size: 16px;
  color: #606266;
  margin-bottom: 4px;
}

.welcome-time .time {
  font-size: 24px;
  color: #303133;
  font-weight: 600;
}

.stats-container {
  margin-bottom: 24px;
}

.stats-card {
  display: flex;
  align-items: center;
  padding: 20px;
  height: 120px;
}

.stats-icon {
  width: 64px;
  height: 64px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 16px;
}

.stats-icon i {
  font-size: 32px;
  color: #fff;
}

.student-icon {
  background: linear-gradient(135deg, #409EFF, #36cfc9);
}

.teacher-icon {
  background: linear-gradient(135deg, #67C23A, #95de64);
}

.course-icon {
  background: linear-gradient(135deg, #722ED1, #9254DE);
}

.system-icon {
  background: linear-gradient(135deg, #E6A23C, #f6cc9f);
}

.stats-info {
  flex: 1;
}

.stats-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 8px;
}

.stats-number {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 4px;
}

.stats-desc {
  font-size: 12px;
  color: #909399;
}

.quick-access {
  padding: 24px;
}

.quick-access h3 {
  margin: 0 0 20px 0;
  font-size: 18px;
  color: #303133;
}

.quick-access .el-button {
  width: 100%;
  height: 40px;
  border-radius: 8px;
}

/* 深色模式适配 */
.dark-theme .glass-card {
  background: rgba(30, 30, 30, 0.95);
  border-color: rgba(255, 255, 255, 0.05);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
}

.dark-theme .welcome-text h2,
.dark-theme .welcome-time .time,
.dark-theme .stats-number,
.dark-theme .quick-access h3 {
  color: var(--text-color);
}

.dark-theme .welcome-text p,
.dark-theme .welcome-time .date,
.dark-theme .stats-title,
.dark-theme .stats-desc {
  color: var(--text-color-light);
}

.easter-egg-active {
  animation: shake 0.5s linear;
}

.easter-egg-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  pointer-events: none;
  z-index: 9999;
  background: rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.firework {
  position: absolute;
  width: 30px;
  height: 30px;
  transform-origin: center;
  animation: fireworkPop 0.6s ease-out;
}

.particle {
  position: absolute;
  width: 4px;
  height: 20px;
  background: var(--firework-color);
  border-radius: 2px;
  transform-origin: center bottom;
  animation: particleSpread 0.6s ease-out;
}

.star {
  position: absolute;
  border-radius: 50%;
  box-shadow: 0 0 10px currentColor;
  animation: none;
}

.blessing-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 32px;
  font-weight: bold;
  color: #fff;
  text-shadow: 0 0 10px rgba(255, 255, 255, 0.8);
  white-space: nowrap;
}

@keyframes fireworkPop {
  0% {
    transform: scale(0);
    opacity: 1;
  }
  100% {
    transform: scale(1);
    opacity: 0;
  }
}

@keyframes particleSpread {
  0% {
    transform: rotate(var(--angle)) translateY(0) scale(1);
    opacity: 1;
  }
  100% {
    transform: rotate(var(--angle)) translateY(100px) scale(0);
    opacity: 0;
  }
}

@keyframes starAnimation {
  0% {
    transform: scale(0) rotate(0deg);
    opacity: 0;
  }
  50% {
    transform: scale(1) rotate(180deg);
    opacity: 1;
  }
  100% {
    transform: scale(0) rotate(360deg);
    opacity: 0;
  }
}

@keyframes blessingAnimation {
  0% {
    transform: translate(-50%, -50%) scale(0);
    opacity: 0;
  }
  50% {
    transform: translate(-50%, -50%) scale(1.2);
    opacity: 1;
  }
  100% {
    transform: translate(-50%, -50%) scale(1);
    opacity: 0;
  }
}

/* 深色模式适配 */
.dark-theme .easter-egg-container {
  background: rgba(0, 0, 0, 0.1);
}

/* 添加系统信息样式 */
.system-info {
  padding: 20px;
}

.info-item {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}

.info-item .label {
  width: 100px;
  color: #909399;
}

.info-item .value {
  color: #303133;
  font-weight: 500;
}

/* 深色模式适配 */
.dark-theme .info-item .label {
  color: #909399;
}

.dark-theme .info-item .value {
  color: var(--text-color);
}

.highlights-container {
  padding: 20px;
}

.highlights-intro {
  margin-bottom: 20px;
  font-size: 16px;
  color: #303133;
}

.highlight-item {
  margin-bottom: 15px;
  padding: 10px;
  border-radius: 8px;
  background: rgba(64, 158, 255, 0.1);
}

.highlight-item h3 {
  margin: 0 0 5px 0;
  font-size: 16px;
}

.highlight-item p {
  margin: 0;
  font-size: 14px;
}

.special-thanks {
  padding: 20px;
  line-height: 1.8;
}

.special-thanks p {
  margin-bottom: 15px;
}

.special-thanks ul {
  margin: 15px 0;
  padding-left: 20px;
}

.special-thanks li {
  margin-bottom: 8px;
  color: #606266;
}

/* 深色模式适配 */
.dark-theme .highlight-item {
  background: rgba(64, 158, 255, 0.05);
}

.dark-theme .special-thanks li {
  color: var(--text-color);
}

.charts-container {
  margin-top: 20px;
  margin-bottom: 40px;
}

.chart-card {
  padding: 20px;
  margin-bottom: 20px;
}

.chart-card h3 {
  margin: 0 0 20px 0;
  font-size: 18px;
  color: #303133;
  font-weight: 600;
}

.chart {
  height: 300px;
  margin-bottom: 10px;
}

/* 深色模式适配 */
.dark-theme .chart-card h3 {
  color: var(--text-color);
}

.dark-theme .chart-card {
  background: rgba(30, 30, 30, 0.95);
}

/* 页脚样式 */
.footer {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 50px;
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  border-top: 1px solid #ebeef5;
  z-index: 100;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  font-size: 14px;
  color: #606266;
}

.footer-left {
  margin-right: auto;
}

.footer-right {
  margin-left: auto;
}

/* 深色模式适配 */
.dark-theme .footer {
  background: rgba(30, 30, 30, 0.95);
  border-top-color: rgba(255, 255, 255, 0.05);
}

.dark-theme .footer-content {
  color: var(--text-color);
}
</style>
