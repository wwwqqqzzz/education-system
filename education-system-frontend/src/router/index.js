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
      { path: '/admin/students', name: 'StudentManagement', component: StudentManagement },
      { path: '/admin/teachers', name: 'TeacherManagement', component: TeacherManagement },
      { path: '/admin/admins', name: 'AdminManagement', component: AdminManagement },
      { path: '/admin/courses', name: 'CourseManagement', component: CourseManagement }
    ]
  },
  {
    path: '/teacher/dashboard',
    name: 'TeacherDashboard',
    component: TeacherDashboard,
    children: [
      {
        path: '/teacher/profile',
        name: 'TeacherProfile',
        component: TeacherProfile
      },
      {
        path: '/teacher/courses',
        name: 'TeacherCourses',
        component: TeacherCourses
      },
      {
        path: '/teacher/grades',
        name: 'TeacherGrades',
        component: TeacherGrades
      },
      {
        path: '',
        redirect: '/teacher/profile'
      }
    ]
  },
  {
    path: '/student/dashboard',
    name: 'StudentDashboard',
    component: StudentDashboard,
    children: [
      {
        path: '/student/profile',
        name: 'StudentProfile',
        component: StudentProfile
      },
      {
        path: '/student/courses',
        name: 'StudentCourses',
        component: StudentCourses
      },
      {
        path: '/student/grades',
        name: 'StudentGrades',
        component: StudentGrades
      },
      {
        path: '',
        redirect: '/student/profile'
      }
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

export default router;
