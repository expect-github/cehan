package com.nj.baijiayun.module_main.mvp.presenter;

import com.nj.baijiayun.module_common.base.BaseObserver;
import com.nj.baijiayun.module_main.api.MainService;
import com.nj.baijiayun.module_main.bean.res.CourseClassifyResponse;
import com.nj.baijiayun.module_main.helper.IntervalHelper;
import com.nj.baijiayun.module_main.mvp.contract.SelectContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.mvp.presenter
 * @describe
 */
public class SelectCoursePresenter extends SelectContract.Presenter {
    @Inject
    MainService mMainService;

    private boolean needLoadFliter = true;

    @Inject
    public SelectCoursePresenter() {

    }


    @Override
    public void getFliterInterval() {
        if (IntervalHelper.isNotAllowStart(5000)) {
            return;
        }
        needLoadFliter = true;
        getFliter();
    }

    @Override
    public void getCourseType() {
        //两个公用一个
        getFliter();
    }


    @Override
    public void getFliter() {
        if (!needLoadFliter) {
            return;
        }


        submitRequest(mMainService.getCourseClassify(), new BaseObserver<CourseClassifyResponse>() {
            @Override
            public void onPreRequest() {

            }

            @Override
            public void onSuccess(CourseClassifyResponse courseClassifyResponse) {
                if (needLoadFliter) {
                    mView.setFilterAttrs(courseClassifyResponse.getData().getAttrClassify());
                    mView.setFilterCourseType(courseClassifyResponse.getData().getAppCourseType());
                }
                needLoadFliter = false;
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
}
