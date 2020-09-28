package com.nj.baijiayun.module_public.mvp.module;

import com.nj.baijiayun.module_common.di.ActivityScoped;
import com.nj.baijiayun.module_public.api.PublicApiModule;
import com.nj.baijiayun.module_public.mvp.contract.BindPhoneContract;
import com.nj.baijiayun.module_public.mvp.presenter.BindPhonePresenter;
import com.nj.baijiayun.module_public.ui.BindPhoneActivity;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * @author chengang
 * @date 2019-06-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.mvp.module
 * @describe
 */
@Module(includes = PublicApiModule.class)
public abstract class BindPhoneModule {

    @ActivityScoped
    @Provides
    static String provideOpenId(BindPhoneActivity bindPhoneActivity) {
        if (bindPhoneActivity.getIntent() == null) {
            return "";
        }
        return bindPhoneActivity.getIntent().getStringExtra("openId");
    }

    @ActivityScoped
    @Provides
    static int provideLoginTypeId(BindPhoneActivity bindPhoneActivity) {
        if (bindPhoneActivity.getIntent() == null) {
            return 0;
        }
        return bindPhoneActivity.getIntent().getIntExtra("loginType", 0);
    }

    @ActivityScoped
    @Binds
    abstract BindPhoneContract.Presenter presenter(BindPhonePresenter presenter);

}
