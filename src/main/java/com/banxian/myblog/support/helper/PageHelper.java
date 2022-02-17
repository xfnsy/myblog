package com.banxian.myblog.support.helper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 分页获取
 *
 * @author wangpeng
 * @since 2020-12-31 13:50:45
 */
public class PageHelper {

    private static final ThreadLocal<Page> threadLocal = new ThreadLocal<>();

    public static <T> Page<T> get() {
        Page<T> page = threadLocal.get();
        return page == null ? new Page<T>() : page;
    }

    public static <T> void set(Page<T> page) {
        threadLocal.set(page);
    }

    public static void remove() {
        threadLocal.remove();
    }
}
