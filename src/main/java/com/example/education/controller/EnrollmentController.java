/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 下午3:02
 */
package com.example.education.controller;

import com.example.education.entity.Enrollment;
import com.example.education.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/student/enrollments")  // 修改路径以反映这是学生的选课功能
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // 学生选课
    @PostMapping
    public String addEnrollment(@RequestBody Enrollment enrollment, HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("userId");
        if (studentId == null) {
            throw new RuntimeException("Please log in first.");
        }
        enrollment.setStudentId(studentId);
        return enrollmentService.addEnrollment(enrollment) ?
                "Enrollment added successfully!" : "Failed to add enrollment.";
    }

    // 学生退课
    @DeleteMapping("/{courseId}")
    public String deleteEnrollment(@PathVariable int courseId, HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("userId");
        if (studentId == null) {
            throw new RuntimeException("Please log in first.");
        }
        return enrollmentService.deleteEnrollment(studentId, courseId) ?
                "Enrollment deleted successfully!" : "Failed to delete enrollment.";
    }

    // 获取学生的选课记录
    @GetMapping
    public List<Enrollment> getMyEnrollments(HttpSession session) {
        Integer studentId = (Integer) session.getAttribute("userId");
        if (studentId == null) {
            throw new RuntimeException("Please log in first.");
        }
        return enrollmentService.getEnrollmentsByStudentId(studentId);
    }
}
