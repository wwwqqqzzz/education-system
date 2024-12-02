//package com.example.education.controller;
//
//import com.example.education.entity.Teacher;
//import com.example.education.service.TeacherService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/admin/teachers")
//public class TeacherController {
//    private final TeacherService teacherService;
//
//    public TeacherController(TeacherService teacherService) {
//        this.teacherService = teacherService;
//    }
//
//    @GetMapping
//    public List<Teacher> getAllTeachers() {
//        return teacherService.getAllTeachers();
//    }
//
//    @GetMapping("/{id}")
//    public Teacher getTeacherById(@PathVariable int id) {
//        return teacherService.getTeacherById(id);
//    }
//
//    @PostMapping
//    public String addTeacher(@RequestBody Teacher teacher) {
//        return teacherService.addTeacher(teacher) ? "Teacher added successfully!" : "Failed to add teacher.";
//    }
//
//    @PutMapping("/{id}")
//    public String updateTeacher(@PathVariable int id, @RequestBody Teacher teacher) {
//        Teacher existingTeacher = teacherService.getTeacherById(id);
//        if (existingTeacher != null) {
//            teacher.setId(id);
//            teacher.setPassword(existingTeacher.getPassword());
//            return teacherService.updateTeacher(teacher) ? "Teacher updated successfully!" : "Failed to update teacher.";
//        }
//        return "Teacher not found.";
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteTeacher(@PathVariable int id) {
//        return teacherService.deleteTeacherById(id) ? "Teacher deleted successfully!" : "Failed to delete teacher.";
//    }
//}
