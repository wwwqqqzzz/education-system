package com.example.education.mapper;

import com.example.education.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {

    // 查询所有课程
    @Select("SELECT * FROM course")
    List<Course> findAll();

    // 根据课程 ID 查询课程
    @Select("SELECT * FROM course WHERE id = #{id}")
    Course findCourseById(int id);

    // 插入课程
    @Insert("INSERT INTO course (name, description, teacher_id) " +
            "VALUES (#{name}, #{description}, #{teacherId})")
    int insertCourse(Course course);

    // 更新课程
    @Update("UPDATE course SET name=#{name}, description=#{description}, teacher_id=#{teacherId} WHERE id=#{id}")
    int updateCourse(Course course);

    // 删除课程
    @Delete("DELETE FROM course WHERE id=#{id}")
    int deleteCourse(int id);
}
