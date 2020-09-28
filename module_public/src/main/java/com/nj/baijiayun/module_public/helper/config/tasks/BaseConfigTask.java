package com.nj.baijiayun.module_public.helper.config.tasks;

import android.annotation.SuppressLint;

import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_public.api.PublicServiceHelper;
import com.nj.baijiayun.module_public.helper.config.ConfigLoadCallBack;

/**
 * @author chengang
 * @date 2020-02-05
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.config
 * @describe
 */
public abstract class BaseConfigTask {

    public abstract void start();

    private ConfigLoadCallBack configLoadCallBack;

    public <T> void setConfigLoadCallBack(ConfigLoadCallBack<T> configLoadCallBack) {
        this.configLoadCallBack = configLoadCallBack;
    }

    public ConfigLoadCallBack getConfigLoadCallBack() {
        return configLoadCallBack;
    }

    @SuppressLint("CheckResult")
    public void loadConfig(String params) {
        PublicServiceHelper.getService()
                .getConfigByParams(params)
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .subscribe(mapBaseResponse -> loadDataSuccess(mapBaseResponse.getData())
                        , throwable -> loadDataFail(throwable));
    }

    protected void loadDataSuccess(Object data) {
    }

    protected void loadDataFail( Throwable throwable) {
    }

}
