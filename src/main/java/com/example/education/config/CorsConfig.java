/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/19 上午12:38
 */
package com.example.education.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置全局跨域，允许所有接口跨域请求
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")  // 允许的前端地址
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的请求方法
                .allowedHeaders("*")  // 允许的请求头
                .allowCredentials(true);  // 允许发送凭证（例如 Cookies）
    }
}

