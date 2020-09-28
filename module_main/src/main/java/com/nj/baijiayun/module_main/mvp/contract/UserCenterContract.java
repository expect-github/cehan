package com.nj.baijiayun.module_main.mvp.contract;

import com.nj.baijiayun.module_common.mvp.BasePresenter;
import com.nj.baijiayun.module_common.mvp.BaseView;
import com.nj.baijiayun.module_main.bean.wx.UserCenterBean;

/**
 * @author chengang
 * @date 2019-08-06
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.mvp.contract
 * @describe
 */
public interface UserCenterContract {
    interface View extends BaseView {
        void setUserCenterInfo(UserCenterBean userCenterInfo);

        @Deprecated
        void setMessageUnRead(boolean unRead);

        void setUserIntegral(int integral);

        void setSignStatus(boolean isSign);

        void setMessageUnReadCount(int count);

    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getUserCenterInfo();

        public abstract void updatUserInfo();

        public abstract void getMessageCount();

        public abstract void getSignAndIntegral();

    }
}
