package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author chengang
 * @date 2020-02-28
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class PublicCourseClassifyWrapperBean   {
    @SerializedName("appCourseType")
    List<PublicCourseTypeBean> appCourseType;

    public List<PublicCourseTypeBean> getAppCourseType() {
        return appCourseType;
    }
}
