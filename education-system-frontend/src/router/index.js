import Vue from 'vue';
import Router from 'vue-router';
import Login from '../views/Login.vue';
import AdminDashboard from '../views/Admin/AdminDashboard.vue';
import StudentManagement from '../views/Admin/StudentManagement.vue';
import TeacherManagement from '../views/Admin/TeacherManagement.vue';
import CourseManagement from '../views/Admin/CourseManagement.vue';
import AdminManagement from '../views/Admin/AdminManagement.vue';
import TeacherDashboard from '../views/Teacher/TeacherDashboard.vue';
import ManageGrades from '../views/Teacher/ManageGrades.vue';
import TeacherProfile from '../views/Teacher/TeacherProfile.vue';
import StudentDashboard from '../views/Student/StudentDashboard.vue';
import CourseSelection from '../views/Student/CourseSelection.vue';
import StudentProfile from '../views/Student/StudentProfile.vue';

Vue.use(Router);

export default new Router({
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', name: 'Login', component: Login },
    {
      path: '/admin',
      component: AdminDashboard,
      children: [
        { path: 'students', name: 'StudentManagement', component: StudentManagement },
        { path: 'teachers', name: 'TeacherManagement', component: TeacherManagement },
        { path: 'courses', name: 'CourseManagement', component: CourseManagement },
        { path: 'admins', name: 'AdminManagement', component: AdminManagement },
      ],
    },
    {
      path: '/teacher',
      component: TeacherDashboard,
      children: [
        { path: 'grades', name: 'ManageGrades', component: ManageGrades },
        { path: 'profile', name: 'TeacherProfile', component: TeacherProfile },
      ],
    },
    {
      path: '/student',
      component: StudentDashboard,
      children: [
        { path: 'courses', name: 'CourseSelection', component: CourseSelection },
        { path: 'profile', name: 'StudentProfile', component: StudentProfile },
      ],
    },
  ],
});
