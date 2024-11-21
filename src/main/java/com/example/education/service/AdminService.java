/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 下午3:24
 */
package com.example.education.service;

import com.example.education.entity.Admin;
import com.example.education.mapper.AdminMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminMapper adminMapper;

    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }



    // 根据用户名获取管理员信息
    public Admin getAdminByUsername(String username) {
        return adminMapper.findAdminByUsername(username);
    }

    // 验证管理员登录
    public boolean validateAdminLogin(String username, String password) {
        Admin admin = adminMapper.findByUsernameAndPassword(username, password);
        return admin != null;
    }

    // 根据 ID 获取管理员信息
    public Admin getAdminById(int id) {
        return adminMapper.findAdminById(id);
    }

    // 添加管理员
    public boolean addAdmin(Admin admin) {
        return adminMapper.insertAdmin(admin) > 0;
    }

    // 更新管理员信息
    public boolean updateAdmin(Admin admin) {
        return adminMapper.updateAdmin(admin) > 0;
    }

    // 根据 ID 删除管理员
    public boolean deleteAdminById(int id) {
        return adminMapper.deleteAdminById(id) > 0;
    }

    // 获取所有管理员信息
    public List<Admin> getAllAdmins() {
        return adminMapper.findAllAdmins();
    }

}
