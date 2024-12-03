//package com.example.education.interceptor;
//
//import com.example.education.enums.Role;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@Component
//public class AdminAuthInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        // 如果是登录请求，直接放行
//        if (request.getRequestURI().equals("/admin/login")) {
//            return true;
//        }
//
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            Object role = session.getAttribute("role");
//            if (role != null && role.equals(Role.ADMIN)) {
//                return true;
//            }
//        }
//
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        return false;
//    }
//}
