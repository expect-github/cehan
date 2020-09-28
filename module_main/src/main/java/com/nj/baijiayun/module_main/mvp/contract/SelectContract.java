package com.nj.baijiayun.module_main.mvp.contract;

import com.nj.baijiayun.module_common.mvp.BasePresenter;
import com.nj.baijiayun.module_common.mvp.BaseView;
import com.nj.baijiayun.module_main.bean.CourseTypeBean;
import com.nj.baijiayun.module_public.bean.PublicAttrClassifyBean;

import java.util.List;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.mvp.contract
 * @describe
 */
public interface SelectContract {

    interface View extends BaseView {
        void setFilterCourseType(List<CourseTypeBean> data);

        void setFilterAttrs(List<PublicAttrClassifyBean> data);
    }

    abstract class Presenter extends BasePresenter<View> {
        @Deprecated
        public abstract void getCourseType();

        public abstract void getFliter();
        @Deprecated
        public abstract void getFliterInterval();
    }

}


