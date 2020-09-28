package com.nj.baijiayun.module_course.ui.wx.courseDetail;

import com.nj.baijiayun.module_common.mvp.BasePresenter;
import com.nj.baijiayun.module_common.mvp.MultiStateView;
import com.nj.baijiayun.module_public.bean.PublicCourseBean;
import com.nj.baijiayun.module_public.bean.PublicCourseDetailBean;

import java.util.List;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.mvp.contract
 * @describe
 */
public interface OutLineContract {

    interface View extends MultiStateView {
        void setOutLineData(List<Object>data);

        PublicCourseBean getCourseBean();

        void updateSignAndLimitNumber(PublicCourseDetailBean publicCourseDetailBean);
    }

    public abstract class Presenter extends BasePresenter<View>{
       public abstract void getOutLine();
       public abstract void getPublicCourseOutLine();

        public abstract void play(int sectionId, int courseType);

        public abstract void download(int sectionId);
    }
}
