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

@RestController
@RequestMapping("/auth")
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
    public Map<String, Object> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        String role = loginData.get("role");

        Map<String, Object> response = new HashMap<>();

        switch (role) {
            case "admin":
                Admin admin = adminService.getAdminByUsername(username);
                if (admin != null && admin.getPassword().equals(password)) {
                    response.put("role", "admin");
                    response.put("data", admin);
                    response.put("message", "Login successful!");
                    response.put("token", "admin-token-" + System.currentTimeMillis());
                    return response;
                }
                break;
            // ... 其他角色的case
        }

        response.put("message", "Invalid username or password.");
        return response;
    }
}
