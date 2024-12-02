/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 上午2:45
 */
package com.example.education.service;

import com.example.education.entity.Course;
import com.example.education.entity.Teacher;
import com.example.education.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {


    private final TeacherMapper teacherMapper;

    public TeacherService(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }



    public List<Teacher> getAllTeachers() {
        return teacherMapper.findAll();
    }

    public Teacher getTeacherById(int id) {
        return teacherMapper.findById(id);
    }

    public boolean addTeacher(Teacher teacher) {
        return teacherMapper.insert(teacher) > 0;
    }

    public boolean updateTeacher(Teacher teacher) {
        return teacherMapper.update(teacher) > 0;
    }

    public boolean deleteTeacherById(int id) {
        return teacherMapper.deleteById(id) > 0;
    }

    public Teacher getTeacherByUsername(String username) {
        return teacherMapper.findTeacherByUsername(username);
    }

    public boolean validateTeacherLogin(String username, String password) {
        return teacherMapper.validateLogin(username, password) > 0;
    }

    public List<Course> getTeacherCourses(Integer teacherId) {
        return teacherMapper.findCoursesByTeacherId(teacherId);
    }

    public boolean addStudentGrade(int courseId, int studentId, double grade) {
        return teacherMapper.insertStudentGrade(courseId, studentId, grade) > 0;
    }

    public boolean updateStudentGrade(int courseId, int studentId, double grade) {
        return teacherMapper.updateStudentGrade(courseId, studentId, grade) > 0;
    }
}

