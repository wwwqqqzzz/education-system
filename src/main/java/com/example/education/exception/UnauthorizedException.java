/**
 * 功能:
 * 作者: 王起哲
 * 日期: 2024/11/29 上午12:15
 */
package com.example.education.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
