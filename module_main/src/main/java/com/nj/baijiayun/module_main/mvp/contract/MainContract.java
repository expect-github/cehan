package com.nj.baijiayun.module_main.mvp.contract;

import com.nj.baijiayun.module_common.mvp.BasePresenter;
import com.nj.baijiayun.module_common.temple.IMultiRecyclerView;
import com.nj.baijiayun.module_main.bean.NavBean;
import com.nj.baijiayun.module_main.bean.NewBannerBean;

import java.util.List;

/**
 * @author chengang
 * @date 2019-06-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.mvp
 * @describe
 */
public interface MainContract {

    interface View extends IMultiRecyclerView {
        void setNewBannerData(List<NewBannerBean> bannerData);

        void setListData(List<Object> bannerData);

        @Deprecated
        void setShowMsgPoint(boolean needShow);

        void setNavData(List<NavBean> navData);

        void setShowUnreadCount(int count);

    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getList(boolean isFirst);

        public abstract void getBanner();

        public abstract void getHomeRecommod();

        public abstract void getMessageCount();

        public abstract void getNav();


    }
}
