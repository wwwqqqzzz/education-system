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
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
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
            session.setAttribute("username", username);
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
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("Please log in first.");
        }
        return teacherService.getTeacherById(userId);
    }

    // 获取教师的课程列表
    @GetMapping("/courses")
    public List<Course> getTeacherCourses(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("Please log in first.");
        }
        return teacherService.getTeacherCourses(userId);
    }

    // 获取某门课程的学生列表及其成绩
    @GetMapping("/courses/{courseId}/students")
    public List<Map<String, Object>> getCourseStudents(@PathVariable Integer courseId) {
        return teacherService.getStudentsByCourse(courseId);
    }

    // 录入或更新学生成绩
    @PostMapping("/grades")
    public String saveGrade(@RequestBody Map<String, Object> gradeInfo) {
        Integer studentId = Integer.parseInt(gradeInfo.get("studentId").toString());
        Integer courseId = Integer.parseInt(gradeInfo.get("courseId").toString());
        Double grade = Double.parseDouble(gradeInfo.get("grade").toString());
        
        boolean success = teacherService.updateStudentGrade(courseId, studentId, grade);
        return success ? "Grade saved successfully!" : "Failed to save grade.";
    }

    // 获取课程选课人数
    @GetMapping("/courses/{courseId}/student-count")
    public Integer getStudentCount(@PathVariable Integer courseId) {
        return teacherService.getStudentCount(courseId);
    }
}
