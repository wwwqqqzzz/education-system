/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 下午2:43
 */
package com.example.education.controller;

import com.example.education.entity.Course;
import com.example.education.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/courses")
@CrossOrigin(origins = "http://localhost:8080")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable int id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    public String addCourse(@RequestBody Course course) {
        return courseService.addCourse(course) > 0 ? "Course added successfully!" : "Failed to add course.";
    }

    @PutMapping("/{id}")
    public String updateCourse(@PathVariable int id, @RequestBody Course course) {
        course.setId(id);
        return courseService.updateCourse(course) > 0 ? "Course updated successfully!" : "Failed to update course.";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable int id) {
        return courseService.deleteCourse(id) > 0 ? "Course deleted successfully!" : "Failed to delete course.";
    }
}

