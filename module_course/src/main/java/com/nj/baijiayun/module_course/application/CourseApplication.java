package com.nj.baijiayun.module_course.application;


import com.nj.baijiayun.module_public.BaseApp;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class CourseApplication extends BaseApp {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
//        DaggerAppComponent.builder().application(this).build()
        return null;
    }
}
