///**
// * 功能:
// * 作者: 王起哲
// * 日期: 2024/11/18 上午2:04
// */
//package com.example.education.controller;
//
//import com.example.education.entity.Student;
//import com.example.education.enums.Role;
//import com.example.education.service.StudentService;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpSession;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/admin/students")
//public class StudentController {
//    private final StudentService studentService;
//
//    public StudentController(StudentService studentService) {
//        this.studentService = studentService;
//    }
//
//    @GetMapping
//    public List<Student> getAllStudents() {
//        return studentService.getAllStudents();
//    }
//
//    @GetMapping("/{id}")
//    public Student getStudentById(@PathVariable int id) {
//        return studentService.getStudentById(id);
//    }
//
//    @PostMapping
//    public String addStudent(@RequestBody Student student) {
//        return studentService.addStudent(student) ? "Student added successfully!" : "Failed to add student.";
//    }
//
//    @PutMapping("/{id}")
//    public String updateStudent(@PathVariable int id, @RequestBody Student student) {
//        student.setId(id);
//        return studentService.updateStudent(student) ? "Student updated successfully!" : "Failed to update student.";
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteStudent(@PathVariable int id) {
//        return studentService.deleteStudentById(id) ? "Student deleted successfully!" : "Failed to delete student.";
//    }
//}
//
