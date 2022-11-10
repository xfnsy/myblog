package com.banxian.myblog.common.util;

import com.banxian.myblog.constant.EncryptConsts;

/**
 * 密码工具类
 */
public class PasswordUtil {

    public static String encrypt(String password){
        return SHAUtil.SHA256(password, EncryptConsts.SHA256_SALT);
    }

}
