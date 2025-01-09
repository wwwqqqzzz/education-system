/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/12/2 下午8:23
 */
package com.example.education.controller;

import com.example.education.entity.Student;
import com.example.education.enums.Role;
import com.example.education.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:8080", 
             allowCredentials = "true",
             allowedHeaders = "*",
             methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, 
                       RequestMethod.DELETE, RequestMethod.OPTIONS})
public class StudentPersonalController {
    private final StudentService studentService;

    public StudentPersonalController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 学生登录
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginInfo, HttpSession session) {
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");
        
        System.out.println("尝试登录: username=" + username + ", password=" + password);  // 添加日志
        
        boolean isValid = studentService.validateStudentLogin(username, password);
        Map<String, Object> response = new HashMap<>();
        
        System.out.println("登录验证结果: " + isValid);  // 添加日志
        
        if (isValid) {
            Student student = studentService.getStudentByUsername(username);
            System.out.println("获取到的学生信息: " + student);  // 添加日志
            
            session.setAttribute("userId", student.getId());
            session.setAttribute("username", username);
            session.setAttribute("role", Role.STUDENT);
            response.put("success", true);
            response.put("message", "Login successful!");
        } else {
            response.put("success", false);
            response.put("message", "Invalid username or password.");
        }
        return response;
    }

    // 获取学生的课程
    @GetMapping("/courses")
    public List<Map<String, Object>> getStudentCourses(HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("userId");
        if (studentId == null) {
            throw new RuntimeException("Please log in as a student.");
        }
        return studentService.getStudentCoursesAndGrades(studentId);
    }

    // 获取个人信息
    @GetMapping("/profile")
    public Student getProfile(HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("userId");
        if (studentId == null) {
            throw new RuntimeException("Please log in first.");
        }
        return studentService.getStudentById(studentId);
    }

    // 获取可选课程列表
    @GetMapping("/available-courses")
    public List<Map<String, Object>> getAvailableCourses(HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("userId");
        if (studentId == null) {
            throw new RuntimeException("Please log in first.");
        }
        return studentService.getAvailableCourses(studentId);
    }

    // 选课
    @PostMapping("/courses/{courseId}/select")
    public String selectCourse(HttpSession session, @PathVariable Integer courseId) {
        Integer studentId = (Integer) session.getAttribute("userId");
        if (studentId == null) {
            throw new RuntimeException("Please log in first.");
        }
        return studentService.selectCourse(studentId, courseId) ? 
               "Course selected successfully!" : "Failed to select course.";
    }

    // 退课
    @DeleteMapping("/courses/{courseId}/drop")
    public String dropCourse(HttpSession session, @PathVariable Integer courseId) {
        Integer studentId = (Integer) session.getAttribute("userId");
        if (studentId == null) {
            throw new RuntimeException("Please log in first.");
        }
        return studentService.dropCourse(studentId, courseId) ? 
               "Course dropped successfully!" : "Failed to drop course.";
    }

    
}
