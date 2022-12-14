package com.banxian.myblog.exception;

/**
 * token异常
 *
 * @author wangpeng
 * @since 2022-1-15 19:59:24
 */
public class TokenInvalidException extends RuntimeException {

    // 无参构造函数
    public TokenInvalidException() {
        super();
    }

    // 用详细信息指定一个异常
    public TokenInvalidException(String message) {
        super(message);
    }

    // 用指定的详细信息和原因构造一个新的异常
    public TokenInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    // 用指定原因构造一个新的异常
    public TokenInvalidException(Throwable cause) {
        super(cause);
    }

}
