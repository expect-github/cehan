package com.nj.baijiayun.module_course.ui.wx.mylearnddetail;

import android.content.Intent;

import com.nj.baijiayun.module_common.di.ActivityScoped;
import com.nj.baijiayun.module_common.di.qualifier.Qualifier1;
import com.nj.baijiayun.module_common.di.qualifier.Qualifier2;
import com.nj.baijiayun.module_course.api.CourseApiModule;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * @author chengang
 * @date 2019-08-04
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx.mylearnddetail
 * @describe
 */
@Module(includes = CourseApiModule.class)
public abstract class MyLearnedDetailModule {
    @ActivityScoped
    @Binds
    abstract MyLearnedDetailContract.Presenter presenter(MyLearnedDetailPresenter presenter);

    @Qualifier1
    @Provides
    @ActivityScoped
    static int providerCourseId(MyLearnedCourseDetailActivity activity) {
        Intent intent = activity.getIntent();
        if (intent == null) {
            return 0;
        }
        return intent.getIntExtra("courseId", 0);
    }

    @Qualifier2
    @Provides
    @ActivityScoped
    static int providerCourseType(MyLearnedCourseDetailActivity activity) {
        Intent intent = activity.getIntent();
        if (intent == null) {
            return 0;
        }
        return intent.getIntExtra("courseType", 0);
    }



}
