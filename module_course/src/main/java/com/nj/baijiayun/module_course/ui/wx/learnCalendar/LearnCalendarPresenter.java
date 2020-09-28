package com.nj.baijiayun.module_course.ui.wx.learnCalendar;

import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_common.helper.TimeFormatHelper;
import com.nj.baijiayun.module_course.api.CourseService;
import com.nj.baijiayun.module_course.bean.response.CalendarCourseResponse;
import com.nj.baijiayun.module_course.bean.response.CalendarResponse;
import com.nj.baijiayun.module_course.bean.wx.CalendarBean;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.helper.videoplay.VideoDataChangeHelper;
import com.nj.baijiayun.module_public.helper.videoplay.VideoPlayHelper;
import com.nj.baijiayun.module_public.helper.videoplay.res.BjyTokensRes;
import com.nj.baijiayun.module_public.helper.videoplay.res.OneToOneTokenRes;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author chengang
 * @date 2019-08-07
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx.learnCalendar
 * @describe
 */
public class LearnCalendarPresenter extends LearnCalendarContract.Presenter {

    private Map<String, List<CalendarBean>> mMouthCourse = new HashMap<>();
    private String mCurrentMount;

    @Inject
    CourseService mCourseService;
    @Inject
    PublicService mPublicService;

    @Inject
    public LearnCalendarPresenter() {

    }


    @Override
    public void play(int courseId,int sectionId, int courseType) {
        mView.showLoadV();

        submitRequest(mCourseService.getBjyVideoToken(String.valueOf(sectionId)), new BaseObserver<BjyTokensRes>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(BjyTokensRes bjyTokensRes) {
                mView.closeLoadV();
                VideoPlayHelper.wrapperBjyTokenBean(bjyTokensRes.getData().getTokenData(),courseId,sectionId);
                VideoPlayHelper.playVideo(bjyTokensRes.getData(),courseType);
            }

            @Override
            public void onFail(Exception e) {
                mView.closeLoadV();
                mView.showToastMsg(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void playOneToOne(int courseId, int courseType) {
        mView.showLoadV();
        submitRequest(mPublicService.getBjyOneToOneToken(String.valueOf(courseId)), new BaseObserver<OneToOneTokenRes>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(OneToOneTokenRes oneToOneTokenRes) {
                mView.closeLoadV();
                VideoPlayHelper.playVideo(VideoDataChangeHelper.OneToOneDataToBjyToken(oneToOneTokenRes.getData()), courseType);
            }

            @Override
            public void onFail(Exception e) {
                mView.closeLoadV();
                mView.showToastMsg(e.getMessage());

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void getCalendar(String month) {
        submitRequest(mCourseService.getCalendar(month), new BaseObserver<CalendarResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(CalendarResponse calendarResponse) {
                mView.setCalendarData(calendarResponse.getData());

            }

            @Override
            public void onFail(Exception e) {
                mView.showToastMsg(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        });


    }

    @Override
    public void getCalendarCourseByTimeStamp(long timeStamp) {
        mView.showLoadV();
        submitRequest(mCourseService.getCalendarCourse(timeStamp), new BaseObserver<CalendarCourseResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(CalendarCourseResponse calendarCourseResponse) {
                mView.closeLoadV();

                mView.setCurrentSelectCourse(calendarCourseResponse.getData());
                getCalendar(TimeFormatHelper.getMonth(timeStamp));

            }

            @Override
            public void onFail(Exception e) {
                mView.closeLoadV();

                mView.showToastMsg(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void getCalendarCourseByYearMonthDay(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        String yearMonth = TimeFormatHelper.getMonth(calendar.getTimeInMillis() / 1000);
        List<CalendarBean> time = mMouthCourse.get(yearMonth);
        if (!yearMonth.equals(mCurrentMount)) {
            mCurrentMount = yearMonth;
            getCalendar(yearMonth);
        } else {
            String selected = TimeFormatHelper.getDate(calendar.getTimeInMillis() / 1000);
            if (time != null && !time.contains(selected)) {
                mView.setCurrentSelectCourse(null);
                return;
            }
        }
        getCalendarCourseByTimeStamp(TimeFormatHelper.getTimeInSecond(year, month, day));

    }


}
