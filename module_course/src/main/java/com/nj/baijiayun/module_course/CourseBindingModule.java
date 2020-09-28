package com.nj.baijiayun.module_course;

import com.nj.baijiayun.module_common.di.ActivityScoped;
import com.nj.baijiayun.module_course.ui.wx.courseDetail.WxCourseDetailActivity;
import com.nj.baijiayun.module_course.ui.wx.learnCalendar.LearnCalendarModule;
import com.nj.baijiayun.module_course.ui.wx.learnCalendar.LearnCalendarNewActivty;
import com.nj.baijiayun.module_course.ui.wx.mylearnddetail.MyLearnedCourseDetailActivity;
import com.nj.baijiayun.module_course.ui.wx.mylearnddetail.MyLearnedDetailModule;
import com.nj.baijiayun.module_course.ui.wx.mylearnlist.MyCourseActivity;
import com.nj.baijiayun.module_course.ui.wx.mylearnlist.MyLearnedCourseModule;
import com.nj.baijiayun.module_course.ui.wx.search.CourseSearchActivity;
import com.nj.baijiayun.module_course.ui.wx.search.CourseSearchModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module ActivityBindingModule is on,
 * in our case that will be AppComponent. The beautiful part about this setup is that you never need to tell AppComponent that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that AppComponent exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the specified modules and be aware of a scope annotation @ActivityScoped
 * When Dagger.Android annotation processor runs it will create 4 subcomponents for us.
 * <p>
 * 全局用来注册的自动绑定的Activity
 */

@Module
public abstract class CourseBindingModule {


    @ActivityScoped
    @ContributesAndroidInjector(modules = com.nj.baijiayun.module_course.ui.wx.courseDetail.CourseDetailModule.class)
    abstract WxCourseDetailActivity courseDetailActivity();



    @ActivityScoped
    @ContributesAndroidInjector(modules = MyLearnedDetailModule.class)
    abstract MyLearnedCourseDetailActivity myLearnedCourseDetailActivity();



    @ActivityScoped
    @ContributesAndroidInjector(modules = CourseSearchModule.class)
    abstract CourseSearchActivity courseSearchActivity();


    @ActivityScoped
    @ContributesAndroidInjector(modules = MyLearnedCourseModule.class)
    abstract MyCourseActivity myCourseActivity();


    @ActivityScoped
    @ContributesAndroidInjector(modules = LearnCalendarModule.class)
    abstract LearnCalendarNewActivty learnCalendarActivity();



}
