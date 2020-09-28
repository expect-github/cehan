package com.nj.baijiayun.module_main.mvp.presenter;

import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_main.api.MainService;
import com.nj.baijiayun.module_main.bean.res.SignResponse;
import com.nj.baijiayun.module_main.bean.res.UserCenterResponse;
import com.nj.baijiayun.module_main.mvp.contract.UserCenterContract;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.response.MessageResponse;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.manager.ShortcutBadgerManager;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author chengang
 * @date 2019-06-19
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.mvp
 * @describe
 */
public class UserCenterPresenter extends UserCenterContract.Presenter {

    @Inject
    MainService mMainService;

    @Inject
    PublicService mPublicService;


    @Inject
    UserCenterPresenter() {
    }


    @Override
    public void getUserCenterInfo() {
        if (!AccountHelper.getInstance().isLogin()) {
            return;
        }
        submitRequest(mMainService.getUserCenterInfo(), new BaseObserver<UserCenterResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(UserCenterResponse userCenterResponse) {

                mView.setUserCenterInfo(userCenterResponse.getData());
            }

            @Override
            public void onFail(Exception e) {
                Logger.e("onFail--->" + e.getMessage());
            }

            @Override
            public void onNext(UserCenterResponse userCenterResponse) {
                if (userCenterResponse.isSuccess()) {
                    onSuccess(userCenterResponse);
                }

            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void updatUserInfo() {
        if (!AccountHelper.getInstance().isLogin()) {
            return;
        }
        AccountHelper.getInstance().updateUserInfoByNet();


    }

    @Override
    public void getMessageCount() {

        if (!AccountHelper.getInstance().isLogin()) {
            mView.setMessageUnReadCount(0);
            return;
        }
        submitRequest(mPublicService.getMessageUnRead(), new BaseObserver<MessageResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(MessageResponse messageResponse) {
                ShortcutBadgerManager.getInstance().setNumber(messageResponse.getUnReadCount());
                mView.setMessageUnReadCount(messageResponse.getUnReadCount());
            }

            @Override
            public void onFail(Exception e) {
                //  mView.showToastMsg(e.getMessage());

            }

            @Override
            public void onNext(MessageResponse messageResponse) {
                if (messageResponse.isSuccess()) {
                    onSuccess(messageResponse);
                }

            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void getSignAndIntegral() {
        if (AccountHelper.getInstance().getInfo() == null) {
            return;
        }
        submitRequest(mMainService.getSign(), new BaseSimpleObserver<SignResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onNext(SignResponse signResponse) {
                if (signResponse.isSuccess()) {
                    onSuccess(signResponse);
                }
            }

            @Override
            public void onSuccess(SignResponse signResponse) {
                mView.setSignStatus(signResponse.getData().isSign());
                mView.setUserIntegral(signResponse.getData().getTotalIntegral());
            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }
}
