package com.tianxiabuyi.txutils.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author lenovos20-2
 * @description 时间工具类
 */
public class TimeUtils {

    public final static int YEAR = 1;       // 时间加减操作 年
    public final static int MONTH = 2;      // 时间加减操作 月
    public final static int DAY = 3;        // 时间加减操作 日

    //////////////////////////////// 时间基本操作 /////////////////////////////////////////////////

    /**
     * 获取当前时间
     */
    public static String getCurrentTime() {
        return formatDate(Calendar.getInstance(), "yyyy-MM-dd HH:mm");
    }

    /**
     * 获取当前日期
     */
    public static String getCurrentDay() {
        return formatDate(Calendar.getInstance(), "yyyy-MM-dd");
    }

    /**
     * 将日期以指定形式输出
     *
     * @param date       日期
     * @param timeFormat 格式
     */
    public static String formatDate(Date date, String timeFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(timeFormat, Locale.getDefault());
        return sf.format(date);
    }

    /**
     * 将日期以指定形式输出
     *
     * @param longTime   日期
     * @param timeFormat 格式
     */
    public static String formatDate(long longTime, String timeFormat) {
        if (longTime == 0) {
            return "";
        }

        SimpleDateFormat sf = new SimpleDateFormat(timeFormat, Locale.getDefault());
        Date date = new Date();
        date.setTime(longTime);
        return sf.format(date);
    }

    /**
     * 将日期以指定形式输出
     *
     * @param calendar   日期
     * @param timeFormat 格式
     */
    public static String formatDate(Calendar calendar, String timeFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(timeFormat, Locale.getDefault());
        return sf.format(calendar.getTime());
    }

    /**
     * 时间转为 Calender
     *
     * @param time       2015-10-10
     * @param timeFormat yyyy-MM-dd
     */
    public static Calendar getCalendar(String time, String timeFormat) {
        Date date = getDate(time, timeFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 时间转为 Date
     *
     * @param time       2015-10-10
     * @param timeFormat yyyy-MM-dd
     */
    public static Date getDate(String time, String timeFormat) {
        DateFormat df = new SimpleDateFormat(timeFormat, Locale.getDefault());
        try {
            return df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期时间，加减操作       注：不支持同时加减年、月、日
     *
     * @param calendar Calendar
     * @param type     TimeUtils.YEAR
     * @param values   5 or -5
     */
    public static String getTime(Calendar calendar, int type, int values) {
        if (type == YEAR) {
            calendar.add(Calendar.YEAR, values);
        } else if (type == MONTH) {
            calendar.add(Calendar.MONTH, values);
        } else if (type == DAY) {
            calendar.add(Calendar.DATE, values);
        }
        return formatDate(calendar, "yyyy-MM-dd");
    }


    //////////////////////////////// 时间友好化处理 ///////////////////////////////////////////////

    /**
     * 友好化格式时间
     *
     * @param time 2015-12-01
     */
    public static String getPrefix(String time) {
        Date date = getDate(time, "yyyy-MM-dd HH:mm:ss");
        if (date == null) {
            return "";
        }

        // 与现在时间差
        long currentSeconds = System.currentTimeMillis();
        long timeGap = currentSeconds - date.getTime();
        // 友好化提示
        String timeStr;
        if (timeGap > 3 * 24 * 60 * 60 * 1000) {
            timeStr = formatDate(date, "yy-MM-dd");
        } else if (timeGap > 2 * 24 * 60 * 60 * 1000) {
            timeStr = "前天 " + formatDate(date, "HH:mm");
        } else if (timeGap > 24 * 60 * 60 * 1000) {
            timeStr = "昨天 " + formatDate(date, "HH:mm");
        } else if (timeGap > 60 * 60 * 1000) {
            timeStr = timeGap / 3600 / 1000 + "小时前";
        } else if (timeGap > 60 * 1000) {
            timeStr = timeGap / 60 / 1000 + "分钟前";
        } else {
            timeStr = "刚刚";
        }
        return timeStr;
    }

    /**
     * 获取某年某日是周几
     *
     * @param time 2015-12-01
     */
    public static String getDayOfWeek(String time) {
        return getDayOfWeek(getCalendar(time, "yyyy-MM-dd"));
    }

    /**
     * 获取某年某日是周几
     */
    public static String getDayOfWeek(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String week = "";
        switch (dayOfWeek) {
            case 1:
                week = "周日";
                break;
            case 2:
                week = "周一";
                break;
            case 3:
                week = "周二";
                break;
            case 4:
                week = "周三";
                break;
            case 5:
                week = "周四";
                break;
            case 6:
                week = "周五";
                break;
            case 7:
                week = "周六";
                break;
            default:
                break;
        }
        return week;
    }


    //////////////////////////////// 时间比较 /////////////////////////////////////////////////////

    /**
     * 时间比较
     *
     * @param time              2015-10-10
     * @param time2             2015-10-10
     * @param isReturnTrueEqual 相等的时候是否返回true
     * @return true time > time2
     */
    public static boolean compareTime(String time, String time2, boolean isReturnTrueEqual) {
        Date date = getDate(time, "yyyy-MM-dd");
        Date date2 = getDate(time2, "yyyy-MM-dd");
        if (date == null && date2 == null) {
            return true;
        }
        if (date == null) {
            return false;
        }
        if (date2 == null) {
            return true;
        }

        int result = date.compareTo(date2);
        if (isReturnTrueEqual) {
            return result >= 0;
        } else {
            return result > 0;
        }
    }

}
