package com.banxian.myblog.exception;

/**
 * 系统发生的一些未定义异常
 *
 * @author wangpeng
 * @since 2021/1/4 13:37
 */
public class UndefinedException extends RuntimeException {

    // 无参构造函数
    public UndefinedException() {
        super();
    }

    // 用详细信息指定一个异常
    public UndefinedException(String message) {
        super(message);
    }

    // 用指定的详细信息和原因构造一个新的异常
    public UndefinedException(String message, Throwable cause) {
        super(message, cause);
    }

    // 用指定原因构造一个新的异常
    public UndefinedException(Throwable cause) {
        super(cause);
    }

}
