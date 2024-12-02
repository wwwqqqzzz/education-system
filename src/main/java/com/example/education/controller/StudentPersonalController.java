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
public class StudentPersonalController {
    private final StudentService studentService;

    public StudentPersonalController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 学生登录
    @PostMapping("/login")
    public Map<String, Object> login(HttpSession session,
                                     @RequestParam String username,
                                     @RequestParam String password) {
        boolean isValid = studentService.validateStudentLogin(username, password);
        Map<String, Object> response = new HashMap<>();
        if (isValid) {
            Student student = studentService.getStudentByUsername(username);
            session.setAttribute("userId", student.getId());
            session.setAttribute("role", Role.STUDENT);
            response.put("message", "Login successful!");
        } else {
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
}
