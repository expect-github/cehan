package com.nj.baijiayun.module_public.widget.simple_calendar;

/**
 * @author chengang
 * @date 2019-07-22
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.widget.simple_calendar
 * @describe
 */
public class DayOfMonthBean {

    private int day;
    private boolean isSelected;

    private boolean isBeforeToday;
    private boolean isToday;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isBeforeToday() {
        return isBeforeToday;
    }

    public void setBeforeToday(boolean beforeToday) {
        isBeforeToday = beforeToday;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

}
