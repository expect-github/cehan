package com.nj.baijiayun.module_public.mvp.presenter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.StringUtils;
import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.response.LoginRes;
import com.nj.baijiayun.module_public.consts.ConstsLoginType;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.mvp.contract.LoginContract;

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
public class LoginByPwdPresenter extends LoginContract.BaseLoginByPwdPresenter {

    @Inject
    PublicService apiService;

    @Inject
    public LoginByPwdPresenter() {

    }


    @Override
    public void forgetPwd() {
        ARouter.getInstance().build(RouterConstant.PAGE_PUBLIC_FORGET_PWD).withString("phone",mView.getPhone()).navigation();

    }

    @Override
    public void login() {
        if (StringUtils.isEmpty(mView.getPhone())) {
            mView.showToastMsg(BaseApp.getInstance().getString(R.string.public_check_phone));
            return;
        }
        if (StringUtils.isEmpty(mView.getPwd())) {
            mView.showToastMsg(BaseApp.getInstance().getString(R.string.public_check_pwd));
            return;
        }
        mView.showLoadV();

        submitRequest(apiService.login(mView.getPhone(), mView.getPwd(), ConstsLoginType.LOGIN_BY_PWD), new BaseObserver<LoginRes>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSubscribe(Disposable d) { addSubscribe(d);

            }

            @Override
            public void onSuccess(LoginRes loginRes) {
                mView.closeLoadV();
                AccountHelper.getInstance().login(loginRes.getData());
                ((Fragment)mView).getActivity().finish();

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
}
