package com.example.education.controller;

import com.example.education.entity.Admin;
import com.example.education.entity.Student;
import com.example.education.entity.Teacher;
import com.example.education.service.AdminService;
import com.example.education.service.StudentService;
import com.example.education.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class AuthController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private TeacherService teacherService;
    
    @Autowired
    private StudentService studentService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> registerInfo) {
        String username = registerInfo.get("username");
        String password = registerInfo.get("password");
        String role = registerInfo.get("role");
        String email = registerInfo.get("email");
        
        Map<String, Object> response = new HashMap<>();
        
        // 检查用户名是否已存在
        if (adminService.getAdminByUsername(username) != null ||
            teacherService.getTeacherByUsername(username) != null ||
            studentService.getStudentByUsername(username) != null) {
            response.put("success", false);
            response.put("message", "用户名已存在");
            return response;
        }
        
        boolean success = false;
        switch (role) {
            case "teacher":
                Teacher teacher = new Teacher();
                teacher.setUsername(username);
                teacher.setPassword(password);
                teacher.setEmail(email);
                success = teacherService.addTeacher(teacher);
                break;
            case "student":
                Student student = new Student();
                student.setUsername(username);
                student.setPassword(password);
                student.setEmail(email);
                success = studentService.addStudent(student);
                break;
        }
        
        response.put("success", success);
        response.put("message", success ? "注册成功" : "注册失败");
        return response;
    }

    @PostMapping("/reset-password")
    public Map<String, Object> resetPassword(@RequestBody Map<String, String> resetInfo) {
        String username = resetInfo.get("username");
        String newPassword = resetInfo.get("newPassword");
        String role = resetInfo.get("role");
        
        Map<String, Object> response = new HashMap<>();
        
        boolean success = false;
        try {
            switch (role) {
                case "admin":
                    Admin admin = adminService.getAdminByUsername(username);
                    if (admin != null) {
                        admin.setPassword(newPassword);
                        success = adminService.updateAdmin(admin);
                    }
                    break;
                case "teacher":
                    Teacher teacher = teacherService.getTeacherByUsername(username);
                    if (teacher != null) {
                        teacher.setPassword(newPassword);
                        success = teacherService.updateTeacher(teacher);
                    }
                    break;
                case "student":
                    Student student = studentService.getStudentByUsername(username);
                    if (student != null) {
                        student.setPassword(newPassword);
                        success = studentService.updateStudent(student);
                    }
                    break;
            }
            
            response.put("success", success);
            response.put("message", success ? "密码重置成功" : "密码重置失败");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "密码重置失败: " + e.getMessage());
        }
        return response;
    }
}