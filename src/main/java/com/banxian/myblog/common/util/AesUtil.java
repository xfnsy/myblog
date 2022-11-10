package com.banxian.myblog.common.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * Aes加解密
 *
 * @author  wangpeng
 * @since  2022/11/10 09.15.44
 */
public class AesUtil {

    public static String aesEncrypt(String content, String key) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key.getBytes()));
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
            byte[] byteRresult = cipher.doFinal(byteContent);
            StringBuilder sb = new StringBuilder();
            for (byte b : byteRresult) {
                String hex = Integer.toHexString(b & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append(hex.toUpperCase());
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String aesDecrypt(String encryptStr, String key) {
        return aesDecryptByBytes(parseHexStr2Byte(encryptStr), key);
    }

    private static String aesDecryptByBytes(byte[] encryptBytes, String key) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key.getBytes()));
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) {
        String key="1eadsd212e122@!@#";
        String v = aesEncrypt("你好啊", key);
        System.out.println(v);
        String s = aesDecrypt(v, key);
        System.out.println(s);
    }

}
