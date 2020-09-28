package com.nj.baijiayun.module_public.helper.config.tasks;

import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_public.api.PublicServiceHelper;
import com.nj.baijiayun.module_public.bean.PublicOauthBean;
import com.nj.baijiayun.module_public.helper.RetryDefaultWithDelay;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;

import io.reactivex.schedulers.Schedulers;

/**
 * @author chengang
 * @date 2020-02-05
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.config.tasks
 * @describe
 */
public class OauthTask extends BaseConfigTask {
    @Override
    public void start() {
        PublicServiceHelper.getService()
                .getOauthConfig()
                .retryWhen(new RetryDefaultWithDelay())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSimpleObserver<BaseResponse<PublicOauthBean>>() {
                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onNext(BaseResponse<PublicOauthBean> response) {
                        if (response.isSuccess()) {
                            onSuccess(response);
                        }
                    }

                    @Override
                    public void onSuccess(BaseResponse<PublicOauthBean> response) {
                        ConfigManager.getInstance().saveOauthConfig(response.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                    }
                });
    }
}
