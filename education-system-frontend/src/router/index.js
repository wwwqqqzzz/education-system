import Vue from 'vue';
import Router from 'vue-router';
import Login from '@/views/Login.vue';
import AdminDashboard from '@/views/admin/Dashboard.vue';
import TeacherDashboard from '@/views/teacher/Dashboard.vue';
import StudentDashboard from '@/views/student/Dashboard.vue';
import StudentManagement from '@/views/admin/StudentManagement.vue';
import TeacherManagement from '@/views/admin/TeacherManagement.vue';
import AdminManagement from '@/views/admin/AdminManagement.vue';
import CourseManagement from '@/views/admin/CourseManagement.vue';

Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', name: 'Login', component: Login },
    { 
      path: '/admin/dashboard', 
      component: AdminDashboard,
      children: [
        { path: '/admin/students', name: 'StudentManagement', component: StudentManagement },
        { path: '/admin/teachers', name: 'TeacherManagement', component: TeacherManagement },
        { path: '/admin/admins', name: 'AdminManagement', component: AdminManagement },
        { path: '/admin/courses', name: 'CourseManagement', component: CourseManagement }
      ]
    },
    { path: '/teacher/dashboard', name: 'TeacherDashboard', component: TeacherDashboard },
    { path: '/student/dashboard', name: 'StudentDashboard', component: StudentDashboard },
  ],
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

export default router;
