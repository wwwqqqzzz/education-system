package com.example.education.mapper;

import com.example.education.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {
    @Select("SELECT * FROM student")
    List<Student> findAll();

    @Select("SELECT * FROM student WHERE id = #{id}")
    Student findById(@Param("id") int id);

    @Insert("INSERT INTO student (username, password, name, phone, email, enrollment_year, major) " +
            "VALUES (#{username}, #{password}, #{name}, #{phone}, #{email}, #{enrollmentYear}, #{major})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Student student);

    @Update("UPDATE student SET username = #{username}, name = #{name}, " +
            "phone = #{phone}, email = #{email}, enrollment_year = #{enrollmentYear}, " +
            "major = #{major} WHERE id = #{id}")
    int update(Student student);

    @Delete("DELETE FROM student WHERE id = #{id}")
    int deleteById(@Param("id") int id);

    @Select("SELECT * FROM student WHERE username = #{username}")
    Student findStudentByUsername(String username);

    @Select("SELECT COUNT(*) FROM student WHERE username = #{username} AND password = #{password}")
    int validateLogin(@Param("username") String username, @Param("password") String password);

    @Select("SELECT c.id AS courseId, c.name AS courseName, e.grade " +
            "FROM enrollment e " +
            "JOIN course c ON e.course_id = c.id " +
            "WHERE e.student_id = #{studentId}")
    List<Map<String, Object>> findStudentCoursesAndGrades(int studentId);
}
