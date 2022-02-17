package com.banxian.myblog.common.util;

/**
 * 字符串工具类
 *
 * @author wangpeng
 */
public class CaseUtil {

    public static final String EMPTY = "";

    /**
     * 驼峰到下划线
     */
    public static String camel2Underline(String str) {
        return camelToFixedString(str, "_");
    }

    public static String underline2Camel(String str) {
        return underline2Camel(str, true);
    }

    /**
     * 下划线到驼峰
     * isCapitalize 是否首字母大写
     */
    public static String underline2Camel(String str, boolean isCapitalize) {
        String[] sections = str.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sections.length; i++) {
            String s = sections[i];
            if (!isCapitalize && i == 0) {
                sb.append(s);
            } else {
                sb.append(capitalize(s));
            }
        }
        return sb.toString();
    }

    /**
     * 字符串首字母大写
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) return EMPTY;
        return Character.toTitleCase(str.charAt(0)) + str.substring(1);
    }


    public static String camelToFixedString(String str, String fixed) {
        if (isEmpty(str)) return EMPTY;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i != 0) {
                    sb.append(fixed);
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(camel2Underline("BlogType"));
        System.out.println(camel2Underline("Admin"));
        System.out.println(underline2Camel("blog_type"));
        System.out.println(underline2Camel("admin"));
    }
}
