/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 上午2:01
 */
package com.example.education.entity;

import lombok.Data;

@Data
public class Student {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private Integer enrollmentYear;
    private String major;
}
