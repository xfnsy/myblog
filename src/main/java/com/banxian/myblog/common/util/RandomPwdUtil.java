package com.banxian.myblog.common.util;

import java.util.Random;

/**
 * 随机密码生成
 */
public class RandomPwdUtil {


    private static final String SPECIAL_CHARS = "!@#$%&*=";

    private static int flag = 1;

    /**
     * 生成随机数字密码
     *
     * @param length 密码长度
     * @return 密码
     */
    public static String randNumerPwd(int length) {
        char[] chars = new char[length];
        Random randomNumber = new Random();
        for (int i = 0; i < length; i++) {
            chars[i] = (char) ('0' + randomNumber.nextInt(10));
        }
        return new String(chars);
    }

    /**
     * 生成随机密码，包含有字母、数字、特殊字符
     *
     * @param length 密码长度
     * @return 密码
     */
    public static String randomCharPassword(int length) {
        char[] chars = new char[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            chars[i] = nextChar(random);
        }
        return new String(chars);
    }

    /**
     * 生成随机简单密码，特殊字符或大写字母续出现一次
     *
     * @param length 密码长度
     * @return 密码
     */
    public static String randomSimpleCharPassword(int length) {
        char[] chars = new char[length];
        Random random = new Random();
        flag = 1;
        for (int i = 0; i < length; i++) {
            chars[i] = nextSimpleChar(random);
        }
        return new String(chars);
    }

    /**
     * 生成简单密码
     *
     * @param random
     * @return
     */
    private static char nextSimpleChar(Random random) {
        switch (random.nextInt(flag == 0 ? 2 : 4)) {
            case 0:
                if (random.nextInt(6) < 5) {
                    return (char) ('a' + random.nextInt(26));
                } else {
                    return (char) ('A' + random.nextInt(26));
                }
            case 1:
                return (char) ('0' + random.nextInt(10));
            default:
                flag--;
                return SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length()));
        }
    }

    private static char nextChar(Random random) {
        switch (random.nextInt(4)) {
            case 0:
                return (char) ('a' + random.nextInt(26));
            case 1:
                return (char) ('A' + random.nextInt(26));
            case 2:
                return (char) ('0' + random.nextInt(10));
            default:
                return SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length()));
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            System.out.println(randomSimpleCharPassword(10));
        }

    }


}
