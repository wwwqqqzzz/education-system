package com.example.education.mapper;

import com.example.education.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    /**
     * 原来内容
     *
     * @Select("SELECT * FROM student")
     *     List<Student> findAll();
     *
     *     @Insert("INSERT INTO student (username, password, name, phone, email, enrollment_year, major) " +
     *             "VALUES (#{username}, #{password}, #{name}, #{phone}, #{email}, #{enrollmentYear}, #{major})")
     *     int insertStudent(Student student);
     *
     *     @Update("UPDATE student SET name=#{name}, phone=#{phone}, email=#{email}, " +
     *             "enrollment_year=#{enrollmentYear}, major=#{major} WHERE id=#{id}")
     *     int updateStudent(Student student);
     *
     *     @Delete("DELETE FROM student WHERE id=#{id}")
     *     int deleteStudent(int id);
     */


    @Select("SELECT * FROM student")
    List<Student> findAll();

    @Select("SELECT * FROM student WHERE id = #{id}")
    Student findById(@Param("id") int id);

    @Select("SELECT * FROM student WHERE username = #{username}")
    Student findStudentByUsername(String username);

    @Insert("INSERT INTO student (username, password, name, phone, email, enrollment_year, major) " +
            "VALUES (#{username}, #{password}, #{name}, #{phone}, #{email}, #{enrollmentYear}, #{major})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Student student);

    @Update("UPDATE student SET username = #{username}, password = #{password}, name = #{name}, " +
            "phone = #{phone}, email = #{email}, enrollment_year = #{enrollmentYear}, major = #{major} WHERE id = #{id}")
    int update(Student student);

    @Delete("DELETE FROM student WHERE id = #{id}")
    int deleteById(@Param("id") int id);


}
