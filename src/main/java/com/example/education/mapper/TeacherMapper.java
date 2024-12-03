package com.example.education.mapper;

import com.example.education.entity.Course;
import com.example.education.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeacherMapper {

    @Select("SELECT * FROM teacher")
    List<Teacher> findAll();

    @Select("SELECT * FROM teacher WHERE id = #{id}")
    Teacher findById(@Param("id") int id);

    @Insert("INSERT INTO teacher (username, password, name, email, phone) VALUES (#{username}, #{password}, #{name}, #{email}, #{phone})")
    int insert(Teacher teacher);

    @Update("UPDATE teacher SET name = #{name}, email = #{email}, phone = #{phone} WHERE id = #{id}")
    int update(Teacher teacher);

    @Delete("DELETE FROM teacher WHERE id = #{id}")
    int deleteById(@Param("id") int id);

    @Select("SELECT * FROM teacher WHERE username = #{username}")
    Teacher findTeacherByUsername(String username);

    @Select("SELECT COUNT(*) FROM teacher WHERE username = #{username} AND password = #{password}")
    int validateLogin(@Param("username") String username, @Param("password") String password);

    @Select("SELECT * FROM course WHERE teacher_id = #{teacherId}")
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
}
