package com.example.sdldemo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/*
 *@ProjectName: AndroidCommon
 *@FileName: DateTimeUtil.java
 *@CreateTime: 2014-2-8 下午3:49:26
 *@Author: Sammie.Zhang
 *@Description:
 */
public class DateTimeUtil {
    /**
     * Date对象转String
     *
     * @param date         Date对象
     * @param formatString 如:yyyy-MM-dd HH:mm:ss,sss
     * @return
     */
    public static String dateToString(Date date, String formatString) {
        String retStr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatString);
            retStr = sdf.format(date);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return retStr;
    }

    /**
     * 获取当前时间字符串
     *
     * @param formatString 如:yyyy-MM-dd HH:mm:ss,sss
     * @return
     */
    public static String getCurrentDateTimeString(String formatString) {
        SimpleDateFormat timeFormater = new SimpleDateFormat(formatString);// 日志内容的时间格式
        return timeFormater.format(new Date().getTime());
    }

    public static String getCurrentDateTimeString(String formatString, Locale locale) {
        SimpleDateFormat timeFormater = new SimpleDateFormat(formatString, locale);// 日志内容的时间格式
        return timeFormater.format(new Date().getTime());
    }

    /**
     * 获取当前时间的Date对象
     *
     * @return
     */
    public static Date getCurrentDateObject() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 转换已有格式的日期字符的格式
     *
     * @param dateString 如:2014-01-02 10:10:10,100
     * @param fromFormatStr  如:yyyy/MM/dd HH:mm:ss,sss
     *  @param toFormatStr  如:yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String formatDateString(String dateString, String fromFormatStr, String toFormatStr) {
        String dateTime = "";
        try {
            DateFormat format = new SimpleDateFormat(fromFormatStr);
            Date date = format.parse(dateString);
            dateTime = dateToString(date, toFormatStr);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dateTime;
    }

    public static Map<String, Long> getTimeSpace(long startTime, long endTime) {
        Map<String, Long> map = new HashMap<String, Long>();
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff = endTime - startTime;
        long day = diff / nd;// 计算差多少天
        long hour = diff % nd / nh;// 计算差多少小时
        long min = diff % nd % nh / nm;// 计算差多少分钟
        long sec = diff % nd % nh % nm / ns;// 计算差多少秒
        map.put("Day", day);
        map.put("Hour", hour);
        map.put("Min", min);
        map.put("Sec", sec);
        return map;
    }


    public static String getTimes(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime_Y_M_D() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static String getCurrentTime_Y_M_D_H_M() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date());
    }

    //精确到秒钟
    public static String getCurrentTime_Y_M_D_H_M_S() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }


    public static String getOffsetMonth(Date protoDate, int monthOffset, String formatString) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(protoDate);
        cal.add(Calendar.MONTH, monthOffset); // 正确写法
        return dateToString(cal.getTime(), formatString);
    }

    // 把字符串转为日期
    public static Date strToDate(String strDate, String formatStr) throws Exception {
        try {
            DateFormat df = new SimpleDateFormat(formatStr);
            return df.parse(strDate);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    public static Calendar CALENDAR = Calendar.getInstance();

    private DateTimeUtil() {
    }

    ;


    /**
     * 格式化时间
     *
     * @param timeMillis
     * @return yyyy-MM-dd
     */
    public static String formatDataToYMD(long timeMillis) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        CALENDAR.setTimeInMillis(timeMillis);
        return formatter.format(CALENDAR.getTime());
    }

    /**
     * 将Long值的时间转换成标准日期格式
     *
     * @param millis
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String covertMillisToDateStr(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (millis > 0) {
            Date dt = new Date(millis);
            return sdf.format(dt);
        } else {
            return "1900-01-01 00:00:00";
        }
    }

    //

    /**
     * 将结束时间戳距离开始时间戳的毫秒值转化成小时、分钟、秒数
     *
     * @param timeMs 结束时间戳距离开始时间戳的毫秒值
     * @return 00小时:00分:00秒
     */
    @SuppressWarnings("resource")
    public static String covertDiffMillisToTimeStr(long timeMs, String[] timeStr) {
        long totalSeconds = timeMs / 1000;// 获取文件有多少秒
        StringBuilder mFormatBuilder = new StringBuilder();

        Formatter formatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int seconds = (int) totalSeconds % 60;
        int minutes = (int) (totalSeconds / 60) % 60;
        int hours = (int) totalSeconds / 3600;
        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return formatter.format("%02d" + timeStr[0] + ":%02d" + timeStr[1] + ":%02d" + timeStr[2], hours, minutes, seconds).toString();// 格式化字符串
        } else if (minutes > 0) {
            return formatter.format("%02d" + timeStr[1] + ":%02d" + timeStr[2], minutes, seconds).toString();
        } else {
            return formatter.format("%02d" + timeStr[2], seconds).toString();
        }
    }

    /**
     * 将字符串dateStr(2013-01-31 08:59:49.42)转换成long型
     *
     * @param dateStr
     * @return long型，精确到毫秒
     */
    public static long getDayTimeToLongToSecondByStr(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt2 = null;
        try {
            dt2 = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt2.getTime();
    }

    /**
     * 将字符串dateStr(2013-01-31 08:59:49.42)转换成long型
     *
     * @param dateStr
     * @return long型，精确到毫秒
     */
    public static long getDayTimeToLongToSecondByStrE(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.US);
        Date dt2 = null;
        try {
            dt2 = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt2.getTime();
    }

    /**
     * 将字符串dateStr(2013-01-31 08:59:49.42)转换成long型
     *
     * @param dateStr
     * @param type    0表示 执行+0.5 1表示-0.5
     * @return long型，精确到毫秒
     */
    public static int getDayTimeToLongToSecondByStr2(String dateStr, int type) {

        String[] strs = dateStr.split(":");
        int time;
        int hour = Integer.valueOf(strs[0]);
        int minut = Integer.valueOf(strs[1]);
        if (minut > 0) {
            time = type == 0 ? ((int) ((hour + 0.5) * 3600)) : (int) ((-hour - 0.5) * 3600);
        } else {
            time = type == 0 ? hour * 3600 : -(hour * 3600);
        }
        return time;
    }


    public static long ComparativeTime(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Long diff = null;
        try {
            Date d1 = df.parse(startTime);
            Date d2 = df.parse(endTime);
            diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
        } catch (Exception e) {

        }
        return diff;
    }

    public static long comparativeTime(String startTime, String endTime, String formatStr) {
        DateFormat df = new SimpleDateFormat(formatStr, Locale.US);
        Long diff = null;
        try {
            Date d1 = df.parse(startTime);
            Date d2 = df.parse(endTime);
            diff = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别
        } catch (Exception e) {

        }
        return diff;
    }

    public static long comparativeTime(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Long diff = null;
        try {
            Date d1 = df.parse(startTime);
            Date d2 = df.parse(endTime);
            diff = (d2.getTime() - d1.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }


    public static String getUTCTimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtTime = sdf.format(new Date());
        return gmtTime;
    }

    public static String getUTCTimeStr(int second) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
        Date now = new Date();
        Date afterDate = new Date(now.getTime() + second * 1000);
        String gmtTime = sdf.format(afterDate);
        return gmtTime;
    }

    private static int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
