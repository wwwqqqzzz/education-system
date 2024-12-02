package com.example.education.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Grade {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private BigDecimal grade;
    private LocalDateTime createdAt;
} 