package com.nj.baijiayun.module_public.helper.config.tasks;

import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_public.api.PublicServiceHelper;
import com.nj.baijiayun.module_public.bean.response.AppConfigResponse;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;

import io.reactivex.schedulers.Schedulers;

/**
 * @author chengang
 * @date 2020-02-05
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.config
 * @describe
 */
public class AppConfigTask extends BaseConfigTask {


    @Override
    public void start() {
        if (getConfigLoadCallBack() != null) {
            getConfigLoadCallBack().preLoad();
        }
        PublicServiceHelper.getService()
                .getAppConfig()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .subscribe(new BaseSimpleObserver<AppConfigResponse>() {

                    @Override
                    public void onNext(AppConfigResponse appConfigResponse) {
                        if (appConfigResponse.isSuccess()) {
                            onSuccess(appConfigResponse);
                        }
                    }

                    @Override
                    public void onSuccess(AppConfigResponse appConfigResponse) {
                        if (getConfigLoadCallBack() != null) {
                            getConfigLoadCallBack().loadSuccess(appConfigResponse.getData());
                        }
                        ConfigManager.getInstance().saveAppCommonConfig(appConfigResponse.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        if (getConfigLoadCallBack() != null) {
                            getConfigLoadCallBack().loadFail(e);
                        }
                    }
                });
    }


}
