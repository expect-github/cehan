package com.nj.baijiayun.module_public.mvp.contract;

import com.nj.baijiayun.module_common.mvp.BasePresenter;
import com.nj.baijiayun.module_common.mvp.BaseView;

/**
 * @author chengang
 * @date 2019-07-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.mvp.contract
 * @describe
 */
public interface SetPwdContract {

    interface View extends BaseView, ICountDown {

        String getPwd();

        String getConfirmPwd();

        String getCode();

        String getPhone();


    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void submit();


        public abstract void sendCode();

    }
}
