package com.nj.baijiayun.module_main;

import com.nj.baijiayun.module_common.di.ActivityScoped;
import com.nj.baijiayun.module_main.mvp.module.MainModule;
import com.nj.baijiayun.module_main.mvp.module.SelectModule;
import com.nj.baijiayun.module_main.ui.SelectCourseActivity;

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
public abstract class MainBindingModule {


    /**
     * 注册module  需要制定modules 知道如何生成Presenter
     *
     * @return Activity
     */

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = SelectModule.class)
    abstract SelectCourseActivity selectCourseActivity();

}
