/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18
 */
package com.example.education.controller;

import com.example.education.entity.Admin;
import com.example.education.service.AdminService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // 管理员登录
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        boolean isValid = adminService.validateAdminLogin(username, password);
        return isValid ? "Login successful!" : "Invalid username or password.";
    }

    // 获取当前管理员的信息
    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable int id) {
        return adminService.getAdminById(id);
    }

    // 修改管理员信息
    @PutMapping("/{id}")
    public String updateAdmin(@PathVariable int id, @RequestBody Admin admin) {
        admin.setId(id);
        boolean isUpdated = adminService.updateAdmin(admin);
        return isUpdated ? "Admin updated successfully!" : "Failed to update admin.";
    }

    // 删除管理员账号
    @DeleteMapping("/{id}")
    public String deleteAdminById(@PathVariable int id) {
        boolean isDeleted = adminService.deleteAdminById(id);
        return isDeleted ? "Admin deleted successfully!" : "Failed to delete admin.";
    }
}
