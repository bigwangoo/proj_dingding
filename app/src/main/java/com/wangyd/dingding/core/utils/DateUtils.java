package com.wangyd.dingding.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author wangyd
 * @date 2018/6/24
 * @description 日期相关工具
 */
public class DateUtils {

    /**
     * 获取当前日期 yyyy-MM-dd
     */
    public static String getCurrentDate() {
        return formatDate(Calendar.getInstance(), "yyyy-MM-dd");
    }

    /**
     * 字符串日期 格式化
     *
     * @param time       2018-01-01
     * @param timeFormat timeFormat
     */
    public static String formatYMD(String time, String timeFormat) {
        try {
            DateFormat df = new SimpleDateFormat(timeFormat, Locale.getDefault());
            Date date = df.parse(time);
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Calendar日期 格式化
     *
     * @param calendar   calendar
     * @param timeFormat timeFormat
     */
    public static String formatDate(Calendar calendar, String timeFormat) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat(timeFormat, Locale.getDefault());
            return sf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * 由出生日期获得年龄
     *
     * @param birthday yyyy-MM-dd
     */
    public static int getAgeByBirthday(String birthday) {
        int age = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date birthDay = null;
            birthDay = sdf.parse(birthday);
            Calendar cal = Calendar.getInstance();
            if (cal.before(birthDay)) {
                return age;
            }

            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH);
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
            cal.setTime(birthDay);

            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH);
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

            age = yearNow - yearBirth;

            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) {
                        age--;
                    }
                } else {
                    age--;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return age;
    }

}
