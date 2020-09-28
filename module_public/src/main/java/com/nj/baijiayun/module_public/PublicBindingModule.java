package com.nj.baijiayun.module_public;

import com.nj.baijiayun.module_common.di.ActivityScoped;
import com.nj.baijiayun.module_public.mvp.module.BindPhoneModule;
import com.nj.baijiayun.module_public.mvp.module.ForgetPwdModule;
import com.nj.baijiayun.module_public.mvp.module.LoginModule;
import com.nj.baijiayun.module_public.mvp.module.SetPwdModule;
import com.nj.baijiayun.module_public.ui.BindPhoneActivity;
import com.nj.baijiayun.module_public.ui.ForgetPwdActivity;
import com.nj.baijiayun.module_public.ui.LoginActivity;
import com.nj.baijiayun.module_public.ui.SetPwdActivity;

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
public abstract class PublicBindingModule {

    /**
     * 注册module  需要制定modules 知道如何生成Presenter
     *
     * @return Activity
     */
    @ActivityScoped
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity loginActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = ForgetPwdModule.class)
    abstract ForgetPwdActivity forgetPwdActivity();


    @ActivityScoped
    @ContributesAndroidInjector(modules = BindPhoneModule.class)
    abstract BindPhoneActivity bindPhoneActivity();


    @ActivityScoped
    @ContributesAndroidInjector(modules = SetPwdModule.class)
    abstract SetPwdActivity setPwdActivity();


}
