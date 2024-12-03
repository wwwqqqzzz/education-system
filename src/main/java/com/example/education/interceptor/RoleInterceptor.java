package com.example.education.interceptor;

import com.example.education.enums.Role;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 对于 OPTIONS 请求直接放行
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        // 对于登录请求直接放行
        if (request.getRequestURI().endsWith("/login")) {
            return true;
        }

        // 获取用户角色
        Role userRole = (Role) request.getSession().getAttribute("role");
        String requestURI = request.getRequestURI();

        // 未登录的用户
        if (userRole == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 判断不同角色的访问权限
        if (requestURI.startsWith("/admin") && userRole != Role.ADMIN) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        } else if (requestURI.startsWith("/teacher") && userRole != Role.TEACHER) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        } else if (requestURI.startsWith("/student") && userRole != Role.STUDENT) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }
}
