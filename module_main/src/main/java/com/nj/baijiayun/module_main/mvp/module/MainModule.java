package com.nj.baijiayun.module_main.mvp.module;

import com.nj.baijiayun.module_common.di.FragmentScoped;
import com.nj.baijiayun.module_course.ui.wx.learnCalendar.LearnCalendarModule;
import com.nj.baijiayun.module_main.api.MainApiModule;
import com.nj.baijiayun.module_main.fragments.HomeCalendarFragment;
import com.nj.baijiayun.module_main.fragments.HomeMainFragment;
import com.nj.baijiayun.module_main.fragments.SelectCourseFragment;
import com.nj.baijiayun.module_main.fragments.UserFragment;
import com.nj.baijiayun.module_main.mvp.contract.MainContract;
import com.nj.baijiayun.module_main.mvp.contract.SelectContract;
import com.nj.baijiayun.module_main.mvp.contract.UserCenterContract;
import com.nj.baijiayun.module_main.mvp.presenter.MainPresenter;
import com.nj.baijiayun.module_main.mvp.presenter.SelectCoursePresenter;
import com.nj.baijiayun.module_main.mvp.presenter.UserCenterPresenter;
import com.nj.baijiayun.module_public.api.PublicApiModule;
import com.nj.baijiayun.module_public.mvp.contract.CourseContract;
import com.nj.baijiayun.module_public.mvp.presenter.CoursePresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author chengang
 * @date 2019-06-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.mvp
 * @describe
 */
@Module(includes = {MainApiModule.class, PublicApiModule.class, LearnCalendarModule.class})
public abstract class MainModule {

    @FragmentScoped
    @Binds
    abstract MainContract.Presenter mainPresenter(MainPresenter presenter);


    @FragmentScoped
    @Binds
    abstract CourseContract.Presenter coursePresenter(CoursePresenter presenter);


    @FragmentScoped
    @Binds
    abstract SelectContract.Presenter selectCoursePresenter(SelectCoursePresenter presenter);

    @FragmentScoped
    @Binds
    abstract UserCenterContract.Presenter userCenterPresenter(UserCenterPresenter presenter);


    @FragmentScoped
    @ContributesAndroidInjector
    abstract HomeMainFragment homeMainFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract UserFragment userFragment();


    @FragmentScoped
    @ContributesAndroidInjector
    abstract SelectCourseFragment selectCourseFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HomeCalendarFragment homeCalendarFragment();


}
