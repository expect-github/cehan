package com.nj.baijiayun.module_course.ui.wx.learnCalendar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.adapter.LearnCalendarAdapter;
import com.nj.baijiayun.module_course.bean.wx.CalendarCourseBean;
import com.nj.baijiayun.module_course.helper.CourseHelper;
import com.nj.baijiayun.module_course.widget.CourseUndercarriageDialog;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.PublicFormatHelper;
import com.nj.baijiayun.module_public.helper.ViewHelper;
import com.nj.baijiayun.refresh.recycleview.SpaceItemDecoration;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author chengang
 * @date 2020-02-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx.learnCalendar
 * @describe
 */
public class LearnCalendarFragment extends BaseAppFragment<LearnCalendarContract.Presenter> implements LearnCalendarContract.View {

    private TextView mSelectedMonthTv;
    private ImageView mPreMonthIv;
    private ImageView mNextMonthIv;
    private CalendarView mCalendarView;
    private TextView mCalendarDateTv;
    private TextView mCourseNumTv;
    private RecyclerView mRv;
    private LinearLayout mEmptyLl;
    private LearnCalendarAdapter mLearnCalendarAdapter;
    private int mSelectedYear;
    private int mSelectedMonth;
    private Button mBtnLogin;


    @Override
    public void initInject() {
        super.initInject();
    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.course_activity_learn_calendar;
    }

    @Override
    protected void initView(View mContextView) {
        mSelectedMonthTv = mContextView.findViewById(R.id.tv_selected_month);
        mPreMonthIv = mContextView.findViewById(R.id.iv_pre_month);
        mNextMonthIv = mContextView.findViewById(R.id.iv_next_month);
        mCalendarView = mContextView.findViewById(R.id.calendarView);
        mCalendarDateTv = mContextView.findViewById(R.id.tv_calendar_date);
        mCourseNumTv = mContextView.findViewById(R.id.tv_course_num);
        mRv = mContextView.findViewById(R.id.rv);
        mEmptyLl = mContextView.findViewById(R.id.ll_empty);
        mBtnLogin = mContextView.findViewById(R.id.btn_login);

        initRv();
        initCalendar();
    }

    @Override
    public void registerListener() {

        mBtnLogin.setOnClickListener(v -> JumpHelper.checkLogin());

        LiveDataBus.get().with(LiveDataBusEvent.LOGIN_STATUS_CHANGE, Boolean.class).observe(this, aBoolean -> {
            tryLoadData();
        });

        mCalendarView.setOnMonthChangeListener((year, month) -> {
            mSelectedMonthTv.setText(getString(R.string.course_fmt_learn_calendar_date, year, month));
            mSelectedYear = year;
            mSelectedMonth = month;
        });


        mCalendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                if (!AccountHelper.getInstance().isLogin()) {
                    return;
                }

                mPresenter.getCalendarCourseByYearMonthDay(calendar.getYear(), calendar.getMonth(), calendar.getDay());

                java.util.Calendar instance = java.util.Calendar.getInstance();
                if (PublicFormatHelper.getYear(instance) == calendar.getYear()
                        && PublicFormatHelper.getMonth(instance) == calendar.getMonth()
                        && PublicFormatHelper.getDay(instance) == calendar.getDay()
                ) {
                    mCalendarDateTv.setText("当日课程");
                    return;
                }

                mCalendarDateTv.setText(MessageFormat.format("{0}月{1}号", calendar.getMonth(), calendar.getDay()));

            }
        });


        mPreMonthIv.setOnClickListener(v -> {
//            if (mSelectedYear > mCalendarView.getCurYear() ||
//                    (mSelectedYear == mCalendarView.getCurYear() &&
//                            mSelectedMonth > mCalendarView.getCurMonth())) {
            mCalendarView.scrollToPre();
//            }
        });
        mNextMonthIv.setOnClickListener(v -> mCalendarView.scrollToNext());

        mLearnCalendarAdapter.setOnItemClickListener((holder, position, view, item) -> {
            if (!item.isCanStudy()) {
                new CourseUndercarriageDialog(getActivity()).show();
                return;
            }

            //这里是章的节id
            if (CourseHelper.isCanPlay(item.getCourseType())) {
                if (ConstsCouseType.isOto(item.getCourseType())) {
                    mPresenter.playOneToOne(item.getCourseId(), item.getCourseType());
                    return;
                }
                mPresenter.play(item.getCourseId(),item.getCourseChapterId(), item.getCourseType());
            } else {
                ARouter.getInstance()
                        .build(RouterConstant.PAGE_COURSE_DETAIL)
                        .withInt("courseId", item.getCourseId())
                        .navigation();
            }

        });

    }

    @Override
    public void processLogic() {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        tryLoadData();
    }

    protected void loadData() {
        mPresenter.getCalendarCourseByTimeStamp((System.currentTimeMillis() / 1000));
    }

    private void initRv() {
        mRv.addItemDecoration(SpaceItemDecoration.create().setSpace(10));
        mRv.setNestedScrollingEnabled(false);
        mLearnCalendarAdapter = new LearnCalendarAdapter(getContext());
        mRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRv.setAdapter(mLearnCalendarAdapter);
    }

    private void initCalendar() {
        java.util.Calendar instance = java.util.Calendar.getInstance();
        int year = instance.get(java.util.Calendar.YEAR);
        int month = instance.get(java.util.Calendar.MONTH) + 1;
        mCalendarView.setRange(2018, 1, 1, 2030, 12, 31);
        mCalendarView.scrollToCurrent(false);
        mCalendarDateTv.setText("当日课程");
        mSelectedMonthTv.setText(getString(R.string.course_fmt_learn_calendar_date, year, month));

    }

    private void tryLoadData() {
        ViewHelper.setViewVisible(mEmptyLl, !AccountHelper.getInstance().isLogin());
        if (!AccountHelper.getInstance().isLogin()) {
            mBtnLogin.setVisibility(View.VISIBLE);
            mLearnCalendarAdapter.clear();
            mCalendarView.clearSchemeDate();
        } else {
            mBtnLogin.setVisibility(View.GONE);
            loadData();
        }
    }


    @Override
    public void setCurrentSelectCourse(List<CalendarCourseBean> data) {

        boolean hasData = data != null && data.size() > 0;
        mRv.setVisibility(hasData ? View.VISIBLE : View.GONE);
        mEmptyLl.setVisibility(hasData ? View.GONE : View.VISIBLE);
        mLearnCalendarAdapter.addAll(data, true);
        mCourseNumTv.setText(MessageFormat.format("当天{0}节课", data.size()));

    }

    @Override
    public void setCalendarData(List<String> data) {
        Map<String, Calendar> calendars = new HashMap<>();
        for (String cDate : data) {
            if (cDate == null || !cDate.contains("-")) {
                continue;
            }
            String[] split = cDate.split("-");
            Calendar calendar = new Calendar();
            calendar.setYear(Integer.parseInt(split[0]));
            calendar.setMonth(Integer.parseInt(split[1]));
            calendar.setDay(Integer.parseInt(split[2]));
            calendars.put(getSchemeCalendar(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])).toString(), calendar);
        }
        mCalendarView.addSchemeDate(calendars);
    }

    int color = 0;


    private Calendar getSchemeCalendar(int year, int month, int day) {
        if (color == 0) {
            color = ContextCompat.getColor(getContext(), R.color.common_main_color);
        }
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);
        return calendar;
    }
}
