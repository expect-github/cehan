package com.nj.baijiayun.module_public.helper.config.tasks;

import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.widget.dialog.CommonShareDialog;
import com.nj.baijiayun.module_public.bean.PublicShareAvaiableBean;
import com.nj.baijiayun.module_public.helper.RetryDefaultWithDelay;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;

import io.reactivex.schedulers.Schedulers;

/**
 * @author chengang
 * @date 2020-02-06
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.config.tasks
 * @describe
 */
public class ShareTask extends BaseConfigTask {
    @Override
    public void start() {
        setShareConfig();
        ConfigManager.getInstance().getService()
                .getShareConfig()
                .retryWhen(new RetryDefaultWithDelay())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSimpleObserver<BaseResponse<PublicShareAvaiableBean>>() {
                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onNext(BaseResponse<PublicShareAvaiableBean> response) {
                        if (response.isSuccess()) {
                            onSuccess(response);
                        }
                    }

                    @Override
                    public void onSuccess(BaseResponse<PublicShareAvaiableBean> response) {
                        ConfigManager.getInstance().saveShareConfig(response.getData());
                        setShareConfig();
                    }

                    @Override
                    public void onFail(Exception e) {
                        setShareConfig();
                    }
                });
    }

    private void setShareConfig() {
        Logger.d("setShareConfig");
        CommonShareDialog.setFilterQq(ConfigManager.getInstance().isAvailableShareByQq());
        CommonShareDialog.setFilterWx(ConfigManager.getInstance().isAvailableShareByWeChat());
    }
}
