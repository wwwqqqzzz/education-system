package com.example.education.mapper;

import com.example.education.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {

    /**
     * 原来内容
     *
     *     @Select("SELECT * FROM teacher")
     *     List<Teacher> findAll();
     *
     *     @Insert("INSERT INTO teacher (username, password, name, phone, email, hiredate, department) " +
     *             "VALUES (#{username}, #{password}, #{name}, #{phone}, #{email}, #{hiredate}, #{department})")
     *     int insertTeacher(Teacher teacher);
     *
     *     @Update("UPDATE teacher SET name=#{name}, phone=#{phone}, email=#{email}, " +
     *             "hiredate=#{hiredate}, department=#{department} WHERE id=#{id}")
     *     int updateTeacher(Teacher teacher);
     *
     *     @Delete("DELETE FROM teacher WHERE id=#{id}")
     *     int deleteTeacher(int id);
     */

    @Select("SELECT * FROM teacher")
    List<Teacher> findAll();

    @Select("SELECT * FROM teacher WHERE id = #{id}")
    Teacher findById(@Param("id") int id);

    @Select("SELECT * FROM teacher WHERE username = #{username}")
    Teacher findTeacherByUsername(String username);

    @Insert("INSERT INTO teacher (username, password, name, phone, email, department) " +
            "VALUES (#{username}, #{password}, #{name}, #{phone}, #{email}, #{department})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Teacher teacher);

    @Update("UPDATE teacher SET username = #{username}, password = #{password}, name = #{name}, " +
            "phone = #{phone}, email = #{email}, department = #{department} WHERE id = #{id}")
    int update(Teacher teacher);

    @Delete("DELETE FROM teacher WHERE id = #{id}")
    int deleteById(@Param("id") int id);


}
