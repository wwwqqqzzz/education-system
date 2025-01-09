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
import java.util.Map;

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

    public List<Map<String, Object>> getStudentsByCourse(Integer courseId) {
        return teacherMapper.findStudentsByCourse(courseId);
    }

    public boolean updateStudentGrade(int courseId, int studentId, double grade) {
        try {
            // 检查学生是否选修了这门课程
            if (teacherMapper.checkEnrollment(courseId, studentId) == 0) {
                return false;
            }

            if (teacherMapper.checkGradeExists(courseId, studentId) > 0) {
                return teacherMapper.updateStudentGrade(courseId, studentId, grade) > 0;
            } else {
                return teacherMapper.insertStudentGrade(courseId, studentId, grade) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getStudentCount(Integer courseId) {
        return teacherMapper.getStudentCount(courseId);
    }

    public Course getCourseById(Integer courseId) {
        return teacherMapper.findCourseById(courseId);
    }

    public boolean deleteCourseById(Integer courseId) {
        return teacherMapper.deleteCourse(courseId) > 0;
    }

    public boolean enrollStudent(Integer courseId, Integer studentId) {
        // 检查是否已经选课
        if (teacherMapper.checkEnrollment(courseId, studentId) > 0) {
            return false;
        }
        return teacherMapper.insertEnrollment(courseId, studentId) > 0;
    }

    public boolean removeStudentFromCourse(Integer courseId, Integer studentId) {
        return teacherMapper.deleteEnrollment(courseId, studentId) > 0;
    }

    public boolean createCourse(Course course) {
        try {
            return teacherMapper.insertCourse(course) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

