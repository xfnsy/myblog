package com.banxian.myblog.exception;

public class RefreshTokenInvalidException extends RuntimeException{
    // 用详细信息指定一个异常
    public RefreshTokenInvalidException(String message) {
        super(message);
    }
}
