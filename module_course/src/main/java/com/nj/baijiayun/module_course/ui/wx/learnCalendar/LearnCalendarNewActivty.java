package com.nj.baijiayun.module_course.ui.wx.learnCalendar;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_common.base.BaseAppFragmentActivity;
import com.nj.baijiayun.module_common.base.BaseEmptyPresenter;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_public.consts.RouterConstant;

/**
 * @author chengang
 * @date 2020-02-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx.learnCalendar
 * @describe
 */
@Route(path = RouterConstant.PAGE_COURSE_LEARN_CALENDAR)
public class LearnCalendarNewActivty extends BaseAppFragmentActivity<BaseEmptyPresenter> {

    @Override
    protected BaseAppFragment createFragment() {
        return new LearnCalendarFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setPageTitle(R.string.course_activity_title_learn_calendar);
    }



    @Override
    protected void registerListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
