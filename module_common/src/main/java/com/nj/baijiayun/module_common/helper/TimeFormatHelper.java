package com.nj.baijiayun.module_common.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author chengang
 * @date 2019-06-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class TimeFormatHelper {

    private final static int MIN_SEC = 60;
    private final static int HOUR_SEC = 60 * MIN_SEC;
    private final static int DAY_SEC = 24 * HOUR_SEC;


    public static String getNoComplementMonthAndDay(long sec) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("M月d日");
        return df.format(new Date(sec * 1000));
    }
    public static String getMonthAndDay(long sec) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("MM月dd日");
        return df.format(new Date(sec * 1000));
    }


    public static String getYearMonthAndDayFormatChina(long sec) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        return df.format(new Date(sec * 1000));
    }

    public static String getYearMonthAndDayByPoint(long sec) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        return df.format(new Date(sec * 1000));
    }

    public static String getHourAndMinute(long sec) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(new Date(sec * 1000));
    }


    public static String getYearMonthDayRange(long start, long end) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String startStr = df.format(new Date(start * 1000));
        String endStr = df.format(new Date(end * 1000));
        if (startStr.equals(endStr)) {
            return startStr;
        }
        return startStr + "~" + endStr;

    }

    public static String getYearMonthDayHourMinRangeByLine(long start, long end) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String startStr = df.format(new Date(start * 1000));
        String endStr = df.format(new Date(end * 1000));
        if (startStr.equals(endStr)) {
            return startStr;
        }
        return startStr + "~" + endStr;

    }

    public static String getYearMonthDayHourMinRangeByPoint(long start, long end) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        String startStr = df.format(new Date(start * 1000));
        String endStr = df.format(new Date(end * 1000));
        if (startStr.equals(endStr)) {
            return startStr;
        }
        return startStr + "-" + endStr;

    }


    public static String getMonthDayRange(long start, long end) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd HH:mm:ss");
        String startStr = df.format(new Date(start * 1000));
        String endStr = df.format(new Date(end * 1000));
        return startStr + "~" + endStr;
    }

    public static String getMonthDayFull(long start) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd HH:mm:ss");
        return df.format(new Date(start * 1000));
    }

    public static String getFullDate(long sec) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date(sec * 1000));
    }

    public static String getFullDateSplitByPoint(long sec) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return df.format(new Date(sec * 1000));
    }


    public static String getYearMonthDayHourMinSplitByPoint(long sec) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return df.format(new Date(sec * 1000));
    }

    public static String getFullDateSplitByLine(long sec) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return df.format(new Date(sec * 1000));
    }

    public static String getFullDateSplitByCross(long sec) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date(sec));
    }

    public static String formatSeconds(long seconds) {
        String standardTime;
        if (seconds <= 0) {
            standardTime = "00:00";
        } else if (seconds < 60) {
            standardTime = String.format(Locale.getDefault(), "00:%02d", seconds % 60);
        } else if (seconds < 3600) {
            standardTime = String.format(Locale.getDefault(), "%02d:%02d", seconds / 60, seconds % 60);
        } else {
            standardTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", seconds / 3600, seconds % 3600 / 60, seconds % 60);
        }
        return standardTime;
    }


    public static String getDayHourMinToCnBySec(long sec) {
        StringBuilder result = new StringBuilder("");
        int day = (int) (sec / DAY_SEC);
        int hour = (int) ((sec - day * DAY_SEC) / HOUR_SEC);
        int min = (int) ((sec - day * DAY_SEC - hour * HOUR_SEC) / 60);

        if (day > 0) {
            result.append(day).append("天");
        }
        if (hour > 0) {
            result.append(hour).append("小时");
        }
        if (min > 0) {
            result.append(min).append("分");
        }

        return result.toString();


    }


    public static String getMonth(long second) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        return df.format(new Date(second*1000));
    }

    public static String getDate(long second) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date(second*1000));
    }

    public static String getDateByFormat(long second,String format) {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date(second*1000));
    }

    public static int getTimeInSecond(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();//日历类的实例化
        calendar.set(year, month - 1, day);//设置日历时间，月份必须减一
        return (int) (calendar.getTimeInMillis() / 1000);
    }


}
