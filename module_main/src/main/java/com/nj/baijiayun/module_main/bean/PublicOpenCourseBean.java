package com.nj.baijiayun.module_main.bean;

import com.nj.baijiayun.module_public.bean.PublicCourseBean;
import com.nj.baijiayun.module_public.helper.TimeManager;

import java.text.MessageFormat;

/**
 * @author chengang
 * @date 2019-12-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class PublicOpenCourseBean extends PublicCourseBean {

    public boolean isLiveInProgress() {
        return TimeManager.getInstance().getServiceTimeSecond() <= getEndPlayDate() && TimeManager.getInstance().getServiceTimeSecond() >= getStartPlayDate();
    }

    public boolean isLiveUnStart() {
        return TimeManager.getInstance().getServiceTimeSecond() < getStartPlayDate();
    }

    public boolean isLiveEnd() {
        return TimeManager.getInstance().getServiceTimeSecond() > getEndPlayDate();
    }


    public String getTeacher() {

        if (getTeacherList() == null || getTeacherList().size() == 0) {
            return "";
        }
        if (getTeacherList().size() == 1) {
            return getTeacherList().get(0).getName();
        }
        return MessageFormat.format("{0}、{1}{2}",
                getTeacherList().get(0).getName(),
                getTeacherList().get(1).getName(),
                getTeacherList().size() > 2 ? "等" : "");
    }

    public String getImg()
    {
        if (getTeacherList() == null || getTeacherList().size() == 0) {
            return "";
        }
        return getTeacherList().get(0).getAvatar();
    }

    public int getNumber() {
        return getSalesNum();
    }


    public long getStartTime() {
        return getStartPlayDate();
    }
}

