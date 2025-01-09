/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/18 上午1:42
 */
package com.example.education.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Admin {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private Date createdAt;
    private Integer roleId;
}

