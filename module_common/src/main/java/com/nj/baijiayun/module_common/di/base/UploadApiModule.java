package com.nj.baijiayun.module_common.di.base;

import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.di.ActivityScoped;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author chengang
 * @date 2019-06-20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.di.base
 * @describe
 */
@Module
public class UploadApiModule {

    @Provides
    @ActivityScoped
    @UploadQualifier
    static Retrofit providerRetrofit() {
        return NetMgr.getInstance().getDefaultRetrofit(NetMgr.UPLOAD_DEFAULT_KEY);
    }

}
