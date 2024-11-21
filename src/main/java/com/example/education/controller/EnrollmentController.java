/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 下午3:02
 */
package com.example.education.controller;

import com.example.education.entity.Enrollment;
import com.example.education.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@CrossOrigin(origins = "http://localhost:8080")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // 学生选课
    @PostMapping
    public String addEnrollment(@RequestBody Enrollment enrollment) {
        boolean isAdded = enrollmentService.addEnrollment(enrollment);
        return isAdded ? "Enrollment added successfully!" : "Failed to add enrollment.";
    }

    // 学生退课
    @DeleteMapping
    public String deleteEnrollment(@RequestParam int studentId, @RequestParam int courseId) {
        boolean isDeleted = enrollmentService.deleteEnrollment(studentId, courseId);
        return isDeleted ? "Enrollment deleted successfully!" : "Failed to delete enrollment.";
    }

    // 获取学生的选课记录
    @GetMapping("/student/{studentId}")
    public List<Enrollment> getEnrollmentsByStudentId(@PathVariable int studentId) {
        return enrollmentService.getEnrollmentsByStudentId(studentId);
    }

    // 获取课程的选课记录
    @GetMapping("/course/{courseId}")
    public List<Enrollment> getEnrollmentsByCourseId(@PathVariable int courseId) {
        return enrollmentService.getEnrollmentsByCourseId(courseId);
    }

    // 修改成绩
    @PutMapping
    public String updateGrade(@RequestParam int studentId, @RequestParam int courseId, @RequestParam Double grade) {
        boolean isUpdated = enrollmentService.updateGrade(studentId, courseId, grade);
        return isUpdated ? "Grade updated successfully!" : "Failed to update grade.";
    }
}

