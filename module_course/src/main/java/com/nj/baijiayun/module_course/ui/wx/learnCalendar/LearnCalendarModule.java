package com.nj.baijiayun.module_course.ui.wx.learnCalendar;

import com.nj.baijiayun.module_common.di.FragmentScoped;
import com.nj.baijiayun.module_course.api.CourseApiModule;
import com.nj.baijiayun.module_public.api.PublicApiModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author chengang
 * @date 2019-08-07
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx.learnCalendar
 * @describe
 */
@Module(includes ={CourseApiModule.class, PublicApiModule.class})
public abstract class LearnCalendarModule {

    @FragmentScoped
    @Binds
    abstract LearnCalendarContract.Presenter presenter(LearnCalendarPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LearnCalendarFragment learnCalendarFragment();
}
