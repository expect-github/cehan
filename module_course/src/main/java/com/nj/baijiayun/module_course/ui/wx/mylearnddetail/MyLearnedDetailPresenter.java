package com.nj.baijiayun.module_course.ui.wx.mylearnddetail;

import android.app.Activity;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.di.qualifier.Qualifier1;
import com.nj.baijiayun.module_common.di.qualifier.Qualifier2;
import com.nj.baijiayun.module_common.helper.BJYDialogHelper;
import com.nj.baijiayun.module_common.widget.dialog.CommonMDDialog;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.api.CourseService;
import com.nj.baijiayun.module_course.bean.response.MyLearnedDetailResponse;
import com.nj.baijiayun.module_course.bean.wx.CourseQrBean;
import com.nj.baijiayun.module_course.bean.wx.MyLearnedDetailWrapperBean;
import com.nj.baijiayun.module_course.helper.CourseHelper;
import com.nj.baijiayun.module_public.consts.ConstsComment;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.helper.videoplay.VideoPlayHelper;
import com.nj.baijiayun.module_public.helper.videoplay.res.BjyTokensRes;
import com.nj.baijiayun.module_public.p_base.dialog.ShowQrCodeDialog;
import com.nj.baijiayun.module_public.widget.dialog.CommentDialog;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author chengang
 * @date 2019-08-04
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx.mylearnddetail
 * @describe
 */
public class MyLearnedDetailPresenter extends MyLearnedDetailContract.Presenter {

    @Qualifier1
    @Inject
    int mCourseId;
    @Qualifier2
    @Inject
    int mCourseType;
    @Inject
    CourseService mCourseService;
    private boolean isFromOrder;
    private List outLineResult;
    private MyLearnedDetailWrapperBean.Course mCourseBean;
    private CommentDialog commentDialog;
    private CourseQrBean courseQrBean;


    @Inject
    public MyLearnedDetailPresenter() {


    }

    @Override
    public boolean isNeedPlayer() {
        return CourseHelper.isShowPlayer(mCourseType);

    }

    @Override
    public void comment() {
        if (mCourseBean == null) {
            return;
        }

        if (commentDialog == null) {
            commentDialog = new CommentDialog((Activity) mView)
                    .setOnDissMissListener((comment, rate) -> {
                        mCourseBean.setCommentContent(comment);
                        mCourseBean.setGrade(rate);
                    })
                    .setCommentCommitListener((comment, rate) -> submitCommentRequest(comment, rate));
        }
        commentDialog.show();
        commentDialog.setCommented(mCourseBean.isCommented(), mCourseBean.getCommentContent(), mCourseBean.getGrade());


    }

    private void submitCommentRequest(String comment, int rate) {
        mView.showLoadV();

        submitRequest(mCourseService.addCourseComment(rate, comment, mCourseId, ConstsComment.COURSE), new BaseObserver<BaseResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {
                mView.closeLoadV();
                mView.showToastMsg(R.string.course_comment_success);
                if (mCourseBean != null) {
                    mCourseBean.setCommentSuccess();
                }
                updateCommentText();
                LiveDataBus.get().with(LiveDataBusEvent.COURSE_COMMENT_SUCCESS).postValue(mCourseId);

            }

            @Override
            public void onFail(Exception e) {
                mView.showToastMsg(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                mView.closeLoadV();
                addSubscribe(d);

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void removeList() {

        if (mCourseBean == null) {
            return;
        }

        final CommonMDDialog mdDialog = BJYDialogHelper.buildMDDialog((Activity) mView);


        mdDialog.setContentTxt(isFromOrder ? ((Activity) mView).getString(R.string.course_remove_content_buy_again)
                : ((Activity) mView).getString(R.string.course_remove_content_join_learn)
        ).setNegativeTxt("再想想")
                .setPositiveTxt("确定移除")
                .setOnNegativeClickListener(() -> {
                    mdDialog.dismiss();
                }).setOnPositiveClickListener(() -> {
            submitRemoveReq(mdDialog);

        }).show();

    }

    private void submitRemoveReq(CommonMDDialog mdDialog) {
        submitRequest(mCourseService.removeLearnedCourse(mCourseId), new BaseObserver<BaseResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {
                mView.showToastMsg(R.string.course_remove_success);
                mdDialog.dismiss();
                LiveDataBus.get().with(LiveDataBusEvent.REMOVE_COURSE_SUCCESS).postValue(mCourseId);
                mView.removeCourseSuccess();
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
    public void goCourseDetail() {
        ARouter.getInstance().build(RouterConstant.PAGE_COURSE_DETAIL).withInt("courseId", mCourseId).navigation();
    }

    @Override
    public void getMyLearnedDetail() {
        submitRequest(mCourseService.getMyLearnedDetail(mCourseId), new BaseObserver<MyLearnedDetailResponse>() {


            @Override
            public void onPreRequest() {
                mView.showLoadView();
            }

            @Override
            public void onSuccess(MyLearnedDetailResponse myLearnedDetailResponse) {
                getCourseQrCode();
                mCourseBean = myLearnedDetailResponse.getData().getCourse();
                isFromOrder = myLearnedDetailResponse.getData().getCourse().isFromOrder();
                outLineResult = myLearnedDetailResponse.getData().getResult();
                updateCommentText();
                mView.setInfo(myLearnedDetailResponse.getData());
                mView.selectLastLearnPosition(myLearnedDetailResponse.getData().getLastStudyChapterId());
                mView.showContentView();
                mView.setCourseHideStatus(mCourseBean.isHide());
            }

            @Override
            public void onFail(Exception e) {
                mView.showToastMsg(e.getMessage());
                mView.showErrorDataView();
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

    private void updateCommentText() {
        if (mCourseBean != null) {
            mView.setCommentBtnText(mCourseBean.isCommented() ? "查看评价" : "写评价");
        }
    }


    @Override
    public void downloadSection(int sectionId) {
        getTokenAndDoSomething(sectionId, false, false);

    }

    @Override
    public void playSectionByCurrentPagePlayer(int sectionId) {
        if (isPlayLastSectionByPlayer(sectionId)) {
            return;
        }
        getTokenAndDoSomething(sectionId, false, true);

    }

    @Override
    public void playSectionByBjySdk(int sectionId) {
        getTokenAndDoSomething(sectionId, true, false);
    }

    @Override
    public boolean isCourseLimit() {
        return mCourseBean.isLimit();
    }

    @Override
    public void getCourseQrCode() {
        submitRequest(mCourseService.getCourseQr(mCourseId), new BaseSimpleObserver<BaseResponse<CourseQrBean>>() {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onSuccess(BaseResponse<CourseQrBean> courseQrBeanBaseResponse) {
                courseQrBean = courseQrBeanBaseResponse.getData();
                if (courseQrBean.isShowQrCode()) {
                    mView.setQrCodeUi(courseQrBean.getCourseQrcodeTitle());
                }
            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }

    @Override
    public void showQrCodeDialog() {
        if (courseQrBean == null) {
            return;
        }
        new ShowQrCodeDialog((Context) mView, courseQrBean.getCourseQrcodeImg()).hideTopCover().show();
    }

    @Override
    public List getListData() {
        return outLineResult;
    }

    @Override
    public boolean isNotCanStudy() {
        return !(mCourseBean != null && mCourseBean.isCanStudy());
    }

    @Override
    public void hideOrRecoverCourse() {
        if (mCourseBean.isHide()) {
            hideCourse(mCourseId, mCourseBean.isHide());
        } else {
            CommonMDDialog mdDialog = BJYDialogHelper.buildMDDialog((Context) mView);
            mdDialog
                    .hideTitle()
                    .setContentTxt(((Context) mView).getString(R.string.course_hide_course_hint))
                    .setPositiveTxt(R.string.public_i_known).setOnPositiveClickListener(() -> {
                hideCourse(mCourseId, mCourseBean.isHide());
                mdDialog.dismiss();
            }).show();

        }
    }

    private boolean isPlayLastSectionByPlayer(int sectionId) {
        return lastPlaySectionId == sectionId;
    }

    private int lastPlaySectionId;

    private void getTokenAndDoSomething(int sectionId, boolean isPlay, boolean isUseLocalPlayer) {
        submitRequest(mCourseService.getBjyVideoToken(String.valueOf(sectionId))
                , new BaseObserver<BjyTokensRes>() {
                    @Override
                    public void onPreRequest() {
                        mView.showLoadV();
                    }

                    @Override
                    public void onSuccess(BjyTokensRes bjyTokensRes) {
                        VideoPlayHelper.wrapperBjyTokenBean(bjyTokensRes.getData().getTokenData(), mCourseId, sectionId);
                        mView.closeLoadV();
                        if (isUseLocalPlayer) {
                            lastPlaySectionId = sectionId;
                            mView.playVideo(bjyTokensRes.getData().getTokenData().getToken(), bjyTokensRes.getData().getTokenData().getVideo_id());
                        } else if (isPlay) {
                            VideoPlayHelper.playVideo(bjyTokensRes.getData(), mCourseBean.getCourseType());
                        } else {
                            VideoPlayHelper.download(
                                    (Activity) mView
                                    , bjyTokensRes.getData().getTokenData()
                                    , CourseHelper.getIDownloadInfo(sectionId, outLineResult)
                                    , CourseHelper.getICourseInfo(mCourseBean)
                            );
                        }
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

    private void hideCourse(int courseId, boolean isHide) {
        mView.showLoadV();
        submitRequest(mCourseService.hideOrRecoverCourse(courseId), new BaseObserver<BaseResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {
                mView.closeLoadV();
                mView.setCourseHideStatus(!isHide);
                mCourseBean.setHide(!isHide);
                if (isHide) {
                    ToastUtil.show(R.string.course_cancel_hide_success);
                } else {
                    mView.removeCourseSuccess();
                }
                LiveDataBus.get().with(LiveDataBusEvent.COURSE_HIDE_RECOVER).postValue(mCourseId);


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

}
