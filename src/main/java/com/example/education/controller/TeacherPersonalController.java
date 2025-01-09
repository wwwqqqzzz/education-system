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
@CrossOrigin(origins = "http://localhost:8080", 
             allowCredentials = "true",
             allowedHeaders = "*",
             methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, 
                       RequestMethod.DELETE, RequestMethod.OPTIONS})
public class TeacherPersonalController {
    private final TeacherService teacherService;

    public TeacherPersonalController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // 教师登录
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginInfo, HttpSession session) {
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");
        
        boolean isValid = teacherService.validateTeacherLogin(username, password);
        Map<String, Object> response = new HashMap<>();
        if (isValid) {
            Teacher teacher = teacherService.getTeacherByUsername(username);
            session.setAttribute("userId", teacher.getId());
            session.setAttribute("username", username);
            session.setAttribute("role", Role.TEACHER);
            response.put("success", true);
            response.put("message", "Login successful!");
        } else {
            response.put("success", false);
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

    // 结课（删除课程）
    @DeleteMapping("/courses/{courseId}")
    public String deleteCourse(@PathVariable Integer courseId, HttpSession session) {
        Integer teacherId = (Integer) session.getAttribute("userId");
        if (teacherId == null) {
            throw new RuntimeException("Please log in first.");
        }
        // 验证课程是否属于该教师
        Course course = teacherService.getCourseById(courseId);
        if (course == null || !course.getTeacherId().equals(teacherId)) {
            return "No permission to delete this course.";
        }
        return teacherService.deleteCourseById(courseId) ? 
               "Course deleted successfully!" : 
               "Failed to delete course.";
    }

    // 添加选课学生
    @PostMapping("/courses/{courseId}/enroll")
    public String enrollStudent(@PathVariable Integer courseId, 
                              @RequestBody Map<String, Integer> studentInfo,
                              HttpSession session) {
        Integer teacherId = (Integer) session.getAttribute("userId");
        if (teacherId == null) {
            throw new RuntimeException("Please log in first.");
        }
        Integer studentId = studentInfo.get("studentId");
        return teacherService.enrollStudent(courseId, studentId) ? 
               "Student enrolled successfully!" : 
               "Failed to enroll student.";
    }

    // 移除选课学生
    @DeleteMapping("/courses/{courseId}/students/{studentId}")
    public String removeStudent(@PathVariable Integer courseId, 
                              @PathVariable Integer studentId,
                              HttpSession session) {
        Integer teacherId = (Integer) session.getAttribute("userId");
        if (teacherId == null) {
            throw new RuntimeException("Please log in first.");
        }
        return teacherService.removeStudentFromCourse(courseId, studentId) ? 
               "Student removed successfully!" : 
               "Failed to remove student.";
    }

    // 添加课程
    @PostMapping("/courses")
    public String createCourse(@RequestBody Course course, HttpSession session) {
        Integer teacherId = (Integer) session.getAttribute("userId");
        if (teacherId == null) {
            throw new RuntimeException("Please log in first.");
        }
        
        // 设置当前教师为课程教师
        course.setTeacherId(teacherId);
        
        // 调用服务层创建课程
        return teacherService.createCourse(course) ? 
               "Course created successfully!" : 
               "Failed to create course.";
    }

    
}
