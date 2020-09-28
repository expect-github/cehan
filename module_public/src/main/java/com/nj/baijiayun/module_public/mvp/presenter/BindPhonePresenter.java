package com.nj.baijiayun.module_public.mvp.presenter;

import android.app.Activity;

import com.nj.baijiayun.basic.manager.AppManager;
import com.nj.baijiayun.basic.utils.StringUtils;
import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.response.LoginRes;
import com.nj.baijiayun.module_public.consts.ConstsSmsCode;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.mvp.contract.BindPhoneContract;
import com.nj.baijiayun.module_public.ui.BindPhoneActivity;
import com.nj.baijiayun.module_public.ui.LoginActivity;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author chengang
 * @date 2019-07-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.mvp.presenter
 * @describe
 */
public class BindPhonePresenter extends BindPhoneContract.Presenter {

    @Inject
    PublicService publicService;
    @Inject
    String openId;
    @Inject
    int loginType;

    @Inject
    BindPhonePresenter() {
    }


    @Override
    public void sendCode() {

        if (StringUtils.isEmpty(mView.getPhone())) {
            mView.showToastMsg(BaseApp.getInstance().getString(R.string.public_check_phone));
            mView.stopCountDown();
            return;
        }


        submitRequest(publicService.sendCode(mView.getPhone(), ConstsSmsCode.CODE_OAUTH), new BaseObserver<BaseResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {
                mView.showToastMsg(((BindPhoneActivity) mView).getString(R.string.public_send_sms_code_success));
            }

            @Override
            public void onFail(Exception e) {
                mView.showToastMsg(e.getMessage());
                mView.stopCountDown();

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
    public void confirm() {
        if (StringUtils.isEmpty(mView.getPhone())) {
            mView.showToastMsg(BaseApp.getInstance().getString(R.string.public_check_phone));
            return;
        }
        if (StringUtils.isEmpty(mView.getCode())) {
            mView.showToastMsg(BaseApp.getInstance().getString(R.string.public_check_code));
            return;
        }
        if (StringUtils.isEmpty(mView.getPwd())) {
            mView.showToastMsg(BaseApp.getInstance().getString(R.string.public_check_pwd));
            return;
        }
        mView.showLoadV();
        submitRequest(publicService.loginByThirdPlatform(mView.getPhone(), mView.getCode(), openId, loginType, mView.getPwd()), new BaseObserver<LoginRes>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(LoginRes loginRes) {
                mView.closeLoadV();
                mView.showToastMsg(((Activity) mView).getString(R.string.public_bind_phone_success));
                AccountHelper.getInstance().login(loginRes.getData());
                if (AppManager.getAppManager().isExist(LoginActivity.class)) {
                    AppManager.getAppManager().finishActivity(LoginActivity.class);
                }
                ((Activity) mView).finish();
            }

            @Override
            public void onFail(Exception e) {
                mView.closeLoadV();
                mView.showToastMsg(e.getMessage());

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
}
