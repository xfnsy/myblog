package com.banxian.myblog.common.util;

import com.banxian.myblog.common.base.Constants;

/**
 * 密码工具类
 */
public class PasswordUtil {

    public static String encrypt(String password){
        return SHAUtil.SHA256(password, Constants.SHA256_SALT);
    }

}
