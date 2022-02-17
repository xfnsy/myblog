package com.banxian.myblog.support.helper;


import com.banxian.myblog.exception.BusinessException;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 断言类,用于简单的判断并扔出自定义异常
 *
 * @author wangpeng
 * @since 2020-12-31
 */
public class Assert {


    public static void notNull(Object obj, String msg) {
        if (obj == null) {
            throw new BusinessException(msg);
        }
    }

    public static void notBlank(String obj, String msg) {
        if (!StringUtils.hasText(obj)) {
            throw new BusinessException(msg);
        }
    }

    public static void notEmpty(Object obj, String msg) {
        if (obj == null
                || (obj instanceof Collection && ((Collection<?>) obj).size() <= 0)
                || (obj instanceof Map && ((Map<?, ?>) obj).size() <= 0)) {
            throw new BusinessException(msg);
        }
    }

    public static void mustBeTrue(boolean obj, String msg) {
        if (!obj) {
            throw new BusinessException(msg);
        }
    }


    public static void mustBeFalse(boolean obj, String msg) {
        if (obj) {
            throw new BusinessException(msg);
        }
    }


}
