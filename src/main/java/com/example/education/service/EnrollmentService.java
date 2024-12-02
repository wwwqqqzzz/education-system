/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 下午2:56
 */
package com.example.education.service;

import com.example.education.entity.Enrollment;
import com.example.education.mapper.EnrollmentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentMapper enrollmentMapper;

    public EnrollmentService(EnrollmentMapper enrollmentMapper) {
        this.enrollmentMapper = enrollmentMapper;
    }

    // 添加选课
    public boolean addEnrollment(Enrollment enrollment) {
        return enrollmentMapper.addEnrollment(enrollment) > 0;
    }

    // 删除选课
    public boolean deleteEnrollment(int studentId, int courseId) {
        return enrollmentMapper.deleteEnrollment(studentId, courseId) > 0;
    }

    // 获取学生的选课列表
    public List<Enrollment> getEnrollmentsByStudentId(int studentId) {
        return enrollmentMapper.getEnrollmentsByStudentId(studentId);
    }

    // 获取课程的选课列表
    public List<Enrollment> getEnrollmentsByCourseId(int courseId) {
        return enrollmentMapper.getEnrollmentsByCourseId(courseId);
    }

    // 修改成绩
    public boolean updateStudentGrade(int studentId, int courseId, Double grade) {
        return enrollmentMapper.updateGrade(studentId, courseId, grade) > 0;
    }
}

