package com.nj.baijiayun.module_course.bean;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_public.bean.PublicCouponBean;
import com.nj.baijiayun.module_public.bean.PublicCourseDetailBean;
import com.nj.baijiayun.module_public.bean.PublicDistributionBean;
import com.nj.baijiayun.module_public.bean.PublicTeacherBean;

import java.util.List;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.bean
 * @describe
 */
public class CourseDetailWrapperBean {
    private PublicCourseDetailBean info;
    private List<PublicTeacherBean>teachers;
    private List<PublicCouponBean>couponList;
    @SerializedName("distribute_item")
    private PublicDistributionBean distributionBean;

    public PublicDistributionBean getDistributionBean() {
        return distributionBean;
    }

    public PublicCourseDetailBean getInfo() {
        return info;
    }

    public void setInfo(PublicCourseDetailBean info) {
        this.info = info;
    }

    public List<PublicTeacherBean> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<PublicTeacherBean> teachers) {
        this.teachers = teachers;
    }

    public List<PublicCouponBean> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<PublicCouponBean> couponList) {
        this.couponList = couponList;
    }
}
