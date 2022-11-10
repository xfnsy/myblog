package com.banxian.myblog.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * sha256和512加密类
 * <p>
 * Created by wangpeng  2020/12/1.
 */
public class SHAUtil {


    private static final String ENCRYPT_TYPE_256 = "SHA-256";
    private static final String ENCRYPT_TYPE_512 = "SHA-512";

    /**
     * 传入文本内容，返回 SHA-256 串
     */
    public static String SHA256(final String encryptText) {
        return SHA(encryptText, ENCRYPT_TYPE_256);
    }

    public static String SHA256(final String encryptText, String salt) {
        return SHA(encryptText + salt, ENCRYPT_TYPE_256);
    }

    /**
     * 传入文本内容，返回 SHA-512 串
     */
    public static String SHA512(final String encryptText) {
        return SHA(encryptText, ENCRYPT_TYPE_512);
    }

    /**
     * 字符串 SHA 加密
     *
     * @param encryptText 需要加密的文本
     * @param encryptType sha加密类型
     */
    private static String SHA(String encryptText, String encryptType) {
        // 返回值
        String strResult = null;
        // 是否是有效字符串
        if (encryptText != null && encryptText.length() > 0) {
            try {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(encryptType);
                // 传入要加密的字符串
                messageDigest.update(encryptText.getBytes());
                // 得到 byte 類型结果
                byte[] byteBuffer = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuilder strHexString = new StringBuilder();
                // 遍歷 byte buffer
                for (byte b : byteBuffer) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return strResult;

    }

    public static void main(String[] args) {
        System.out.println(SHA256("tudou", "ban9527xian,,"));
        System.out.println(SHA512("12345@abcde"));
    }
}