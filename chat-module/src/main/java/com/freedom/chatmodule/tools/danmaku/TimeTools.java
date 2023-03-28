package com.freedom.chatmodule.tools.danmaku;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @Author kede·W  on  2023/3/26
 */
public class TimeTools {
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将时间戳转换为日期时间字符串
     *
     * @param timestamp 时间戳，单位为毫秒
     * @return 日期时间字符串
     */
    public static String formatTimestamp(long timestamp) {
        return formatTimestamp(timestamp, DEFAULT_PATTERN);
    }

    /**
     * 将时间戳转换为指定格式的日期时间字符串
     *
     * @param timestamp 时间戳，单位为毫秒
     * @param pattern   格式化模式，例如：yyyy-MM-dd HH:mm:ss
     * @return 日期时间字符串
     */
    public static String formatTimestamp(long timestamp, String pattern) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(localDateTime);
    }

    /**
     * 将日期时间字符串解析为时间戳
     *
     * @param dateTimeString 日期时间字符串
     * @return 时间戳，单位为毫秒
     */
    public static long parseDateTimeString(String dateTimeString) {
        return parseDateTimeString(dateTimeString, DEFAULT_PATTERN);
    }

    /**
     * 将指定格式的日期时间字符串解析为时间戳
     *
     * @param dateTimeString 日期时间字符串
     * @param pattern        格式化模式，例如：yyyy-MM-dd HH:mm:ss
     * @return 时间戳，单位为毫秒
     */
    public static long parseDateTimeString(String dateTimeString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 获取当前时间戳
     *
     * @return 当前时间戳，单位为毫秒
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间戳 对应的字符串时间
     *
     * @return 当前时间戳，单位为毫秒
     */
    public static String getCurrentTime() {
        return formatTimestamp(System.currentTimeMillis());
    }
}
