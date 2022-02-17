package com.banxian.myblog.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 日期格式校验注解
 *
 * @author wangpeng
 * @since 2021/1/11 10:40
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {DateFormatValidator.class})
public @interface DateFormat{

    // 默认错误消息
    String message() default "日期格式错误";

    // 默认日期格式
    String datePattern() default "yyyyMMdd";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};

}
