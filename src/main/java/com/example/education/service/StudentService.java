/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 上午2:03
 */
package com.example.education.service;

import com.example.education.entity.Student;
import com.example.education.entity.Course;
import com.example.education.mapper.StudentMapper;
import com.example.education.mapper.CourseMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;

    public StudentService(StudentMapper studentMapper, CourseMapper courseMapper) {
        this.studentMapper = studentMapper;
        this.courseMapper = courseMapper;
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
        
        // 检查课程是否存在
        Course course = courseMapper.findCourseById(courseId);
        if (course == null) {
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

    public Map<String, Object> getStudentDashboardData(Integer studentId) {
        Map<String, Object> result = new HashMap<>();
        
        // 获取已选课程数据
        List<Map<String, Object>> selectedCourses = studentMapper.findStudentCoursesAndGrades(studentId);
        
        // 统计数据
        Map<String, Object> stats = new HashMap<>();
        stats.put("courseCount", selectedCourses.size());  // 实际的已选课程数量
        
        // 计算平均分
        double totalGrade = 0;
        int gradeCount = 0;
        for (Map<String, Object> course : selectedCourses) {
            Object grade = course.get("grade");
            if (grade != null) {
                totalGrade += Double.parseDouble(grade.toString());
                gradeCount++;
            }
        }
        double averageGrade = gradeCount > 0 ? totalGrade / gradeCount : 0.0;
        stats.put("averageGrade", Math.round(averageGrade * 10) / 10.0);  // 保留一位小数
        
        // 暂时保留模拟的学分数据
        stats.put("totalCredits", 16);
        
        // 使用已选课程作为最近课程数据
        List<Map<String, Object>> recentCourses = selectedCourses.stream()
            .limit(3)  // 只取前3个
            .map(course -> {
                Map<String, Object> recentCourse = new HashMap<>();
                recentCourse.put("course_id", course.get("course_id"));
                recentCourse.put("name", course.get("course_name"));
                recentCourse.put("description", course.get("description"));
                recentCourse.put("teacher", course.get("teacher_name"));
                recentCourse.put("created_at", course.get("created_at"));
                return recentCourse;
            })
            .collect(Collectors.toList());
        
        result.put("stats", stats);
        result.put("recentCourses", recentCourses);
        
        return result;
    }
}
