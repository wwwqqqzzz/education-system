/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 上午2:03
 */
package com.example.education.service;

import com.example.education.entity.Admin;
import com.example.education.entity.Student;
import com.example.education.mapper.StudentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    /**
     * 原本内容
     * private final StudentMapper studentMapper;
     *
     *     public StudentService(StudentMapper studentMapper) {
     *         this.studentMapper = studentMapper;
     *     }
     *
     *     public List<Student> getAllStudents() {
     *         return studentMapper.findAll();
     *     }
     *
     *     public int addStudent(Student student) {
     *         return studentMapper.insertStudent(student);
     *     }
     *
     *     public int updateStudent(Student student) {
     *         return studentMapper.updateStudent(student);
     *     }
     *
     *     public int deleteStudent(int id) {
     *         return studentMapper.deleteStudent(id);
     *     }
     */

    private final StudentMapper studentMapper;

    public StudentService(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

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

    //student登陆
    public boolean validateStudentLogin(String username, String password) {
        Student student = studentMapper.findStudentByUsername(username);
        return student != null && student.getPassword().equals(password);
    }


}
