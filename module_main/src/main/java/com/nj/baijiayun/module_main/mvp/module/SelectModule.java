package com.nj.baijiayun.module_main.mvp.module;

import com.nj.baijiayun.module_common.di.FragmentScoped;
import com.nj.baijiayun.module_main.api.MainApiModule;
import com.nj.baijiayun.module_main.fragments.SelectCourseFragment;
import com.nj.baijiayun.module_main.mvp.contract.SelectContract;
import com.nj.baijiayun.module_main.mvp.presenter.SelectCoursePresenter;
import com.nj.baijiayun.module_public.api.PublicApiModule;
import com.nj.baijiayun.module_public.mvp.contract.CourseContract;
import com.nj.baijiayun.module_public.mvp.presenter.CoursePresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author chengang
 * @date 2020-02-20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.mvp.module
 * @describe
 */
@Module(includes = {MainApiModule.class, PublicApiModule.class})
public abstract class SelectModule {

    @FragmentScoped
    @Binds
    abstract CourseContract.Presenter coursePresenter(CoursePresenter presenter);


    @FragmentScoped
    @Binds
    abstract SelectContract.Presenter selectCoursePresenter(SelectCoursePresenter presenter);


    @FragmentScoped
    @ContributesAndroidInjector
    abstract SelectCourseFragment selectCourseFragment();


}
