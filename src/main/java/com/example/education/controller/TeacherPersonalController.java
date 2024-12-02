/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/12/2 下午8:27
 */
package com.example.education.controller;

import com.example.education.entity.Course;
import com.example.education.entity.Teacher;
import com.example.education.enums.Role;
import com.example.education.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherPersonalController {
    private final TeacherService teacherService;

    public TeacherPersonalController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // 教师登录
    @PostMapping("/login")
    public Map<String, Object> login(HttpSession session,
                                     @RequestParam String username,
                                     @RequestParam String password) {
        boolean isValid = teacherService.validateTeacherLogin(username, password);
        Map<String, Object> response = new HashMap<>();
        if (isValid) {
            Teacher teacher = teacherService.getTeacherByUsername(username);
            session.setAttribute("userId", teacher.getId());
            session.setAttribute("role", Role.TEACHER);
            response.put("message", "Login successful!");
        } else {
            response.put("message", "Invalid username or password.");
        }
        return response;
    }

    // 获取教师个人信息
    @GetMapping("/profile")
    public Teacher getProfile(HttpSession session) {
        Integer teacherId = (Integer) session.getAttribute("userId");
        if (teacherId == null) {
            throw new RuntimeException("Please log in first.");
        }
        return teacherService.getTeacherById(teacherId);
    }

    // 获取教师的课程
    @GetMapping("/courses")
    public List<Course> getTeacherCourses(HttpSession session) {
        Integer teacherId = (Integer) session.getAttribute("userId");
        if (teacherId == null) {
            throw new RuntimeException("Please log in first.");
        }
        return teacherService.getTeacherCourses(teacherId);
    }

    // 录入学生成绩
    @PostMapping("/courses/{courseId}/students/{studentId}/grade")
    public String addStudentGrade(@PathVariable int courseId,
                                  @PathVariable int studentId,
                                  @RequestParam double grade) {
        return teacherService.addStudentGrade(courseId, studentId, grade) ?
                "Grade added successfully!" : "Failed to add grade.";
    }
}
