package com.nj.baijiayun.module_public.mvp.module;

import com.nj.baijiayun.module_common.di.ActivityScoped;
import com.nj.baijiayun.module_common.di.FragmentScoped;
import com.nj.baijiayun.module_public.api.PublicApiModule;
import com.nj.baijiayun.module_public.fragment.LoginByCodeFragment;
import com.nj.baijiayun.module_public.fragment.LoginByPwdFragment;
import com.nj.baijiayun.module_public.mvp.contract.LoginContract;
import com.nj.baijiayun.module_public.mvp.presenter.LoginByCodePresenter;
import com.nj.baijiayun.module_public.mvp.presenter.LoginByPwdPresenter;
import com.nj.baijiayun.module_public.mvp.presenter.LoginPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author chengang
 * @date 2019-06-10
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_user.ui.login
 * @describe 登录的module 提供一个返回Presenter 的方法，这个会自动生成 LoginPresenter 对象
 */
@Module(includes = PublicApiModule.class)
public abstract class LoginModule {


    @ActivityScoped
    @Binds
    abstract LoginContract.Presenter loginPresenter(LoginPresenter loginPresenter);

    @FragmentScoped
    @Binds
    abstract LoginContract.BaseLoginByCodePresenter loginByCodePresenter(LoginByCodePresenter presenter);

    @FragmentScoped
    @Binds
    abstract LoginContract.BaseLoginByPwdPresenter loginByPwdPresenter(LoginByPwdPresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoginByCodeFragment loginByCodeFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract LoginByPwdFragment loginByPwdFragment();

}
