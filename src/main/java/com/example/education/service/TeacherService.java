/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 上午2:45
 */
package com.example.education.service;

import com.example.education.entity.Admin;
import com.example.education.entity.Teacher;
import com.example.education.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    /**
     * 原来内容
     *
     * private final TeacherMapper teacherMapper;
     *
     *     public TeacherService(TeacherMapper teacherMapper) {
     *         this.teacherMapper = teacherMapper;
     *     }
     *
     *     public List<Teacher> getAllTeachers() {
     *         return teacherMapper.findAll();
     *     }
     *
     *     public int addTeacher(Teacher teacher) {
     *         return teacherMapper.insertTeacher(teacher);
     *     }
     *
     *     public int updateTeacher(Teacher teacher) {
     *         return teacherMapper.updateTeacher(teacher);
     *     }
     *
     *     public int deleteTeacher(int id) {
     *         return teacherMapper.deleteTeacher(id);
     *     }
     */

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
        // 插入数据库
        return teacherMapper.insert(teacher) > 0;
    }

    public boolean updateTeacher(Teacher teacher) {
        return teacherMapper.update(teacher) > 0;
    }

    public boolean deleteTeacherById(int id) {
        return teacherMapper.deleteById(id) > 0;
    }

    //teacher登陆
    public boolean validateTeacherLogin(String username, String password) {
        Teacher teacher = teacherMapper.findTeacherByUsername(username);
        return teacher != null && teacher.getPassword().equals(password);
    }

}

