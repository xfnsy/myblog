package com.banxian.myblog.web.validation;

import com.banxian.myblog.common.util.DateUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 日期格式校验器
 *
 * @author wangpeng
 * @since 2021/1/11 10:43
 */
public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {

    private String datePattern = "yyyyMMdd";

    @Override
    public void initialize(DateFormat annotation) {
        this.datePattern = annotation.datePattern();
    }

    @Override
    public boolean isValid(String dateParam, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (!StringUtils.hasText(dateParam)) {
                DateUtil.parseDate(dateParam, this.datePattern);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
