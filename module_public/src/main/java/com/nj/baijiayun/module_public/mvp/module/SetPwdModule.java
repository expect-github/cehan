package com.nj.baijiayun.module_public.mvp.module;

import com.nj.baijiayun.module_common.di.ActivityScoped;
import com.nj.baijiayun.module_public.api.PublicApiModule;
import com.nj.baijiayun.module_public.mvp.contract.SetPwdContract;
import com.nj.baijiayun.module_public.mvp.presenter.SetPwdPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * @author chengang
 * @date 2019-06-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.mvp.module
 * @describe
 */
@Module(includes = PublicApiModule.class)
public abstract class SetPwdModule {



    @ActivityScoped
    @Binds
    abstract SetPwdContract.Presenter presenter(SetPwdPresenter presenter);

}
