import Vue from 'vue';
import VueRouter from 'vue-router';
import Login from '@/views/Login.vue';
import AdminDashboard from '@/views/admin/Dashboard.vue';
import TeacherDashboard from '@/views/teacher/Dashboard.vue';
import TeacherProfile from '@/views/teacher/TeacherProfile.vue';
import StudentDashboard from '@/views/student/Dashboard.vue';
import StudentManagement from '@/views/admin/StudentManagement.vue';
import TeacherManagement from '@/views/admin/TeacherManagement.vue';
import AdminManagement from '@/views/admin/AdminManagement.vue';
import CourseManagement from '@/views/admin/CourseManagement.vue';
import TeacherGrades from '@/views/teacher/TeacherGrades.vue';
import TeacherCourses from '@/views/teacher/TeacherCourses.vue';
import StudentProfile from '@/views/student/StudentProfile.vue';
import StudentCourses from '@/views/student/StudentCourses.vue';
import StudentGrades from '@/views/student/StudentGrades.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/admin/dashboard',
    name: 'AdminDashboard',
    component: AdminDashboard,
    children: [
      { 
        path: '', // 默认子路由
        name: 'AdminHome',
        component: () => import('@/views/admin/AdminHome.vue')
      },
      { 
        path: '/admin/students', 
        name: 'StudentManagement', 
        component: StudentManagement,
        meta: { title: '学生管理' }
      },
      { 
        path: '/admin/teachers', 
        name: 'TeacherManagement', 
        component: TeacherManagement,
        meta: { title: '教师管理' }
      },
      { 
        path: '/admin/admins', 
        name: 'AdminManagement', 
        component: AdminManagement,
        meta: { title: '管理员管理' }
      },
      { 
        path: '/admin/courses', 
        name: 'CourseManagement', 
        component: CourseManagement,
        meta: { title: '课程管理' }
      }
    ]
  },
  {
    path: '/teacher/dashboard',
    name: 'TeacherDashboard',
    component: TeacherDashboard,
    children: [
      { 
        path: '', // 默认子路由
        name: 'TeacherHome',
        component: () => import('@/views/teacher/TeacherHome.vue')
      },
      { path: '/teacher/profile', name: 'TeacherProfile', component: TeacherProfile },
      { path: '/teacher/courses', name: 'TeacherCourses', component: TeacherCourses },
      { path: '/teacher/grades', name: 'TeacherGrades', component: TeacherGrades }
    ]
  },
  {
    path: '/student/dashboard',
    name: 'StudentDashboard',
    component: StudentDashboard,
    children: [
      { 
        path: '', // 默认子路由
        name: 'StudentHome',
        component: () => import('@/views/student/StudentHome.vue')
      },
      { path: '/student/profile', name: 'StudentProfile', component: StudentProfile },
      { path: '/student/courses', name: 'StudentCourses', component: StudentCourses },
      { path: '/student/grades', name: 'StudentGrades', component: StudentGrades }
    ]
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

// 路由守卫
router.beforeEach((to, from, next) => {
  const role = localStorage.getItem('role');
  if (to.path === '/login') {
    next();
  } else if (!role) {
    next('/login');
  } else if (to.path.startsWith('/admin') && role !== 'admin') {
    next('/login');
  } else if (to.path.startsWith('/teacher') && role !== 'teacher') {
    next('/login');
  } else if (to.path.startsWith('/student') && role !== 'student') {
    next('/login');
  } else {
    next();
  }
});

// 添加路由导航错误处理
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => {
    if (err.name !== 'NavigationDuplicated') throw err
  })
}

export default router;
