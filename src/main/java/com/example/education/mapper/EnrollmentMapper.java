package com.example.education.mapper;

import com.example.education.entity.Enrollment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EnrollmentMapper {

    // 添加选课记录
    @Insert("INSERT INTO enrollment (student_id, course_id, grade, created_at) VALUES (#{studentId}, #{courseId}, NULL, CURRENT_TIMESTAMP)")
    int addEnrollment(Enrollment enrollment);

    // 删除选课记录
    @Delete("DELETE FROM enrollment WHERE student_id = #{studentId} AND course_id = #{courseId}")
    int deleteEnrollment(@Param("studentId") int studentId, @Param("courseId") int courseId);

    // 查询学生的选课记录
    @Select("SELECT * FROM enrollment WHERE student_id = #{studentId}")
    List<Enrollment> getEnrollmentsByStudentId(int studentId);

    // 查询课程的选课记录
    @Select("SELECT * FROM enrollment WHERE course_id = #{courseId}")
    List<Enrollment> getEnrollmentsByCourseId(int courseId);

    // 修改成绩
    @Update("UPDATE enrollment SET grade = #{grade} WHERE course_id = #{courseId} AND student_id = #{studentId}")
    int updateGrade(@Param("courseId") int courseId, @Param("studentId") int studentId, @Param("grade") Double grade);

}

