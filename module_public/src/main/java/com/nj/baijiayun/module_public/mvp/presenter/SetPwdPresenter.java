package com.nj.baijiayun.module_public.mvp.presenter;

import android.app.Activity;

import com.nj.baijiayun.basic.utils.StringUtils;
import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.consts.ConstsSmsCode;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.mvp.contract.SetPwdContract;

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
public class SetPwdPresenter extends SetPwdContract.Presenter {

    @Inject
    PublicService publicService;


    @Inject
    public SetPwdPresenter() {
    }


    @Override
    public void submit() {
        if (JumpHelper.checkLogin()) {
            return;
        }

        if (StringUtils.isEmpty(mView.getPwd())) {
            mView.showToastMsg(BaseApp.getInstance().getString(R.string.public_check_pwd));
            return;
        }
        if (!mView.getPwd().equals(mView.getConfirmPwd())) {
            mView.showToastMsg("两次密码不一致");
            return;
        }
        if (StringUtils.isEmpty(mView.getCode())) {
            mView.showToastMsg(BaseApp.getInstance().getString(R.string.public_check_code));
            return;
        }


        mView.showLoadV();

        submitRequest(publicService.changePwd(AccountHelper.getInstance().getInfo().getMobile(),
                mView.getPwd(), mView.getCode())
                , new BaseObserver<BaseResponse>() {
                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.closeLoadV();

                        mView.showToastMsg("设置密码成功");
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

    @Override
    public void sendCode() {

        submitRequest(publicService.sendCode(mView.getPhone(), ConstsSmsCode.CODE_LOGIN), new BaseObserver<BaseResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {
//                mView.showToastMsg(baseResponse.getMsg());
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
}
