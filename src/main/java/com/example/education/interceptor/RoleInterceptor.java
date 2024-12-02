package com.example.education.interceptor;

import com.example.education.enums.Role;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 对于 OPTIONS 请求直接放行
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        // 获取用户角色
        Role userRole = (Role) request.getSession().getAttribute("role");
        String requestURI = request.getRequestURI();
        
        // 检查请求头中的 token
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            // 如果有 token，暂时允许通过（后续可以添加 token 验证逻辑）
            return true;
        }

        // 未登录的用户
        if (userRole == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please log in.");
            return false;
        }

        // 判断不同角色的访问权限
        if (requestURI.startsWith("/admin")) {
            if (userRole != Role.ADMIN) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to access this resource.");
                return false;
            }
        } else if (requestURI.startsWith("/teacher")) {
            if (userRole != Role.TEACHER) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to access this resource.");
                return false;
            }
        } else if (requestURI.startsWith("/student")) {
            if (userRole != Role.STUDENT) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to access this resource.");
                return false;
            }
        }
        return true;
    }
}
