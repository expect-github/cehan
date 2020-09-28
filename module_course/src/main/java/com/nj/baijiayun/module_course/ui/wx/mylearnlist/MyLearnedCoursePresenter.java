package com.nj.baijiayun.module_course.ui.wx.mylearnlist;

import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_common.base.BaseResponse;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.api.CourseService;
import com.nj.baijiayun.module_course.bean.response.MyCourseResponse;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author chengang
 * @date 2019-08-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.mvp.contract
 * @describe
 */
public class MyLearnedCoursePresenter extends MyLearnedCourseContract.Presenter {

    @Inject
    CourseService mCourseService;

    @Inject
    MyLearnedCoursePresenter() {

    }

    @Override
    void getCourseType(final int courseType, boolean isHide) {
        submitRequest(isHide ? mCourseService.getMyHideCourse(courseType) : mCourseService.getMyCourse(courseType), new BaseObserver<MyCourseResponse>() {
            @Override
            public void onPreRequest() {
                mView.showLoadView();
            }

            @Override
            public void onSuccess(MyCourseResponse baseResponse) {
                mView.showContentView();
                if (isHide) {
                    baseResponse.getData().setCourseListHide();
                }
                mView.setTabs(baseResponse.getData().getCourseList());
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

    @Override
    void removeCourse(int courseId) {
        submitRequest(mCourseService.removeLearnedCourse(courseId), new BaseObserver<BaseResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(BaseResponse baseResponse) {
                mView.showToastMsg(R.string.course_remove_success);
                mView.removeCourseSuccess(courseId);
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


}
