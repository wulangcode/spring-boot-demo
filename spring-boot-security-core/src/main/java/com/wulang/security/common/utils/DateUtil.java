/**
 * DateUtil工具类API速查表:
 * 1.得到当前时间 getCurrentDate()
 * 2.得到当前年份字符串 getCurrentYear()
 * 3.得到当前月份字符串 getCurrentMonth()
 * 4.得到当天字符串 getCurrentDay()
 * 5.得到当前星期字符串(星期几) getCurrentWeek()
 * 6.Date转化为String formatDate()
 * 7.String转化为Date parseDate()
 * 8.比较时间大小 compareToDate()
 * 9.得到给定时间的给定天数后的日期 getAppointDate()
 * 10.获取两个日期之间的天数 getDistanceOfTwoDate()
 * 11.获取过去的天数 pastDays()
 * 12.获取过去的小时 pastHour()
 * 13.获取过去的分钟  pastMinutes()
 * 14.得到本周的第一天  getFirstDayOfWeek()
 * 15.得到当月第一天 getFirstDayOfMonth()
 * 16.得到下月的第一天 getFirstDayOfNextMonth()
 * 17.根据生日获取年龄 getAgeByBirthDate()
 */
package com.wulang.security.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期时间工具类
 * Created by wangfan on 2017-6-10 上午10:10
 */
public class DateUtil {
    /**
     * 得到当前时间(yyyy-MM-dd HH:mm:ss)
     *
     * @return
     */
    public static String getCurrentDate() {
        return formatDate(new Date());
    }

    /**
     * Date转化为String(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Date转化为String
     *
     * @param date
     * @param formate 格式
     * @return
     */
    public static String formatDate(Date date, String formate) {
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        return sdf.format(date);
    }

    /**
     * 得到当前时间
     *
     * @param formate 格式
     * @return
     */
    public static String getCurrentDate(String formate) {
        return formatDate(new Date(), formate);
    }

    /**
     * 得到当前年份字符串
     */
    public static String getCurrentYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串
     */
    public static String getCurrentMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串
     */
    public static String getCurrentDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串(星期几)
     */
    public static String getCurrentWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * String转化为Date
     *
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
        return parseDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * String转化为Date
     *
     * @param date
     * @param formate
     * @return
     */
    public static Date parseDate(String date, String formate) {
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 比较时间大小
     *
     * @param first
     * @param second
     * @return 返回0 first等于second, 返回-1 first小于second,, 返回1 first大于second
     */
    public static int compareToDate(String first, String second, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        try {
            cal1.setTime(df.parse(first));
            cal2.setTime(df.parse(second));
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("比较时间错误");
        }
        int result = cal1.compareTo(cal2);
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        }
        return 0;
    }

    /**
     * 比较时间大小
     *
     * @param first
     * @param second
     * @return 返回0 first等于second, 返回-1 first小于second,, 返回1 first大于second
     */
    public static int compareToDate(Date first, Date second) {
        int result = first.compareTo(second);
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        }
        return 0;
    }

    /**
     * 获取当前时间，包含时分秒now
     *
     * @return
     */
    public static Date getNowDateFull() {
        String date = DateUtil.formatDate(new Date(), "yyyy/MM/dd HH:mm:ss");
        Date now = DateUtil.parseDate(date, "yyyy/MM/dd HH:mm:ss");
        return now;
    }

    /**
     * 获取当前时间，不包含时分秒now
     *
     * @return
     */
    public static Date getNowDateWithNoTime() {
        String date = DateUtil.formatDate(new Date(), "yyyy/MM/dd");
        Date now = DateUtil.parseDate(date, "yyyy/MM/dd");
        return now;
    }

    /**
     * 得到给定时间的给定天数后的日期
     *
     * @return
     */
    public static Date getAppointDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 得到本周的第一天
     *
     * @return
     */
    public static Date getFirstDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        // 如果是周日要减一天
        if (1 == cal.get(Calendar.DAY_OF_WEEK)) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * 得到给定日期的周一
     *
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 如果是周日要减一天
        if (1 == cal.get(Calendar.DAY_OF_WEEK)) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * 得到给定日期所在的一周
     *
     * @return
     */
    public static List<String> getWeekDays(Date date) {
        return getWeekDays(date, "yyyy-MM-dd");
    }

    /**
     * 得到给定日期所在的一周
     *
     * @return
     */
    public static List<String> getWeekDays(Date date, String format) {
        List<String> weekDays = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 如果是周日要减一天
        if (1 == cal.get(Calendar.DAY_OF_WEEK)) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        weekDays.add(formatDate(cal.getTime(), format));
        for (int i = 1; i < 7; i++) {
            cal.add(Calendar.DATE, 1);
            weekDays.add(formatDate(cal.getTime(), format));
        }
        return weekDays;
    }

    /**
     * 得到当月第一天
     *
     * @return
     */
    public static Date getFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return cal.getTime();
    }

    /**
     * 得到下月的第一天
     *
     * @return
     */
    public static Date getFirstDayOfNextMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, +1);
        int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return cal.getTime();
    }

    /**
     * 根据生日获取年龄
     *
     * @param birtnDay
     * @return
     */
    public static int getAgeByBirthDate(Date birtnDay) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birtnDay)) {
            return 0;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birtnDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    public static long getDistanceOfM(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60);
    }

    public static long getDistanceOfS(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000);
    }

    /**
     * 判断字符串是否是合法日期格式 yyyy-MM-dd HH:mm
     *
     * @param str
     * @return
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    public static List<Date> getBetweenDates(String start, String end) {
        Date start_date = parseDate(start, "yyyy-MM-dd HH:mm:ss");
        Date end_date = parseDate(end, "yyyy-MM-dd HH:mm:ss");
        return getBetweenDates(start_date, end_date);
    }

    public static List<Date> getBetweenDates(Date start_date, Date end_date) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start_date);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end_date);
        while (tempStart.before(tempEnd) || tempStart.equals(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        // Collections.reverse(result);
        return result;
    }

    public static String getWeek(Date date) {
        String[] weeks = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }
}
