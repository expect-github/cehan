package com.nj.baijiayun.module_course.ui.wx.courseDetail;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.JsonElement;
import com.nj.baijiayun.basic.rxlife.RxLife;
import com.nj.baijiayun.basic.utils.ClickUtils;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.di.qualifier.Qualifier1;
import com.nj.baijiayun.module_common.helper.BJYDialogHelper;
import com.nj.baijiayun.module_common.helper.RxJavaHelper;
import com.nj.baijiayun.module_common.widget.dialog.CommonMDDialog;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.api.CourseService;
import com.nj.baijiayun.module_course.bean.CourseDetailWrapperBean;
import com.nj.baijiayun.module_course.bean.response.AssembleCourseInfoResponse;
import com.nj.baijiayun.module_course.bean.response.AssembleJoinInfoResponse;
import com.nj.baijiayun.module_course.bean.response.CourseDetailResponse;
import com.nj.baijiayun.module_course.bean.response.ShareResponse;
import com.nj.baijiayun.module_course.bean.wx.AssembleCourseBean;
import com.nj.baijiayun.module_course.helper.CourseHelper;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.PublicCourseDetailBean;
import com.nj.baijiayun.module_public.consts.ConstsCollect;
import com.nj.baijiayun.module_public.consts.ConstsCourseParams;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.helper.JumpHelper;

import org.json.JSONObject;

import java.text.MessageFormat;

import javax.inject.Inject;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx.courseDetail
 * @describe
 */
public class CourseDetailPresenter extends CourseDetailContract.Presenter {
    public static final int SHARE_FROM_NORMAL = 0;
    public static final int SHARE_FROM_DISTRIBUTION = 1;

    @Inject
    CourseService mCourseServices;
    @Inject
    PublicService mPublicService;

    @Inject
    int mCourseId;
    @Qualifier1
    @Inject
    int mSystemCourseId;

    private boolean isDistribution = false;

    private PublicCourseDetailBean mCourseDetailBean;

    @Inject
    public CourseDetailPresenter() {

    }

    @Override
    public void getDetail() {
        submitRequest(mCourseServices.getCourseDetail(mCourseId), new BaseObserver<CourseDetailResponse>() {
            @Override
            public void onPreRequest() {
                mView.showLoadView();
            }

            @Override
            public void onSuccess(CourseDetailResponse courseDetailResponse) {
                if (!CourseHelper.isCanStudy(courseDetailResponse.getData().getInfo())) {
                    mView.showNoDataView();
                    return;
                }
//                mView.showContentView();
                getAssembleCourseInfo();
                setCourseInfo(courseDetailResponse);


            }

            private void setCourseInfo(CourseDetailResponse courseDetailResponse) {
                CourseDetailWrapperBean data = courseDetailResponse.getData();
                mCourseDetailBean = data.getInfo();
                if (mCourseDetailBean == null) {
                    onFail(new Exception("获取课程错误"));
                    return;
                }
                data.getInfo().setTeachers(data.getTeachers());
                mView.setTab(mCourseDetailBean.getCourseType());
                mView.setInfo(data.getCouponList(), data.getInfo());
                isDistribution = data.getDistributionBean() != null;
                mView.setShareProfit(data.getDistributionBean());
                setBottomBtnTxt(data.getInfo());
            }


            @Override
            public void onFail(Exception e) {
                mView.showErrorDataView();
                mView.showToastMsg(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void setBottomBtnTxt(PublicCourseDetailBean info) {
        mView.setBottomBtnTxt(getBottomBtnShowText(info), info.isVipCourse() && AccountHelper.getInstance().isVip());
    }


    private String getBottomBtnShowText(PublicCourseDetailBean info) {

        if (info.isJoinStudy()) {
            return "立即学习";
        } else {
            if (info.isBuy() || (info.isVipCourse() && AccountHelper.getInstance().isVip())) {
                return "加入学习";
            }
            return "立即报名";
        }
    }


    @Override
    public void collect(boolean collect) {
        submitRequest(collect ? mPublicService.collect(mCourseId, 0, ConstsCollect.COURSE) :
                mPublicService.cancelCollect(mCourseDetailBean.getCollectId(), ConstsCollect.COURSE), new BaseObserver<BaseResponse<JsonElement>>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(BaseResponse<JsonElement> baseResponse) {
                mView.collectStateChange(mCourseId, collect);
                mView.showToastMsg(collect ? "收藏成功" : "取消收藏成功");
                if (collect) {
                    mCourseDetailBean.setCollectId(baseResponse.getData().getAsInt());
                }
                LiveDataBus.get().with(LiveDataBusEvent.COLLECTION_STATUES_CHANGE).postValue(true);
            }

            @Override
            public void onFail(Exception e) {
                mView.showToastMsg(e.getMessage());

            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void btnConfirm() {
        if (ClickUtils.isFastDoubleClick()) {
            return;
        }
        if (mCourseDetailBean == null) {
            return;
        }
        if (mCourseDetailBean.isJoinStudy()) {
            if (ConstsCouseType.isSystem(mCourseDetailBean.getCourseType())) {
                mView.jumpSystemCourseFirst();
                return;
            }

            ARouter.getInstance().build(RouterConstant.PAGE_COURSE_MY_LEARNED_DETAIL)
                    .withInt("courseType", mCourseDetailBean.getCourseType())
                    .withInt("courseId", mCourseId).navigation();

        } else {
            joinLearned();
        }


//        if (mCourseDetailBean.isBuyOrAddJoin()) {
//
//            if (mCourseDetailBean.isBuy() && !mCourseDetailBean.isJoinStudy()) {
//                hasBuyJoinLearnedList();
//            }
//            if (ConstsCouseType.isSystem(mCourseDetailBean.getCourseType())) {
//                mView.jumpSystemCourseFirst();
//                return;
//            }
//
//            ARouter.getInstance().build(RouterConstant.PAGE_COURSE_MY_LEARNED_DETAIL)
//                    .withInt("courseType", mCourseDetailBean.getCourseType())
//                    .withInt("courseId", mCourseId).navigation();
//        } else {
//            joinLearned();
//        }

    }

    private void hasBuyJoinLearnedList() {
        mView.showLoadV();
        submitRequest(mCourseServices.joinLearnedList(mCourseId, 0, ConstsCourseParams.JOIN_FROM_NORMAL_DETAIL, mCourseDetailBean.getCourseType())
                , new BaseObserver<BaseResponse>() {
                    @Override
                    public void onPreRequest() {


                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        mView.closeLoadV();
                        hasBuyJoinLearnedSuccess();

                    }

                    @Override
                    public void onFail(Exception e) {
                        mView.closeLoadV();
                        mView.showToastMsg(e.getMessage());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void hasBuyJoinLearnedSuccess() {
        mView.showToastMsg("加入成功");
        mCourseDetailBean.setJoinSuccess();
        setBottomBtnTxt(mCourseDetailBean);

    }

    @Override
    public void joinLearned() {
        if (JumpHelper.checkLogin()) {
            return;
        }
        if (mCourseDetailBean == null) {
            return;
        }
        if (CourseHelper.isNeedJudgeStockNum(mCourseDetailBean.getCourseType())) {
            if (mCourseDetailBean.isNoStock()) {
                mView.showToastMsg("你来晚了哦,名额已经没有了~");
                return;
            }
        }
        //检查vip课程
//        if (mCourseDetailBean.isVipCourse() && mCourseDetailBean.isVipUser()) {
//            vipJoinLearned();
//        } else if (mCourseDetailBean.isBuy()) {
//            hasBuyJoinLearnedList();
//        } else if (mCourseDetailBean.isFree()) {
//            freeJoinLearned();
//        } else {
//            buyJoinLearned();
//        }
        if (mCourseDetailBean.isBuy()) {
            hasBuyJoinLearnedList();
        } else {
            buyJoinLearned();
        }


    }

    @Override
    public void vipJoinStudySuccess(int courseId) {
        if (courseId != mCourseId || mCourseDetailBean == null) {
            return;
        }
        mCourseDetailBean.joinLearnedSuccess();
        updateUi();

    }

    private void updateUi() {
        Logger.d("TAg----updateUi");
        setBottomBtnTxt(mCourseDetailBean);
        mView.refreshSignUpInfo(mCourseDetailBean);
    }

    @Override
    public void buySuccess(int courseId) {

        if (courseId != mCourseId || mCourseDetailBean == null) {
            return;

        }
        mCourseDetailBean.setBuySuccess();
        updateUi();


    }

    private void vipJoinLearned() {
        mView.showLoadV();
        //1代表来源于课程详情
        submitRequest(mCourseServices.joinLearnedList(mCourseId, 0, ConstsCourseParams.JOIN_FROM_VIP_DETAIL, mCourseDetailBean.getCourseType()), new BaseObserver<BaseResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {
                mView.closeLoadV();
                joinLearnedSuccess();


            }


            @Override
            public void onFail(Exception e) {
                mView.closeLoadV();
                mView.showToastMsg(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void buyJoinLearned() {
        //跳转加入学习
        if (mCourseDetailBean == null) {
            return;
        }
        JumpHelper.jumpBuyCourse(mCourseId, mCourseDetailBean.getCourseType());


    }

    private void freeJoinLearned() {
        mView.showLoadV();
        submitRequest(mCourseServices.freeCourseJoinLearnedList(mCourseId, mCourseDetailBean.getCourseType()), new BaseObserver<BaseResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {
                mView.closeLoadV();
                joinLearnedSuccess();

            }

            @Override
            public void onFail(Exception e) {
                mView.closeLoadV();
                mView.showToastMsg(e.getMessage());

            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void joinLearnedSuccess() {
        //发送通知
        LiveDataBus.get().with(LiveDataBusEvent.COURSE_HAS_BUY_SUCCESS_BY_JOIN_OR_FREE).postValue(mCourseId);
        mView.showToastMsg((mCourseDetailBean.isVipCourse() && mCourseDetailBean.isVipUser()) ? "加入成功" : "报名成功");
        if (mCourseDetailBean.isVipCourse() && mCourseDetailBean.isVipUser()) {
            vipJoinStudySuccess(mCourseId);
        } else {
            buySuccess(mCourseId);
        }
//        setBottomBtnTxt(mCourseDetailBean);
//        mView.setBottomBtnTxt(getBottomBtnShowText(mCourseDetailBean), mCourseDetailBean.isVipCourse());

    }


    @Override
    public void getShareInfo(int form) {
        submitRequest(mCourseServices.getShareData(mCourseId, form), new BaseObserver<ShareResponse>() {
            @Override
            public void onPreRequest() {
                mView.showLoadV();

            }

            @Override
            public void onSuccess(ShareResponse shareResponse) {
                mView.closeLoadV();
                mView.showShare(shareResponse.getData(), form);
            }

            @Override
            public void onFail(Exception e) {
                mView.closeLoadV();
                mView.showToastMsg(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        });
    }


    private AssembleCourseBean assembleCourseBean;

    @Override
    public void getAssembleCourseInfo() {
        mView.showLoadView();
        //每次请求设置为null
        assembleCourseBean = null;
        isSetBaseInfo = false;
        mCourseServices.getAssembleCourseInfo(mCourseId)
                .flatMap((Function<AssembleCourseInfoResponse, Observable<AssembleJoinInfoResponse>>) assembleCourseInfoResponse -> {
                    assembleCourseBean = assembleCourseInfoResponse.getData();
                    //拼团活动为null 或者不限时拼团列表 不继续下去
                    return (assembleCourseBean == null || !assembleCourseBean.needShowAssembleGroup()) ?
                            Observable.just(new AssembleJoinInfoResponse()) :
                            mCourseServices.getAssembleJoinInfo(assembleCourseBean.getId());
                }).compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .subscribe(new BaseSimpleObserver<AssembleJoinInfoResponse>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        addSubscribe(d);
                    }

                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onSuccess(AssembleJoinInfoResponse assembleJoinInfoResponse) {
                        // 参团了:开团成功不显示 或者 没有参团:购买了课程 不显示 其他情况显示 需要显示参团
                        mView.showContentView();
                        setAssembleBaseInfo();
                        if (assembleJoinInfoResponse.getData() != null) {
                            mView.setJoinInfo(assembleJoinInfoResponse.getData().getList(),
                                    assembleCourseBean.getJoinNum(),
                                    needShowAssembleInfo && assembleCourseBean.needShowAssembleGroup());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        mView.showContentView();
                        if (assembleCourseBean != null) {
                            setAssembleBaseInfo();
                        }
                    }
                });


    }

    private boolean needShowAssembleInfo;
    private boolean isSetBaseInfo;


    private void setAssembleBaseInfo() {
        //已经设置了就不需要再设置
        if (isSetBaseInfo) {
            return;
        }
        boolean isNotJoinAssembleButHasBuy = !assembleCourseBean.isJoinSpell() && mCourseDetailBean.isBuyOrAddJoin();
        needShowAssembleInfo = !(assembleCourseBean.isAssembleSuccess() || isNotJoinAssembleButHasBuy);
        mView.showAssembleAction(needShowAssembleInfo);
        if (needShowAssembleInfo) {
            mView.setAssemnleInfo(assembleCourseBean);
        }
        mView.setAssembleActionUi(
                assembleCourseBean.isJoinSpell()
                , assembleCourseBean.getStock() - assembleCourseBean.getSalesNum()
                , assembleCourseBean.getPrice()
                , assembleCourseBean.getOpenAssemblePrice()
                , assembleCourseBean.getJoinNum());
//        mView.setShowCouponByAssemble(assembleCourseBean.needShowCoupon());
        isSetBaseInfo = true;
    }

    @Override
    public void singleBuy() {
        btnConfirm();
    }

    @Override
    public void assembleBuy() {
        if (assembleCourseBean == null) {
            return;
        }
        if (assembleCourseBean.isJoinSpell()) {
            goMyAssemble(String.valueOf(assembleCourseBean.getJoinGroupId()));
        } else {
            assembleJoinGroup(0);
        }

    }

    @Override
    public void assembleJoinGroup(int groupId) {
        if (JumpHelper.checkLogin()) {
            return;
        }
        //groupId !=0 表示去参加团
        if (groupId != 0 && assembleCourseBean.isJoinSpell()) {
            CommonMDDialog commonMDDialog = BJYDialogHelper.buildMDDialog((Context) mView);
            commonMDDialog.setContentTxt(((Context) mView).getString(R.string.course_not_allow_again_join));
            commonMDDialog
                    .hideTitle()
                    .setNegativeTxt("随便逛逛")
                    .setPositiveTxt("查看我的团")
                    .setOnNegativeClickListener(() -> {
                        ARouter.getInstance().build(RouterConstant.PAGE_ASSEMBLE).navigation();
                        commonMDDialog.dismiss();
                    })
                    .setOnPositiveClickListener(() -> {
                        goMyAssemble(String.valueOf(groupId));
                        commonMDDialog.dismiss();
                    });
            commonMDDialog.show();
            return;
        }

        mView.showLoadV();
        NetMgr.getInstance()
                .getDefaultRetrofit()
                .create(CourseService.class)
                .checkAssembleJoin(assembleCourseBean.getId(), groupId)
                .compose(RxJavaHelper.subscribeOnIoObserveOnMain())
                .as(RxLife.as((LifecycleOwner) mView))
                .subscribe(new BaseSimpleObserver<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        addSubscribe(d);
                    }

                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onSuccess(BaseResponse baseResponse) {
                        JumpHelper.jumpAssemblePay(mCourseId, mCourseDetailBean.getCourseType(), assembleCourseBean.getId(), groupId);
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        mView.closeLoadV();
                        if (baseResponse.isSuccess()) {
                            onSuccess(baseResponse);
                            //这里301 203 返回很随意 也不想写成静态变量了
                            //301弹出提示
                        } else if (baseResponse.getStatus() == 301) {
                            //面向测试编程，需求变了 都不知道 也没有人通知， web端加逻辑，也没有人通知
                            CommonMDDialog commonMDDialog = BJYDialogHelper.buildMDDialog((Context) mView);
                            commonMDDialog.setContentTxt(baseResponse.getMsg());
                            commonMDDialog.hideTitle().setPositiveTxt("我知道了").setOnPositiveClickListener(() -> commonMDDialog.dismiss());
                            commonMDDialog.show();

                        } else if (baseResponse.getStatus() == 203) {
                            //这个奇葩的处理没见过吧 我也是第一次这么写，嘿嘿
                            try {
                                JSONObject jsonObject = new JSONObject(baseResponse.getMsg());
                                mView.showToastMsg(jsonObject.optString("msg"));
                                String data = jsonObject.optString("data");
                                JSONObject dataObject = new JSONObject(data);
                                final String orderId = dataObject.getString("order_id");
                                new android.os.Handler().postDelayed(() -> JumpHelper.jumpOrderDetail(orderId, mCourseDetailBean.getCourseType()), 1000);
                            } catch (Exception ee) {
                                Logger.e(ee.getMessage());
                            }
                            // 别删
//                            CommonMDDialog commonMDDialog = BJYDialogHelper.buildMDDialog((Context) mView);
//                            commonMDDialog.setContentTxt(baseResponse.getMsg());
//                            commonMDDialog.setTitleTxt("拼团中").setNegativeTxt("随便逛逛")
//                                    .setPositiveTxt("查看我的团")
//                                    .setOnNegativeClickListener(() -> ARouter.getInstance().build(RouterConstant.PAGE_ASSEMBLE).navigation())
//                                    .setOnPositiveClickListener(() -> {
//                                        goMyAssemble(String.valueOf(groupId));
//                                        commonMDDialog.dismiss();
//                                    });
//                            commonMDDialog.show();
                        } else {
                            super.onNext(baseResponse);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        mView.closeLoadV();
                        ToastUtil.shortShow((Context) mView, e.getMessage());

                    }
                });

    }

    @Override
    public boolean checkIsCurrentCourse(int course) {
        return mCourseId == course;
    }

    @Override
    public PublicCourseDetailBean getCourseBean() {
        return mCourseDetailBean;
    }

    @Override
    public int getLastPageSystemCourseId() {
        return mSystemCourseId;
    }

    @Override
    public boolean isDistribution() {
        return isDistribution;
    }

    @Override
    public void goDistributionSharePage() {
        JumpHelper.jumpWebViewNoNeedAppTitle(MessageFormat.format("distribute/invite/post?id={0}&dis_type=0&source=1", String.valueOf(mCourseId)));
    }

    private void goMyAssemble(String groupId) {
        JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getAssembleDetail(groupId));
//        ARouter.getInstance().build(RouterConstant.PAGE_ASSEMBLE).withBoolean("needSelectMyAssemble", true).navigation();

    }

    @Override
    public boolean isCourseLimit() {
        return mCourseDetailBean.isLimit();
    }

    @Override
    public boolean isJoinSpell() {
        if (assembleCourseBean != null) {
            return assembleCourseBean.isJoinSpell();
        }
        return false;
    }

    @Override
    public boolean isSpellSuceess() {
        if (assembleCourseBean != null) {
            return assembleCourseBean.isAssembleSuccess();
        }
        return false;
    }


}
