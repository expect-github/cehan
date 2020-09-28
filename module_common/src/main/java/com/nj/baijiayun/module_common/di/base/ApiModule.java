package com.nj.baijiayun.module_common.di.base;

import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.di.ActivityScoped;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author chengang
 * @date 2019-06-06
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.basic.demo
 * @describe
 */
@Module
public class ApiModule {


    @Provides
    @ActivityScoped
    static Retrofit providerRetrofit() {
        return NetMgr.getInstance().getDefaultRetrofit();
    }

}
