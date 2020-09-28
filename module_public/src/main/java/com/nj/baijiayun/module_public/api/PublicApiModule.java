package com.nj.baijiayun.module_public.api;

import com.nj.baijiayun.module_common.di.ActivityScoped;
import com.nj.baijiayun.module_common.di.base.ApiModule;
import com.nj.baijiayun.module_common.di.base.UploadApiModule;
import com.nj.baijiayun.module_common.di.base.UploadQualifier;

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
@Module(includes = {ApiModule.class, UploadApiModule.class})
public class PublicApiModule {

    @Provides
    @ActivityScoped
    static PublicService provideApi(Retrofit retrofit) {
        return retrofit.create(PublicService.class);
    }

    @Provides
    @ActivityScoped
    @UploadQualifier
    static PublicService provideUpload(@UploadQualifier Retrofit retrofit) {
        return retrofit.create(PublicService.class);
    }

}
