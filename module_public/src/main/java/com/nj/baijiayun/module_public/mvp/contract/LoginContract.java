package com.nj.baijiayun.module_public.mvp.contract;

import com.nj.baijiayun.module_common.mvp.BasePresenter;
import com.nj.baijiayun.module_common.mvp.BaseView;

/**
 * @author chengang
 * @date 2019-06-10
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_user.ui.login
 * @describe
 */
public interface LoginContract {
    interface View extends BaseView {


        boolean checkAgreeProtocolPass();

    }

    abstract class Presenter extends BasePresenter<View> {


        public abstract void qqLogin();

        public abstract void wechatLogin();

    }




    interface LoginByCodeView extends BaseView {
        String getPhone();

        String getCode();

        void stopCountDown();


    }

    interface LoginByPwdView extends BaseView {
        String getPhone();

        String getPwd();
    }

    abstract class BaseLoginByCodePresenter extends BasePresenter<LoginByCodeView> {

        public abstract void login();

        public abstract void sendCode();

        public abstract void loginByPWd();


    }

    abstract class BaseLoginByPwdPresenter extends BasePresenter<LoginByPwdView> {
        public abstract void forgetPwd();

        public abstract void login();
    }


}
