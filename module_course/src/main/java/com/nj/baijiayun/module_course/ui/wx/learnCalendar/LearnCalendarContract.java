package com.nj.baijiayun.module_course.ui.wx.learnCalendar;

import com.nj.baijiayun.module_common.mvp.BasePresenter;
import com.nj.baijiayun.module_common.mvp.BaseView;
import com.nj.baijiayun.module_course.bean.wx.CalendarCourseBean;

import java.util.List;

/**
 * @author chengang
 * @date 2019-08-07
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx.learnCalendar
 * @describe
 */
public interface LearnCalendarContract {

    interface View extends BaseView {

        void setCurrentSelectCourse(List<CalendarCourseBean> data);

        void setCalendarData(List<String> data);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void play(int courseId,int sectionId, int courseType);

        public abstract void playOneToOne(int courseId, int courseType);

        public abstract void getCalendar(String month);

        public abstract void getCalendarCourseByTimeStamp(long timeStamp);

        public abstract void getCalendarCourseByYearMonthDay(int year, int month, int day);
    }

}
