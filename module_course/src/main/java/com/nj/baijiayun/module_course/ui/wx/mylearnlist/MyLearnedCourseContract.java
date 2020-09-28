package com.nj.baijiayun.module_course.ui.wx.mylearnlist;

import com.nj.baijiayun.module_common.mvp.BasePresenter;
import com.nj.baijiayun.module_common.mvp.MultiStateView;
import com.nj.baijiayun.module_course.bean.wx.MyCourseBean;

import java.util.List;

/**
 * @author chengang
 * @date 2019-08-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.mvp.contract
 * @describe
 */
public interface MyLearnedCourseContract {

    interface View extends MultiStateView {
        void setTabs(List<MyCourseBean> data);

        void removeCourseSuccess(int courseId);

//        void setCourseInfo(List<MyCourseBean> data, int index);
    }

    abstract class Presenter extends BasePresenter<View> {
        abstract void getCourseType(int courseType,boolean isHide);

        abstract void removeCourse(int courseId);
    }


}
