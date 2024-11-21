/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 下午2:55
 */
package com.example.education.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.security.Timestamp;

@Data
public class Enrollment {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private Double grade; // 成绩，允许为空
    private Timestamp createdAt;
}

