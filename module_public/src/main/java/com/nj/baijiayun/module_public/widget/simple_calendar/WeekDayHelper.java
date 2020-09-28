package com.nj.baijiayun.module_public.widget.simple_calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author chengang
 * @date 2019-07-22
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget.simple_calendar
 * @describe
 */
public class WeekDayHelper {


    public static int getTodayIndex() {
        List<String> data = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int mWay = c.get(Calendar.DAY_OF_WEEK);
        if (mWay == 1) {
            mWay = 8;
        }
        return mWay - 2;
    }

    /**
     * 这里计算出开始的日期从周一算
     *
     * @param formatStr
     * @return
     */
    public static List<String> getTwoWeekStartThisWeekData(String formatStr) {
        List<String> data = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Calendar c = Calendar.getInstance();
        int mWay = c.get(Calendar.DAY_OF_WEEK);
        if (mWay == 1) {
            mWay = 8;
        }

        System.out.println(mWay);
        //将时间退到周一
        for (int i = 0; i < (mWay - 1); i++) {
            c.add(Calendar.DATE, -1);
        }
        //得到2周时间
        for (int i = 0; i < 14; i++) {
            c.add(Calendar.DATE, 1);
            data.add(format.format(c.getTime()));
        }
        return data;
    }
}
