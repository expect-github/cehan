package com.nj.baijiayun.module_course.ui.wx.courseDetail;

import com.nj.baijiayun.module_common.mvp.BasePresenter;
import com.nj.baijiayun.module_common.mvp.MultiStateView;
import com.nj.baijiayun.module_course.bean.wx.AssembleCourseBean;
import com.nj.baijiayun.module_course.bean.wx.AssembleJoinInfoBean;
import com.nj.baijiayun.module_public.bean.PublicCouponBean;
import com.nj.baijiayun.module_public.bean.PublicCourseDetailBean;
import com.nj.baijiayun.module_public.bean.PublicDistributionBean;
import com.nj.baijiayun.module_public.helper.share_login.ShareInfo;

import java.util.List;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx.courseDetail
 * @describe
 */
public interface CourseDetailContract {

    interface View extends MultiStateView {

        void setInfo(List<PublicCouponBean> couponInfo, PublicCourseDetailBean publicCourseDetailBean);

        void collectStateChange(int id, boolean collectState);

        void setBottomBtnTxt(String text, boolean showViplogo);

        void showShare(ShareInfo shareInfo,int form);

        void refreshSignUpInfo(PublicCourseDetailBean publicCourseDetailBean);

        void jumpSystemCourseFirst();

        void setJoinInfo(List<AssembleJoinInfoBean> datas, int assembleJoinNumber, boolean needShow);

        void showAssembleAction(boolean isNeedShowAssembleAction);

        void setAssembleActionUi(boolean isJoinedAssemble,
                                 int stockNumber, String price, String assemblePrice, int userNumber);

        void setAssemnleInfo(AssembleCourseBean assemnleInfo);

        void setShowCouponByAssemble(boolean isShow);

        void setTab(int courseType);

        void setShareProfit(PublicDistributionBean data);

    }


    abstract class Presenter extends BasePresenter<View> {
        public abstract void getDetail();

        public abstract void collect(boolean collect);

        public abstract void btnConfirm();

        public abstract void joinLearned();

        public abstract void vipJoinStudySuccess(int courseId);

        public abstract void buySuccess(int courseId);

        public abstract void getShareInfo(int form);

        public abstract void getAssembleCourseInfo();

        public abstract void singleBuy();

        public abstract void assembleBuy();

        public abstract void assembleJoinGroup(int groupId);

        public abstract boolean checkIsCurrentCourse(int course);

        public abstract PublicCourseDetailBean getCourseBean();

        public abstract int getLastPageSystemCourseId();

        public  abstract boolean isDistribution();

        public abstract void goDistributionSharePage();

        public abstract boolean isCourseLimit();

        public abstract boolean isJoinSpell();

        public abstract boolean isSpellSuceess();



    }
}
