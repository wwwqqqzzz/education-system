package com.example.education.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理所有运行时异常
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 处理未登录的异常
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>("Unauthorized: Please log in first", HttpStatus.UNAUTHORIZED);
    }

    // 处理资源未找到的异常
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>("Resource not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 处理权限不足的异常
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleForbiddenException(ForbiddenException ex) {
        return new ResponseEntity<>("Forbidden: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    // 可以添加更多的自定义异常处理
}
