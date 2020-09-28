package com.nj.baijiayun.module_main.bean;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_public.bean.PublicAttrClassifyBean;
import com.nj.baijiayun.module_public.bean.PublicClassifyBean;

import java.util.List;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class CourseClassifyWrapperBean {
    @SerializedName("attrclassify")
    private List<PublicAttrClassifyBean> attrClassify;
    private List<PublicClassifyBean> classify;
    @SerializedName("appCourseType")
    List<CourseTypeBean> appCourseType;

    public List<CourseTypeBean> getAppCourseType() {
        return appCourseType;
    }

    public List<PublicAttrClassifyBean> getAttrClassify() {
        return attrClassify;
    }

    public void setAttrClassify(List<PublicAttrClassifyBean> attrClassify) {
        this.attrClassify = attrClassify;
    }

    public List<PublicClassifyBean> getClassify() {
        return classify;
    }

    public void setClassify(List<PublicClassifyBean> classify) {
        this.classify = classify;
    }
}
