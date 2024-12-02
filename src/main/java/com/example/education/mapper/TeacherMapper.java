package com.example.education.mapper;

import com.example.education.entity.Course;
import com.example.education.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {

    @Select("SELECT * FROM teacher")
    List<Teacher> findAll();

    @Select("SELECT * FROM teacher WHERE id = #{id}")
    Teacher findById(@Param("id") int id);

    @Insert("INSERT INTO teacher (username, password, name, phone, email, department) " +
            "VALUES (#{username}, #{password}, #{name}, #{phone}, #{email}, #{department})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Teacher teacher);

    @Update("UPDATE teacher SET username = #{username}, name = #{name}, " +
            "phone = #{phone}, email = #{email}, department = #{department} WHERE id = #{id}")
    int update(Teacher teacher);

    @Delete("DELETE FROM teacher WHERE id = #{id}")
    int deleteById(@Param("id") int id);

    @Select("SELECT * FROM teacher WHERE username = #{username}")
    Teacher findTeacherByUsername(String username);

    @Select("SELECT COUNT(*) FROM teacher WHERE username = #{username} AND password = #{password}")
    int validateLogin(@Param("username") String username, @Param("password") String password);

    @Select("SELECT * FROM course WHERE teacher_id = #{teacherId}")
    List<Course> findCoursesByTeacherId(Integer teacherId);

    @Insert("INSERT INTO enrollment (student_id, course_id, grade) VALUES (#{studentId}, #{courseId}, #{grade})")
    int insertStudentGrade(@Param("courseId") int courseId, @Param("studentId") int studentId, @Param("grade") double grade);

    @Update("UPDATE enrollment SET grade = #{grade} WHERE course_id = #{courseId} AND student_id = #{studentId}")
    int updateStudentGrade(@Param("courseId") int courseId, @Param("studentId") int studentId, @Param("grade") double grade);
}
