package com.nj.baijiayun.module_common.demo;

import com.nj.baijiayun.module_common.base.BaseListEmptyContract;
import com.nj.baijiayun.module_common.base.BaseListEmptyPresenter;
import com.nj.baijiayun.module_common.di.ActivityScoped;
import com.nj.baijiayun.module_common.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * @author chengang
 * @date 2019-06-06
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.demo
 * @describe
 */
@Module
public abstract class DemoModule {


    @ActivityScoped
    @Binds
    abstract DemoContract.Presenter presenterD(DemoPresenter presenter);

    @Remote
    @Provides
    @ActivityScoped
    static String provideTaskId(DemoActivity activity) {

        return "112";
    }

    @Local
    @Provides
    @ActivityScoped
    static String provideTaskId2(DemoActivity activity) {
        return "99";
    }


    @FragmentScoped
    @ContributesAndroidInjector
    abstract DemoListFragment demoListFragment();


    @FragmentScoped
    @Binds
    abstract BaseListEmptyContract.Presenter presenter(BaseListEmptyPresenter presenter);



}
