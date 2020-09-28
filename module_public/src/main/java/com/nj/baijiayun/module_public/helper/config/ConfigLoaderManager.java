package com.nj.baijiayun.module_public.helper.config;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.SystemWebConfigBean;
import com.nj.baijiayun.module_public.helper.RetryDefaultWithDelay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author chengang
 * @date 2019-09-25
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.config
 * @describe
 */
public class ConfigLoaderManager extends AbstractConfigLoaderCreate {
    private static ConfigLoaderManager mInstance;
    private SystemConfigLoader systemConfigLoader;

    //单例模式，节省资源
    public static ConfigLoaderManager get() {
        if (mInstance == null) {
            synchronized (ConfigLoaderManager.class) {
                if (mInstance == null) {
                    mInstance = new ConfigLoaderManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    public AbstractConfigLoader<SystemWebConfigBean> getSystemConfigLoader() {
        if (systemConfigLoader == null) {
            systemConfigLoader = new SystemConfigLoader();
        }
        return systemConfigLoader;
    }

    @SuppressLint("CheckResult")
    public void getSystemConfig() {
        NetMgr.getInstance().getDefaultRetrofit()
                .create(PublicService.class).getSystemConfig()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryDefaultWithDelay())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                })
                .subscribe(systemWebConfigResponse -> {

                    String json = GsonHelper.getGsonInstance().toJson(systemWebConfigResponse.getData());
                    SystemWebConfigBean systemWebConfigBean = GsonHelper.getGsonInstance().fromJson(json, SystemWebConfigBean.class);
                    getSystemConfigLoader().updateConfig(systemWebConfigBean, json);

                    //加载时候就去加载登陆图
                    if (systemWebConfigBean != null) {
                        Glide.with(BaseApp.getInstance()).load(systemWebConfigBean.getMobileLogo()).into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });
                    }
                });
    }

}
