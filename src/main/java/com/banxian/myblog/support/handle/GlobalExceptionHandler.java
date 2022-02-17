package com.banxian.myblog.support.handle;

import com.banxian.myblog.exception.BusinessException;
import com.banxian.myblog.exception.TokenException;
import com.banxian.myblog.exception.UndefinedException;
import com.banxian.myblog.web.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Controller层全局异常处理
 *
 * @author wangpeng
 * @since 2021/1/4 13:47
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public JsonView handleException(Exception ex) {
        try {
            // 1。自定义业务异常处理
            if (ex instanceof BusinessException) {
                return JsonView.fail(ex.getMessage());
                // 2。token校验异常,失效或无效
            } else if (ex instanceof TokenException) {
                return JsonView.fail(JsonView.TOKEN_INVALID, ex.getMessage());
                // 3.未定义的系统异常
            } else if (ex instanceof UndefinedException) {
                return JsonView.fail(ex.getMessage());
                // 4.参数校验失败异常,提取其中的提示信息
            } else if (ex instanceof MethodArgumentNotValidException) {
                BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
                // 可能有多个参数校验失败
                List<ObjectError> allErrors = bindingResult.getAllErrors();
                StringBuffer sbf = new StringBuffer("参数校验失败:");
                allErrors.forEach(e -> sbf.append(e.getDefaultMessage()).append(";"));
                return JsonView.fail(sbf.deleteCharAt(sbf.length() - 1).toString());
            }
        } catch (Exception e) {
            // 统一在下面处理
            ex = e;
        }
        // 未定义异常记录日志
        log.error("未定义异常详情: --->", ex);
        return JsonView.fail("系统异常!!!");
    }

}
