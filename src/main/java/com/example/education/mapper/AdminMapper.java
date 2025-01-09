/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 下午3:24
 */
package com.example.education.mapper;

import com.example.education.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM admin WHERE username = #{username}")
    Admin findAdminByUsername(String username);

    @Select("SELECT * FROM admin WHERE id = #{id}")
    Admin findAdminById(int id);

    @Select("SELECT id, username, password, name, phone, email, " +
            "created_at as createdAt, role_id as roleId " +
            "FROM admin")
    List<Admin> findAllAdmins();

    @Insert("INSERT INTO admin (username, password, name, phone, email, role_id) " +
            "VALUES (#{username}, #{password}, #{name}, #{phone}, #{email}, #{roleId})")
    int insertAdmin(Admin admin);

    @Update("UPDATE admin SET username = #{username}, name = #{name}, " +
            "phone = #{phone}, email = #{email}, role_id = #{roleId} " +
            "WHERE id = #{id}")
    int updateAdmin(Admin admin);

    @Delete("DELETE FROM admin WHERE id = #{id}")
    int deleteAdminById(int id);


//    登陆
    @Select("SELECT * FROM admin WHERE username = #{username} AND password = #{password}")
    Admin login(String username, String password);
}
