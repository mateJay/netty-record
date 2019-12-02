package com.matejay.netty.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    public static final String DEFAULT_TIME_FORMAT ="yyyyMMdd-HHmmss-SSS";
    public static final String FORMAT_1 = "yyyy-MM-dd";
    public static final String FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_3 = "yyyyMMdd";
    public static final String FORMAT_4 = "yyyyMMdd HH:mm:ss";
    public static final String FORMAT_5 = "MM月dd日HH时mm分ss秒";
    public static final String FORMAT_6 = "MM月dd日HH:mm";
    public static final String FORMAT_7 = "HH:mm";
    public static final String FORMAT_8 = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_9 = "yyyyMMdd-HHmmss";

    public static void main(String[] args) {
        System.out.println(TimeUtil.format(new Date(), DEFAULT_TIME_FORMAT));
        System.out.println(TimeUtil.getZeroTimestamp(System.currentTimeMillis()));
        System.out.println(TimeUtil.getZeroDate(System.currentTimeMillis()));
        System.out.println(TimeUtil.format(System.currentTimeMillis()));
        System.out.println(formatSrtTimeToSec("01:01:01,480"));
        System.out.println(TimeUtil.get235959OfDate(System.currentTimeMillis()));
        System.out.println(addMonth(new Date(), 2));
        System.out.println(addYear(new Date(), 2));
    }

    public static Date addYear(Date date, int year) {
        return add(date, year, Calendar.YEAR);
    }

    public static Date addMonth(Date date, int month) {
        return add(date, month, Calendar.MONTH);
    }

    public static Date addDay(Date date, int day) {
        return add(date, day, Calendar.DAY_OF_MONTH);
    }

    public static Date addHours(Date date, int hours) {
        return add(date, hours, Calendar.HOUR_OF_DAY);
    }

    public static Date addMinutes(Date date, int mintutes) {
        return add(date, mintutes, Calendar.MINUTE);
    }

    public static Date addSeconds(Date date, int seconds) {
        return add(date, seconds, Calendar.SECOND);
    }

    private static Date add(Date date, int val, int type) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, val);
        return calendar.getTime();
    }

    public  static Date parse(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date  d= sdf.parse(date);
            return d;
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getZeroDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        return getZeroDate(date.getTime());
    }

    /**
     * 获取timestamp的零点的unix时间戳
     */
    public static long getZeroTimestamp(long timestamp) {
        return getZeroDate(timestamp).getTime();
    }

    /**
     * 获取date的零点date
     */
    public static Date getZeroDate(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取date的23点59分59秒
     */
    public static Date get235959OfDate(Long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 将unix时间戳格式化为yyyyMMddHHmmssSSS
     */
    public static String format(long timestamp){
        return format(new Date(timestamp));
    }

    public static String format(long timestamp, String format){
        return format(new Date(timestamp), format);
    }

    public static String format(Date date) {
        return format(date, DEFAULT_TIME_FORMAT);
    }

    public static String format(Date date, String string) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(string);
        return sdf.format(date);
    }


    public static String formatSrtTime(int time) {
        int hour = time / (1000 * 60 * 60);
        String hourStr = "" + hour;
        if (hour < 10) {
            hourStr = "0" + hour;
        }
        int minute = (time / (1000 * 60)) % 60;
        String minuteStr = "" + minute;
        if (minute < 10) {
            minuteStr = "0" + minute;
        }
        int second = (time / 1000) % 60;
        String secondStr = "" + second;
        if (second < 10) {
            secondStr = "0" + second;
        }
        int millisecond = time % 1000;
        String millisecondStr = "" + millisecond;
        if (millisecond < 10) {
            millisecondStr = "00" + millisecond;
        } else if (millisecond < 100) {
            millisecondStr = "0" + millisecond;
        }
        String strTime = hourStr + ":" + minuteStr + ":" + secondStr + "," + millisecondStr;
        return strTime;
    }

    /**
     * 将srtTime转换为浮点类型的秒数
     * srtTime格式为：HH:mm:ss,SSS
     * @param srtTime
     * @return
     */
    public static float formatSrtTimeToSec(String srtTime) {
        String hhmmss = srtTime.split(",")[0];
        String SSS = srtTime.split(",")[1];
        int hour = Integer.parseInt(hhmmss.split(":")[0]);
        int min = Integer.parseInt(hhmmss.split(":")[1]);
        int sec = Integer.parseInt(hhmmss.split(":")[2]);
        return (hour * 60 * 60 + min * 60 + sec) + Float.parseFloat(SSS) / 1000;
    }

    /**
     * 将srtTime转换为毫秒
     * srtTime格式为：HH:mm:ss,SSS
     * @param srtTime
     * @return
     */
    public static int formatSrtTimeToMilliSec(String srtTime) {
        String hhmmss = srtTime.split(",")[0];
        String SSS = srtTime.split(",")[1];
        int hour = Integer.parseInt(hhmmss.split(":")[0]);
        int min = Integer.parseInt(hhmmss.split(":")[1]);
        int sec = Integer.parseInt(hhmmss.split(":")[2]);
        return (hour * 60 * 60 + min * 60 + sec) * 1000 + Integer.parseInt(SSS);
    }

    /**
     * 将毫秒转换为assTime格式
     * assTime格式为：H:mm:ss,SS
     * @param milliSec
     * @return
     */
    public static String assTimeFormat(int milliSec) {
        long milsec = milliSec % 1000;
        long sec = (milliSec / 1000) % 60;
        long min = (milliSec / 1000 / 60) % 60;
        long hour = (milliSec / 1000 / 60 / 60) % 60;
        return String.format(Locale.getDefault(), "%1d:%02d:%02d.%02d", (int) hour, min, sec, milsec / 10);
    }

    /**
     * 将srtTime格式转换为assTime格式
     * srtTime格式为：HH:mm:ss,SSS
     * assTime格式为：H:mm:ss,SS
     * @param srtTime
     * @return
     */
    public static String getAssTimeBySrtTime(String srtTime) {
        int milliSec = formatSrtTimeToMilliSec(srtTime);
        return assTimeFormat(milliSec);
    }


}
