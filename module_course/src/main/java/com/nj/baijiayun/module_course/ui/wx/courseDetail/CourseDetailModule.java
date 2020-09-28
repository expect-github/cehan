package com.nj.baijiayun.module_course.ui.wx.courseDetail;

import android.content.Intent;

import com.nj.baijiayun.module_common.di.ActivityScoped;
import com.nj.baijiayun.module_common.di.qualifier.Qualifier1;
import com.nj.baijiayun.module_course.api.CourseApiModule;
import com.nj.baijiayun.module_public.api.PublicApiModule;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx.courseDetail
 * @describe
 */
@Module(includes ={ CourseApiModule.class, PublicApiModule.class})
public abstract class CourseDetailModule {

    @ActivityScoped
    @Binds
    abstract CourseDetailContract.Presenter presenter(CourseDetailPresenter presenter);

    @ActivityScoped
    @Binds
    abstract OutLineContract.Presenter outLinePresenter(OutLinePresenter presenter);


    @Provides
    @ActivityScoped
    static int providerCourseId(WxCourseDetailActivity wxCourseDetailActivity) {
        Intent intent = wxCourseDetailActivity.getIntent();
        if (intent == null) {
            return 0;
        }
        return intent.getIntExtra("courseId", 0);
    }
    @Qualifier1
    @Provides
    @ActivityScoped
    static int providerLastPageSystemCourseId(WxCourseDetailActivity wxCourseDetailActivity) {
        Intent intent = wxCourseDetailActivity.getIntent();
        if (intent == null) {
            return 0;
        }
        return intent.getIntExtra("system_course_id", 0);
    }



}
