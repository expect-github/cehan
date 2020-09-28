package com.nj.baijiayun.module_public.mvp.presenter;

import android.app.Activity;

import com.nj.baijiayun.basic.utils.StringUtils;
import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.consts.ConstsSmsCode;
import com.nj.baijiayun.module_public.mvp.contract.ForgetPwdContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author chengang
 * @date 2019-06-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.mvp.presenter
 * @describe
 */
public class ForgetPwdPresenter extends ForgetPwdContract.Presenter {
    @Inject
    PublicService publicService;

    @Inject
    ForgetPwdPresenter() {
    }

    @Override
    public void sendCode() {

        if (StringUtils.isEmpty(mView.getPhone())) {
            mView.showToastMsg(BaseApp.getInstance().getString(R.string.public_check_phone));
            mView.endCountDown();
            return;
        }

        submitRequest(publicService.sendCode(mView.getPhone(), ConstsSmsCode.CODE_FIND_BACK_PWD), new BaseObserver<BaseResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {

            }

            @Override
            public void onFail(Exception e) {
                mView.showToastMsg(e.getMessage());
                mView.endCountDown();

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
    public void changePwd() {

        if (StringUtils.isEmpty(mView.getPhone())) {
            mView.showToastMsg(BaseApp.getInstance().getString(R.string.public_check_phone));
            return;
        }
        if (StringUtils.isEmpty(mView.getCodeStr())) {
            mView.showToastMsg(BaseApp.getInstance().getString(R.string.public_check_code));
            return;
        }

        if (StringUtils.isEmpty(mView.getNewPwd())) {
            mView.showToastMsg(BaseApp.getInstance().getString(R.string.public_check_pwd));
            return;
        }
//
//        if (StringUtils.isEmpty(mView.getNewPwdAgain())) {
//            mView.showToastMsg(BaseApp.getInstance().getString(R.string.public_check_pwd_again));
//            return;
//        }

        mView.showLoadV();
        submitRequest(publicService.changePwd(mView.getPhone(), mView.getNewPwd(), mView.getCodeStr())
                , new BaseObserver<BaseResponse>() {
                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.closeLoadV();

                        mView.showToastMsg("找回密码成功");
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
