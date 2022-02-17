package com.banxian.myblog.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密/验证工具类
 */
public class MD5Util {


    /**
     * MD5加密后生成32位(小写字母+数字)字符串
     * 同 MD5Lower() 一样
     */
    public static String MD5(String plainText) {
        return MD5(plainText, "");
    }

    public static String MD5(String plainText, String saltValue) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            md.update(plainText.getBytes());
            md.update(saltValue.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值。1 固定值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 校验MD5码
     *
     * @param text 要校验的字符串
     * @param md5  md5值
     * @return 校验结果
     */
    public static boolean valid(String text, String md5) {
        return md5.equals(MD5(text)) || md5.equals(MD5(text).toUpperCase());
    }


    /**
     * 测试
     */
    public static void main(String[] args) {
        String saltValue = "da9527king";
        String plainText = "12345@abcde";
        System.out.println(MD5(plainText));
        System.out.println(MD5(plainText, saltValue));
        System.out.println("=====校验结果======");
        System.out.println(valid(plainText, MD5(plainText)));
    }
}
