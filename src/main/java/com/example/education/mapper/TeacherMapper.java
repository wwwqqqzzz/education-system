package com.example.education.mapper;

import com.example.education.entity.Course;
import com.example.education.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeacherMapper {

    @Select("SELECT id, username, password, name, phone, email, " +
            "department, hireDate, created_at as createdAt " +
            "FROM teacher")
    List<Teacher> findAll();

    @Select("SELECT * FROM teacher WHERE id = #{id}")
    Teacher findById(@Param("id") int id);

    @Insert("INSERT INTO teacher (username, password, name, phone, email, department, hireDate) " +
            "VALUES (#{username}, #{password}, #{name}, #{phone}, #{email}, #{department}, #{hireDate})")
    int insert(Teacher teacher);

    @Update("UPDATE teacher SET password = #{password}, name = #{name}, email = #{email}, " +
            "phone = #{phone}, department = #{department}, hireDate = #{hireDate} " +
            "WHERE id = #{id}")
    int update(Teacher teacher);

    @Delete("DELETE FROM teacher WHERE id = #{id}")
    int deleteById(@Param("id") int id);

    @Select("SELECT * FROM teacher WHERE username = #{username}")
    Teacher findTeacherByUsername(String username);

    @Select("SELECT COUNT(*) FROM teacher WHERE username = #{username} AND password = #{password}")
    int validateLogin(@Param("username") String username, @Param("password") String password);

    @Select("SELECT c.id, c.name, c.description, c.teacher_id as teacherId, " +
            "c.created_at as createdAt " +
            "FROM course c " +
            "WHERE c.teacher_id = #{teacherId}")
    List<Course> findCoursesByTeacherId(Integer teacherId);

    @Select("SELECT DISTINCT " +
            "s.id, " +
            "s.name, " +
            "s.major, " +
            "s.enrollment_year, " +
            "s.phone, " +
            "s.email, " +
            "g.grade, " +
            "g.created_at " +
            "FROM student s " +
            "INNER JOIN enrollment e ON s.id = e.student_id AND e.course_id = #{courseId} " +
            "LEFT JOIN grade g ON s.id = g.student_id AND g.course_id = #{courseId}")
    List<Map<String, Object>> findStudentsByCourse(Integer courseId);

    @Select("SELECT COUNT(*) FROM grade WHERE course_id = #{courseId} AND student_id = #{studentId}")
    int checkGradeExists(@Param("courseId") int courseId, @Param("studentId") int studentId);

    @Insert("INSERT INTO grade (student_id, course_id, grade) VALUES (#{studentId}, #{courseId}, #{grade})")
    int insertStudentGrade(@Param("courseId") int courseId, @Param("studentId") int studentId, @Param("grade") double grade);

    @Update("UPDATE grade SET grade = #{grade} WHERE course_id = #{courseId} AND student_id = #{studentId}")
    int updateStudentGrade(@Param("courseId") int courseId, @Param("studentId") int studentId, @Param("grade") double grade);

    @Select("SELECT COUNT(*) FROM enrollment WHERE course_id = #{courseId} AND student_id = #{studentId}")
    int checkEnrollment(@Param("courseId") int courseId, @Param("studentId") int studentId);

    @Select("SELECT COUNT(*) FROM enrollment WHERE course_id = #{courseId}")
    int getStudentCount(@Param("courseId") Integer courseId);

    @Insert("INSERT INTO course (name, description, teacher_id) " +
            "VALUES (#{name}, #{description}, #{teacherId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCourse(Course course);

    @Delete("DELETE FROM enrollment WHERE course_id = #{courseId} AND student_id = #{studentId}")
    int deleteEnrollment(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);

    @Insert("INSERT INTO enrollment (course_id, student_id) VALUES (#{courseId}, #{studentId})")
    int insertEnrollment(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);

    @Select("SELECT * FROM course WHERE id = #{courseId}")
    Course findCourseById(@Param("courseId") Integer courseId);

    @Delete("DELETE FROM course WHERE id = #{courseId}")
    int deleteCourse(@Param("courseId") Integer courseId);

    @Update("UPDATE teacher SET password = #{password} WHERE id = #{id}")
    int updatePassword(Teacher teacher);
}
