package com.nj.baijiayun.module_public.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author chengang
 * @date 2019-07-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class PublicFormatHelper {

    private static final String YEAR_PATTER = "yyyy年";
    private static final String DATE_PATTER = "MM月dd日";
    private static final String YEAR_PATTER_SHORT = "yyyy/";
    private static final String DATE_PATTER_SHORT = "MM/dd";
    private static final String TIME_PATTER = "HH:mm";
    private static final String FULL_PATTER = YEAR_PATTER + DATE_PATTER + TIME_PATTER;
    private static final String FULL_PATTER_SHORT = YEAR_PATTER_SHORT + DATE_PATTER_SHORT + TIME_PATTER;
    private static final String EXCLUDE_YEAR_PATTER = DATE_PATTER + TIME_PATTER;
    private static final String YEAR_DATE_PATTER = YEAR_PATTER + DATE_PATTER;
    private static final String YEAR_DATE_PATTER_SHORT = YEAR_PATTER_SHORT + DATE_PATTER_SHORT;


    public static String getPrice(String price) {
        return price;
    }

    @Deprecated
    public static String getTimeByCourseType(int type, long startPlay, long endPlay) {
        return getTimeRange(startPlay, endPlay, true);
    }

    public static String getTimeByTimeRange(long startPlay, long endPlay) {
        return getTimeRange(startPlay, endPlay, true);
    }

    public static String getTimeByTimeRange(long startPlay, long endPlay, boolean isShowAllWhenDiffYear, boolean isResultContainsYearUseShortShow) {
        String timeRange = getTimeRange(startPlay, endPlay, isShowAllWhenDiffYear);
        if (isResultContainsYearUseShortShow && timeRange.contains("年")) {
            timeRange = timeRange.replace("年", "/").replace("月", "/").replace("日", "");
        }
        return timeRange;
    }


    public static String getTimeByTimeRangeWhenYearDiffNoShowHour(long startPlay, long endPlay) {
        return getTimeRange(startPlay, endPlay, false);
    }


    public static String getTimeRange(long startPlay, long endPlay, boolean isShowAllWhenDiffYear) {
        if (startPlay == endPlay || startPlay == 0 || endPlay == 0) {
            return "";
        }
        //默认的为不是同一年的都显示完整的
        String startPatter = isShowAllWhenDiffYear ? FULL_PATTER : YEAR_DATE_PATTER;
        String endPatter = startPatter;
        Calendar startCalendar = getCalendarByTimeStamp(startPlay);
        Calendar endCalendar = getCalendarByTimeStamp(endPlay);
        //是同一年
        if (getYear(startCalendar) == getYear(endCalendar)) {
            //本年
            boolean isCurrentYear = getYear(startCalendar) == getYear(Calendar.getInstance());
            //同一天的
            boolean isCurrentDay = startCalendar.get(Calendar.DAY_OF_YEAR) == endCalendar.get(Calendar.DAY_OF_YEAR);
            //是本年，开始时间 不显示年 否则显示全路径
            startPatter = isCurrentYear ? EXCLUDE_YEAR_PATTER : FULL_PATTER;
            //是同一天的话，结束时间只显示 小时秒 否则显示月日
            endPatter = isCurrentDay ? TIME_PATTER : EXCLUDE_YEAR_PATTER;

            //新加的同一天
            startPatter = isCurrentDay ? startPatter : DATE_PATTER;
            endPatter = isCurrentDay ? endPatter : DATE_PATTER;

        }
        return getDateToString(startPlay, startPatter) + " - " + getDateToString(endPlay, endPatter);

    }


    public static int getYear(Calendar calendar) {
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Calendar calendar) {
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    private static Calendar getCalendarByTimeStamp(long timeStampSecond) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(timeStampSecond * 1000);
        return instance;
    }

    private static String getDateToString(long milSecond, String pattern) {
        milSecond = milSecond * 1000;
        //这个是你要转成后的时间的格式
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(milSecond));
    }


}
