/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 下午2:43
 */
package com.example.education.service;

import com.example.education.entity.Course;
import com.example.education.mapper.CourseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseMapper courseMapper;

    public CourseService(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    public List<Course> getAllCourses() {
        return courseMapper.findAll();
    }

    public Course getCourseById(int id) {
        return courseMapper.findCourseById(id);
    }

    public int addCourse(Course course) {
        return courseMapper.insertCourse(course);
    }

    public int updateCourse(Course course) {
        return courseMapper.updateCourse(course);
    }

    public int deleteCourse(int id) {
        return courseMapper.deleteCourse(id);
    }

}

