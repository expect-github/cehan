package com.nj.baijiayun.module_common.demo;

import com.nj.baijiayun.module_common.di.ActivityScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author chengang
 * @date 2019-06-06
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.demo
 * @describe 不要删除 用来占位生成的继承类的
 */
@Module
public abstract class DemoActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = DemoModule.class)
    abstract DemoActivity demoActivity();



}
