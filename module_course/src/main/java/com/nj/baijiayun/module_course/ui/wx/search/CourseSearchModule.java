package com.nj.baijiayun.module_course.ui.wx.search;

import com.nj.baijiayun.module_common.di.ActivityScoped;
import com.nj.baijiayun.module_public.api.PublicApiModule;

import dagger.Binds;
import dagger.Module;

@Module(includes = PublicApiModule.class)
public abstract class CourseSearchModule {
    @ActivityScoped
    @Binds
    abstract CourseSearchContract.Presenter presenter(CourseSearchPresenter presenter);
}
