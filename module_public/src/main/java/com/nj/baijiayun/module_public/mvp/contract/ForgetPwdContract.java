package com.nj.baijiayun.module_public.mvp.contract;

import com.nj.baijiayun.module_common.mvp.BasePresenter;
import com.nj.baijiayun.module_common.mvp.BaseView;

/**
 * @author chengang
 * @date 2019-06-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.mvp.contract
 * @describe
 */
public interface ForgetPwdContract {



    interface View extends BaseView,ICountDown{
        String getNewPwd();

        String getPhone();

        String getNewPwdAgain();

        String getCodeStr();


    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void sendCode();

        public abstract void changePwd();
    }


}
