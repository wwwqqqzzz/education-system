package com.example.education.mapper;

import com.example.education.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {
    @Select("SELECT id, username, password, name, phone, email, " +
            "enrollment_year, " +
            "major, " +
            "created_at as createdAt " +
            "FROM student")
    List<Student> findAll();

    @Select("SELECT * FROM student WHERE id = #{id}")
    Student findById(@Param("id") int id);

    @Insert("INSERT INTO student (username, password, name, phone, email, enrollment_year, major) " +
            "VALUES (#{username}, #{password}, #{name}, #{phone}, #{email}, #{enrollment_year}, #{major})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Student student);

    @Update("UPDATE student SET password = #{password}, name = #{name}, email = #{email}, " +
            "phone = #{phone}, major = #{major}, enrollment_year = #{enrollment_year} " +
            "WHERE id = #{id}")
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

    @Select("SELECT COUNT(*) FROM enrollment WHERE student_id = #{studentId}")
    int countCurrentCourses(Integer studentId);

    @Select("SELECT AVG(grade) FROM grade WHERE student_id = #{studentId}")
    Double calculateAverageGrade(Integer studentId);

    @Select("SELECT SUM(c.credit) FROM enrollment e " +
            "JOIN course c ON e.course_id = c.id " +
            "WHERE e.student_id = #{studentId} AND EXISTS (" +
            "   SELECT 1 FROM grade g " +
            "   WHERE g.student_id = e.student_id " +
            "   AND g.course_id = e.course_id " +
            "   AND g.grade >= 60" +
            ")")
    Integer calculateTotalCredits(Integer studentId);

    @Select("SELECT c.name as name, t.name as teacher, " +
            "c.schedule as time, c.location, c.credit as credits " +
            "FROM enrollment e " +
            "JOIN course c ON e.course_id = c.id " +
            "JOIN teacher t ON c.teacher_id = t.id " +
            "WHERE e.student_id = #{studentId} " +
            "ORDER BY e.created_at DESC LIMIT 3")
    List<Map<String, Object>> findRecentCourses(Integer studentId);

    @Update("UPDATE student SET password = #{password} WHERE id = #{id}")
    int updatePassword(Student student);

    
}
