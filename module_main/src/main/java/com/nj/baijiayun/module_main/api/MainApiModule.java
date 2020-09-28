package com.nj.baijiayun.module_main.api;

import com.nj.baijiayun.module_common.di.ActivityScoped;
import com.nj.baijiayun.module_common.di.base.ApiModule;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author chengang
 * @date 2019-06-10
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_user.api
 * @describe
 */
@Module(includes = {ApiModule.class})
public class MainApiModule {

    @Provides
    @ActivityScoped
    static MainService provideApi(Retrofit retrofit) {
        return retrofit.create(MainService.class);
    }

}
