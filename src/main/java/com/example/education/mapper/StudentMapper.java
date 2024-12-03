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

    @Select("SELECT c.id as course_id, c.name as course_name, " +
            "t.name as teacher_name, g.grade, g.created_at " +
            "FROM course c " +
            "JOIN teacher t ON c.teacher_id = t.id " +
            "JOIN enrollment e ON c.id = e.course_id " +
            "LEFT JOIN grade g ON e.course_id = g.course_id AND e.student_id = g.student_id " +
            "WHERE e.student_id = #{studentId}")
    List<Map<String, Object>> findStudentCoursesAndGrades(Integer studentId);

    @Select("SELECT c.id as course_id, c.name as course_name, " +
            "c.description, t.name as teacher_name " +
            "FROM course c " +
            "JOIN teacher t ON c.teacher_id = t.id " +
            "WHERE c.id NOT IN (SELECT course_id FROM enrollment WHERE student_id = #{studentId})")
    List<Map<String, Object>> findAvailableCourses(Integer studentId);

    @Select("SELECT COUNT(*) FROM enrollment WHERE student_id = #{studentId} AND course_id = #{courseId}")
    int checkEnrollment(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    @Select("SELECT COUNT(*) FROM grade WHERE student_id = #{studentId} AND course_id = #{courseId}")
    int checkGradeExists(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    @Insert("INSERT INTO enrollment (student_id, course_id) VALUES (#{studentId}, #{courseId})")
    int insertEnrollment(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    @Delete("DELETE FROM enrollment WHERE student_id = #{studentId} AND course_id = #{courseId}")
    int deleteEnrollment(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);
}
