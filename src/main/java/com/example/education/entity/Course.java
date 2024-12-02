/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 下午2:42
 */
package com.example.education.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Course {
    private Integer id;
    private String name;
    private String description;
    private Integer teacherId;
    private LocalDateTime createdAt;
}
