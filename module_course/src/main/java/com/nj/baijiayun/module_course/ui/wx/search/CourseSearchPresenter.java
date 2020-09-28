package com.nj.baijiayun.module_course.ui.wx.search;

import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.response.CourseListRes;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CourseSearchPresenter extends CourseSearchContract.Presenter {
    @Inject
    PublicService mPublicService;
    @Inject
    CourseSearchPresenter() {

    }

    @Override
    public List resultCovertToList(CourseListRes result) {
        return result.getData().getList();
    }

    @Override
    public Observable<CourseListRes> getListObservable(String title, int page) {
        return mPublicService.getCourseList(
                0,
                0,
                "",
                0,
                title,
                0, page);
    }



}
