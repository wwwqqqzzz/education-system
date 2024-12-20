/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 上午2:03
 */
package com.example.education.service;

import com.example.education.entity.Student;
import com.example.education.mapper.StudentMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private final StudentMapper studentMapper;

    public StudentService(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    // 管理员功能
    public List<Student> getAllStudents() {
        return studentMapper.findAll();
    }

    public Student getStudentById(int id) {
        return studentMapper.findById(id);
    }

    public boolean addStudent(Student student) {
        return studentMapper.insert(student) > 0;
    }

    public boolean updateStudent(Student student) {
        return studentMapper.update(student) > 0;
    }

    public boolean deleteStudentById(int id) {
        return studentMapper.deleteById(id) > 0;
    }

    // 学生个人功能
    public Student getStudentByUsername(String username) {
        return studentMapper.findStudentByUsername(username);
    }

    public boolean validateStudentLogin(String username, String password) {
        return studentMapper.validateLogin(username, password) > 0;
    }

    public List<Map<String, Object>> getStudentCoursesAndGrades(int studentId) {
        return studentMapper.findStudentCoursesAndGrades(studentId);
    }

    // 新增选课相关功能
    public List<Map<String, Object>> getAvailableCourses(Integer studentId) {
        return studentMapper.findAvailableCourses(studentId);
    }

    public boolean selectCourse(Integer studentId, Integer courseId) {
        // 检查是否已经选过这门课
        if (studentMapper.checkEnrollment(studentId, courseId) > 0) {
            return false;
        }
        return studentMapper.insertEnrollment(studentId, courseId) > 0;
    }

    public boolean dropCourse(Integer studentId, Integer courseId) {
        // 检查是否已经有成绩
        if (studentMapper.checkGradeExists(studentId, courseId) > 0) {
            return false; // 已有成绩的课程不能退选
        }
        return studentMapper.deleteEnrollment(studentId, courseId) > 0;
    }
}
