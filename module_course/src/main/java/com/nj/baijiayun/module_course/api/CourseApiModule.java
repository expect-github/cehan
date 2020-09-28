package com.nj.baijiayun.module_course.api;

import com.nj.baijiayun.module_common.di.ActivityScoped;
import com.nj.baijiayun.module_common.di.base.ApiModule;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = ApiModule.class)
public class CourseApiModule {
    @Provides
    @ActivityScoped
    static CourseService providerApi(Retrofit retrofit) {
        return retrofit.create(CourseService.class);
    }
}
