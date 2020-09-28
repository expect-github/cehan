package com.nj.baijiayun.module_course.ui.wx.mylearnlist;

import com.nj.baijiayun.module_common.di.FragmentScoped;
import com.nj.baijiayun.module_course.api.CourseApiModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author chengang
 * @date 2019-08-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.mvp.contract
 * @describe
 */
@Module(includes = CourseApiModule.class)
public abstract class MyLearnedCourseModule {

    @FragmentScoped
    @Binds
    abstract MyLearnedCourseContract.Presenter presenter(MyLearnedCoursePresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MyCourseFragment myCourseFragment();

}
