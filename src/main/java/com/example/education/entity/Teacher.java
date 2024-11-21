/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 上午2:43
 */
package com.example.education.entity;

import lombok.Data;

import java.util.Date;


@Data
public class Teacher {
    private int id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private Date hiredate;
    private String department;
}
