/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 上午2:46
 */
package com.example.education.controller;

import com.example.education.entity.Teacher;
import com.example.education.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@CrossOrigin(origins = "http://localhost:8080")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        boolean isValid = teacherService.validateTeacherLogin(username, password);
        if (isValid) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

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
        boolean isAdded = teacherService.addTeacher(teacher);
        return isAdded ? "Teacher added successfully!" : "Failed to add teacher.";
    }

    @PutMapping("/teachers/{id}")
    public String updateTeacher(@PathVariable int id, @RequestBody Teacher teacher) {
        teacher.setId(id);
        boolean isUpdated = teacherService.updateTeacher(teacher);
        return isUpdated ? "Teacher updated successfully!" : "Failed to update teacher.";
    }

    @DeleteMapping("/teachers/{id}")
    public String deleteTeacherById(@PathVariable int id) {
        boolean isDeleted = teacherService.deleteTeacherById(id);
        return isDeleted ? "Teacher deleted successfully!" : "Failed to delete teacher.";
    }
}
