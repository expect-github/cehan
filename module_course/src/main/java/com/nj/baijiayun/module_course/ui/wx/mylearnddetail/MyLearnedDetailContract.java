package com.nj.baijiayun.module_course.ui.wx.mylearnddetail;

import com.nj.baijiayun.module_common.mvp.BasePresenter;
import com.nj.baijiayun.module_common.mvp.MultiStateView;
import com.nj.baijiayun.module_course.bean.wx.MyLearnedDetailWrapperBean;

import java.util.List;

/**
 * @author chengang
 * @date 2019-08-04
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx.mylearnddetail
 * @describe
 */
public interface MyLearnedDetailContract {

    interface View extends MultiStateView {

        void setInfo(MyLearnedDetailWrapperBean myLearnedDetailWrapperBean);

        void playVideo(String token, String videoId);

        void setCommentBtnText(String text);

        void selectLastLearnPosition(int sectionId);

        void removeCourseSuccess();

        void setQrCodeUi(String title);

        void setCourseHideStatus(boolean isHide);


    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract boolean isNeedPlayer();

        public abstract void comment();

        public abstract void removeList();

        public abstract void goCourseDetail();

        public abstract void getMyLearnedDetail();

        public abstract void downloadSection(int sectionId);

        public abstract void playSectionByCurrentPagePlayer(int sectionId);

        public abstract void playSectionByBjySdk(int sectionId);

        public abstract boolean isCourseLimit();

        public abstract void getCourseQrCode();

        public abstract void showQrCodeDialog();

        public abstract List getListData();

        public abstract boolean isNotCanStudy();

        public abstract void hideOrRecoverCourse();


    }
}
