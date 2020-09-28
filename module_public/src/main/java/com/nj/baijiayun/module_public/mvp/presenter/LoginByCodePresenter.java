package com.nj.baijiayun.module_public.mvp.presenter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.StringUtils;
import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.response.LoginRes;
import com.nj.baijiayun.module_public.consts.ConstsLoginType;
import com.nj.baijiayun.module_public.consts.ConstsSmsCode;
import com.nj.baijiayun.module_public.fragment.LoginByPwdFragment;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.mvp.contract.LoginContract;
import com.nj.baijiayun.module_public.ui.LoginActivity;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import io.reactivex.disposables.Disposable;

/**
 * @author chengang
 * @date 2019-07-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.mvp.presenter
 * @describe
 */
public class LoginByCodePresenter extends LoginContract.BaseLoginByCodePresenter {

    @Inject
    PublicService apiService;


    @Inject
    public LoginByCodePresenter() {

    }


    @Override
    public void login() {
        if (StringUtils.isEmpty(mView.getPhone())) {
            mView.showToastMsg(BaseApp.getInstance().getString(R.string.public_check_phone));
            return;
        }
        if (StringUtils.isEmpty(mView.getCode())) {
            mView.showToastMsg(BaseApp.getInstance().getString(R.string.public_check_code));
            return;
        }


        mView.showLoadV();
        submitRequest(apiService.loginByCode(mView.getPhone(), mView.getCode(), ConstsLoginType.LOGIN_BY_SMS_CODE), new BaseObserver<LoginRes>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);

            }

            @Override
            public void onSuccess(LoginRes loginRes) {
                mView.closeLoadV();
                AccountHelper.getInstance().login(loginRes.getData());
                ((Fragment) mView).getActivity().finish();
                if (AccountHelper.getInstance().getInfo().isNewUser()) {
                    ARouter.getInstance().build(RouterConstant.PAGE_PUBLIC_SET_PWD).withBoolean("isFromAppInner", false).withString("smsCode", mView.getCode()).navigation();

                }

            }

            @Override
            public void onNext(LoginRes loginRes) {
                if (loginRes.isSuccess()) {
                    onSuccess(loginRes);
                } else {
                    onFail(new Exception(loginRes.getMsg()));
                }
            }

            @Override
            public void onFail(Exception e) {
                mView.closeLoadV();
                mView.showToastMsg(e.getMessage());

            }

            @Override
            public void onComplete() {

            }
        });
    }


    @Override
    public void sendCode() {
        if (StringUtils.isEmpty(mView.getPhone())) {
            mView.showToastMsg(BaseApp.getInstance().getString(R.string.public_check_phone));
            mView.stopCountDown();
            return;
        }
        submitRequest(apiService.sendCode(mView.getPhone(), ConstsSmsCode.CODE_LOGIN), new BaseObserver<BaseResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {
//                mView.showToastMsg(baseResponse.getMsg());
            }

            @Override
            public void onNext(BaseResponse baseResponse) {
                if (baseResponse.isSuccess()) {
                    onSuccess(baseResponse);
                } else {
                    onFail(new Exception(baseResponse.getMsg()));
                }
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
    public void loginByPWd() {

        ((LoginActivity) (((Fragment) mView)).getActivity()).start(new LoginByPwdFragment());

    }
}
