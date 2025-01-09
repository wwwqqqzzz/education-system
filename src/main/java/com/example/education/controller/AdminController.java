/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18
 */
package com.example.education.controller;

import com.example.education.entity.Admin;
import com.example.education.entity.Course;
import com.example.education.entity.Student;
import com.example.education.entity.Teacher;
import com.example.education.enums.Role;
import com.example.education.service.AdminService;
import com.example.education.service.CourseService;
import com.example.education.service.StudentService;
import com.example.education.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class AdminController {
    private final AdminService adminService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final CourseService courseService;

    public AdminController(AdminService adminService,
                         TeacherService teacherService,
                         StudentService studentService,
                         CourseService courseService) {
        this.adminService = adminService;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    // 管理员登录
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginInfo, HttpSession session) {
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");
        
        boolean isValid = adminService.validateAdminLogin(username, password);
        Map<String, Object> response = new HashMap<>();
        if (isValid) {
            Admin admin = adminService.getAdminByUsername(username);
            session.setAttribute("userId", admin.getId());
            session.setAttribute("username", username);
            session.setAttribute("role", Role.ADMIN);
            response.put("success", true);
            response.put("message", "Login successful!");
        } else {
            response.put("success", false);
            response.put("message", "Invalid username or password.");
        }
        return response;
    }

    // 管理员管理
    @GetMapping("/admins")
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @PostMapping("/admins")
    public String addAdmin(@RequestBody Admin admin) {
        return adminService.addAdmin(admin) ? "Admin added successfully!" : "Failed to add admin.";
    }

    @PutMapping("/admins/{id}")
    public String updateAdmin(@PathVariable int id, @RequestBody Admin admin) {
        Admin existingAdmin = adminService.getAdminById(id);
        if (existingAdmin != null) {
            admin.setId(id);
            admin.setPassword(existingAdmin.getPassword());
            return adminService.updateAdmin(admin) ? "Admin updated successfully!" : "Failed to update admin.";
        }
        return "Admin not found.";
    }

    @DeleteMapping("/admins/{id}")
    public String deleteAdmin(@PathVariable int id) {
        return adminService.deleteAdminById(id) ? "Admin deleted successfully!" : "Failed to delete admin.";
    }

    // 教师管理
    @GetMapping("/teachers")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/teachers/{id}")
    public Teacher getTeacherById(@PathVariable int id) {
        return teacherService.getTeacherById(id);
    }

    @PostMapping("/teachers")
    public String addTeacher(@RequestBody Teacher teacher) {
        return teacherService.addTeacher(teacher) ? "Teacher added successfully!" : "Failed to add teacher.";
    }

    @PutMapping("/teachers/{id}")
    public String updateTeacher(@PathVariable int id, @RequestBody Teacher teacher) {
        Teacher existingTeacher = teacherService.getTeacherById(id);
        if (existingTeacher != null) {
            teacher.setId(id);
            teacher.setPassword(existingTeacher.getPassword());
            return teacherService.updateTeacher(teacher) ? "Teacher updated successfully!" : "Failed to update teacher.";
        }
        return "Teacher not found.";
    }

    @DeleteMapping("/teachers/{id}")
    public String deleteTeacher(@PathVariable int id) {
        return teacherService.deleteTeacherById(id) ? "Teacher deleted successfully!" : "Failed to delete teacher.";
    }

    // 学生管理
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        // 添加调试日志
        students.forEach(student -> {
            System.out.println("返回的学生数据: " + student);
        });
        return students;
    }

    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    @PostMapping("/students")
    public String addStudent(@RequestBody Student student) {
        System.out.println("接收到的学生数据: " + student);  // 添加日志
        return studentService.addStudent(student) ? "Student added successfully!" : "Failed to add student.";
    }

    @PutMapping("/students/{id}")
    public String updateStudent(@PathVariable int id, @RequestBody Student student) {
        System.out.println("更新的学生数据: " + student);  // 添加日志
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent != null) {
            student.setId(id);
            student.setPassword(existingStudent.getPassword());
            return studentService.updateStudent(student) ? "Student updated successfully!" : "Failed to update student.";
        }
        return "Student not found.";
    }

    @DeleteMapping("/students/{id}")
    public String deleteStudent(@PathVariable int id) {
        return studentService.deleteStudentById(id) ? "Student deleted successfully!" : "Failed to delete student.";
    }

    // 课程管理
    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/courses/{id}")
    public Course getCourseById(@PathVariable int id) {
        return courseService.getCourseById(id);
    }

    @PostMapping("/courses")
    public String addCourse(@RequestBody Course course) {
        if (course.getTeacherId() == null) {
            return "Teacher ID cannot be null";
        }

        Teacher teacher = teacherService.getTeacherById(course.getTeacherId());
        if (teacher == null) {
            return "Teacher not found with ID: " + course.getTeacherId();
        }

        return courseService.addCourse(course) > 0 ? "Course added successfully!" : "Failed to add course.";
    }

    @PutMapping("/courses/{id}")
    public String updateCourse(@PathVariable int id, @RequestBody Course course) {
        course.setId(id);
        return courseService.updateCourse(course) > 0 ? "Course updated successfully!" : "Failed to update course.";
    }

    @DeleteMapping("/courses/{id}")
    public String deleteCourse(@PathVariable int id) {
        return courseService.deleteCourse(id) > 0 ? "Course deleted successfully!" : "Failed to delete course.";
    }
}
