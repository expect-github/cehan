package com.nj.baijiayun.module_public.mvp.presenter;

import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.response.CourseListRes;
import com.nj.baijiayun.module_public.mvp.contract.CourseContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author chengang
 * @date 2019-07-30
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.mvp.presenter
 * @describe
 */
public class CoursePresenter extends CourseContract.Presenter {

    @Inject
    PublicService publicService;

    @Inject
    public CoursePresenter() {

    }

    int courseType = 0;
    int classifyId = 0;
    String attrValId = "";
    int isVip = 0;
    String keyWords;
    int orderBy = 0;

    @Override
    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }

    @Override
    public void setAttrValId(String attrValId) {
        this.attrValId = attrValId;
    }

    @Override
    public void setVip(int vip) {
        this.isVip = vip;
    }

    @Override
    public void clearSelect() {
        courseType = 0;
        classifyId = 0;
        attrValId = "";
        isVip = 0;
        orderBy = 0;
    }


    @Override
    public List resultCovertToList(CourseListRes result) {
        return result.getData().getList();
    }

    @Override
    public Observable<CourseListRes> getListObservable(int page) {
        return publicService.getCourseList(courseType, classifyId, attrValId, isVip, keyWords, orderBy, page);
    }
}
