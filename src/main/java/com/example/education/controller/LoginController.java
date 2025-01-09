package com.example.education.controller;

import com.example.education.entity.Admin;
import com.example.education.entity.Student;
import com.example.education.entity.Teacher;
import com.example.education.service.AdminService;
import com.example.education.service.StudentService;
import com.example.education.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class LoginController {

    private final AdminService adminService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    public LoginController(AdminService adminService, TeacherService teacherService, StudentService studentService) {
        this.adminService = adminService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData, HttpSession session) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        String role = loginData.get("role");

        Map<String, Object> response = new HashMap<>();

        switch (role) {
            case "admin":
                if (adminService.validateAdminLogin(username, password)) {
                    Admin admin = adminService.getAdminByUsername(username);
                    session.setAttribute("userId", admin.getId());
                    session.setAttribute("role", "admin");
                    response.put("message", "Login successful!");
                    response.put("role", "admin");
                }
                break;
            case "teacher":
                if (teacherService.validateTeacherLogin(username, password)) {
                    Teacher teacher = teacherService.getTeacherByUsername(username);
                    session.setAttribute("userId", teacher.getId());
                    session.setAttribute("role", "teacher");
                    response.put("message", "Login successful!");
                    response.put("role", "teacher");
                }
                break;
            case "student":
                if (studentService.validateStudentLogin(username, password)) {
                    Student student = studentService.getStudentByUsername(username);
                    session.setAttribute("userId", student.getId());
                    session.setAttribute("role", "student");
                    response.put("message", "Login successful!");
                    response.put("role", "student");
                }
                break;
        }

        if (!response.containsKey("message")) {
            response.put("message", "Invalid username or password");
        }

        return response;
    }
}
