package com.banxian.myblog.support.helper;

import com.banxian.myblog.common.base.UserInfo;

public class UserInfoHelper {

    private static final ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();

    public static void set(UserInfo val) {
        threadLocal.set(val);
    }

    public static UserInfo get() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }

}
