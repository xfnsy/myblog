package com.banxian.myblog.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

/**
 * 日期时间工具类
 *
 * @author wangpeng
 * @datetime 2020/12/11 8:37
 */
public class DateUtil {

    private static final HashMap<String, DateTimeFormatter> DTF_MAP;

    private static final DateTimeFormatter DEFAULT_DTF;
    private static final DateTimeFormatter DEFAULT_DF;

    private DateUtil() {
    }

    static {
        // 初始化dateTimeFormatterMap集合
        DTF_MAP = new HashMap<>();
        for (Pattern pattern : Pattern.values()) {
            DTF_MAP.put(pattern.name, DateTimeFormatter.ofPattern(pattern.name));
        }
        // 初始化默认日期和时间格式
        DEFAULT_DTF = DTF_MAP.get(Pattern.P1.name);
        DEFAULT_DF = DTF_MAP.get(Pattern.P2.name);
    }

    public static DateTimeFormatter getByPattern(String pattern) {
        if (DTF_MAP.containsKey(pattern)) {
            return DTF_MAP.get(pattern);
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        DTF_MAP.put(pattern, dtf);
        return dtf;
    }


    /*************************************************  Date和LocalDateTime等的互转 *****************************************/

    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate date2LocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date localDate2Date(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    /************************************************* 日期格式化为字符串 *****************************************/

    public static String format(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(getByPattern(pattern));
    }

    public static String format(LocalDateTime localDateTime) {
        return localDateTime.format(DEFAULT_DTF);
    }

    public static String format(LocalDate localDate, String pattern) {
        return localDate.format(getByPattern(pattern));
    }

    public static String format(LocalDate localDate) {
        return localDate.format(DEFAULT_DF);
    }

    public static String format(Date date, String pattern) {
        return format(date2LocalDateTime(date), pattern);
    }

    public static String format(Date date) {
        return format(date2LocalDateTime(date));
    }

    public static String formate2Day(LocalDateTime localDateTime) {
        return localDateTime.format(DEFAULT_DF);
    }

    public static String formate2Day(Date date) {
        return formate2Day(date2LocalDateTime(date));
    }


    /************************************************* 字符串解析为日期 *****************************************/

    public static LocalDateTime parseLocalDateTime(String dateTime, String pattern) {
        return LocalDateTime.parse(dateTime, getByPattern(pattern));
    }

    public static LocalDateTime parseLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DEFAULT_DTF);
    }

    public static LocalDate parseLocalDate(String dateTime, String pattern) {
        return LocalDate.parse(dateTime, getByPattern(pattern));
    }

    public static LocalDate parseLocalDate(String dateTime) {
        return LocalDate.parse(dateTime, DEFAULT_DF);
    }

    public static Date parseDate(String dateTime, String pattern) {
        dateTime = dateTime.trim();
        if (dateTime.length() <= 10) {
            return localDate2Date(parseLocalDate(dateTime, pattern));
        }
        return localDateTime2Date(parseLocalDateTime(dateTime, pattern));
    }


    public static Date parseDate(String dateTime) {
        dateTime = dateTime.trim();
        if (dateTime.length() <= 10) {
            return localDate2Date(parseLocalDate(dateTime));
        }
        return localDateTime2Date(parseLocalDateTime(dateTime));
    }


    /************************************************* Date日期年，日月等的加减 *****************************************/
    public static Date plusYears(Date date, long years) {
        return localDateTime2Date(date2LocalDateTime(date).plusYears(years));
    }

    public static Date plusMonths(Date date, long months) {
        return localDateTime2Date(date2LocalDateTime(date).plusMonths(months));
    }

    public static Date plusDays(Date date, long days) {
        return localDateTime2Date(date2LocalDateTime(date).plusDays(days));
    }

    public static Date plusHours(Date date, long hours) {
        return localDateTime2Date(date2LocalDateTime(date).plusHours(hours));
    }

    public static Date plusMinutes(Date date, long minutes) {
        return localDateTime2Date(date2LocalDateTime(date).plusMinutes(minutes));
    }

    public static Date plusSeconds(Date date, long seconds) {
        return localDateTime2Date(date2LocalDateTime(date).plusSeconds(seconds));
    }

    public static Date minusYears(Date date, long years) {
        return localDateTime2Date(date2LocalDateTime(date).minusYears(years));
    }

    public static Date minusMonths(Date date, long months) {
        return localDateTime2Date(date2LocalDateTime(date).minusMonths(months));
    }

    public static Date minusDays(Date date, long days) {
        return localDateTime2Date(date2LocalDateTime(date).minusDays(days));
    }

    public static Date minusHours(Date date, long hours) {
        return localDateTime2Date(date2LocalDateTime(date).minusHours(hours));
    }

    public static Date minusMinutes(Date date, long minutes) {
        return localDateTime2Date(date2LocalDateTime(date).minusMinutes(minutes));
    }

    public static Date minusSeconds(Date date, long seconds) {
        return localDateTime2Date(date2LocalDateTime(date).minusSeconds(seconds));
    }

    /**
     * 日期模式枚举
     */
    public enum Pattern {
        P1("yyyy-MM-dd HH:mm:ss"),
        P2("yyyy-MM-dd"),
        P3("yyyy/MM/dd"),
        P4("yyyy MM dd"),
        P5("yyyyMMdd"),
        P6("yyyy.MM.dd");
        public final String name;

        Pattern(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
//        LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(0);
//        System.out.println(localDateTime.now().compareTo(localDateTime));
//        System.out.println(localDateTime2Date(localDateTime));
//        System.out.println(localDate2Date(LocalDate.now()));
//        System.out.println(date2LocalDate(new Date()));
//        System.out.println(date2LocalDateTime(new Date()));

//        System.out.println(format(new Date()));
//        System.out.println(formate2Day(new Date()));

//        System.out.println(parseDate("2020-12-11 13:33:22"));
//        System.out.println(parseDate("2020-12-11"));

//        System.out.println(plusHours(new Date(), 10l));
//        System.out.println(plusMinutes(new Date(), 10l));
//        System.out.println(minusDays(new Date(), 10l));
//        System.out.println(plusMonths(new Date(), 10l));
//       String a="199210181";
//        if(a.length()>8){
//            System.out.println(a.substring(0,8));
//        }

    }
}
