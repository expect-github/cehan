package com.nj.baijiayun.module_course.ui.wx.courseDetail;

import android.app.Activity;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_course.api.CourseService;
import com.nj.baijiayun.module_course.bean.response.OutLineResponse;
import com.nj.baijiayun.module_course.bean.response.SystemCourseListResponse;
import com.nj.baijiayun.module_course.helper.CourseHelper;
import com.nj.baijiayun.module_public.bean.PublicCourseBean;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.helper.videoplay.VideoPlayHelper;
import com.nj.baijiayun.module_public.helper.videoplay.res.BjyTokensRes;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.mvp.presenter
 * @describe
 */
public class OutLinePresenter extends OutLineContract.Presenter {
    @Inject
    CourseService mCourseService;
    @Inject
    int courseId;

    @Inject
    public OutLinePresenter() {

    }


    @Override
    public void getOutLine() {
        submitRequest(mCourseService.getCourseOutLine(courseId), new BaseObserver<OutLineResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(OutLineResponse outLineResponse) {
                if (mView == null) {
                    return;
                }
                if (outLineResponse.getData() != null) {
                    mView.setOutLineData(outLineResponse.getData().getResult());
                } else {
                    mView.setOutLineData(new ArrayList<>());
                }
            }

            @Override
            public void onFail(Exception e) {

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

    List<Object> outLineResultList = new ArrayList<>();

    @Override
    public void getPublicCourseOutLine() {
        submitRequest(mCourseService.getSystemCourseList(courseId), new BaseObserver<SystemCourseListResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(SystemCourseListResponse systemCourseListResponse) {
                List<PublicCourseBean> data = systemCourseListResponse.getData();
                outLineResultList.clear();
                outLineResultList.addAll(data);
                mView.setOutLineData(outLineResultList);

            }

            @Override
            public void onFail(Exception e) {

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
    public void play(int sectionId, int courseType) {
        getToken(sectionId, true, courseType);
    }

    @Override
    public void download(int sectionId) {
        getToken(sectionId, false, -1);
    }


    public void getToken(int sectionId, boolean isPlay, int courseType) {
        if (AccountHelper.getInstance().getInfo() == null) {
            ARouter.getInstance().build(RouterConstant.PAGE_LOGIN).navigation();
            return;
        }
        submitRequest(mCourseService.getBjyVideoToken(String.valueOf(sectionId),String.valueOf(CourseHelper.getSystemCourseId((Context) mView))),
                new BaseObserver<BjyTokensRes>() {
                    @Override
                    public void onPreRequest() {
                        mView.showLoadV();
                    }

                    @Override
                    public void onSuccess(BjyTokensRes bjyTokensRes) {
                        mView.closeLoadV();
                        if (isPlay) {
                            VideoPlayHelper.playVideo(bjyTokensRes.getData(), courseType);
                        } else {
                            VideoPlayHelper.download(
                                    (Activity) mView,
                                    bjyTokensRes.getData().getTokenData(),
                                    CourseHelper.getIDownloadInfo(sectionId, outLineResultList),
                                    CourseHelper.getICourseInfo(mView.getCourseBean())
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

}
