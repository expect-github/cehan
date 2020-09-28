package com.nj.baijiayun.module_course.bean.wx;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2020-03-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.bean.wx
 * @describe
 */
public class CourseQrBean {

    /**
     * id : 1
     * course_qrcode_title : 0
     * course_qrcode_img : 0
     */

    @SerializedName("course_qrcode_title")
    private String courseQrcodeTitle;
    @SerializedName("course_qrcode_img")
    private String courseQrcodeImg;

    public String getCourseQrcodeTitle() {
        return courseQrcodeTitle;
    }


    public String getCourseQrcodeImg() {
        return courseQrcodeImg;
    }

    public boolean isShowQrCode() {
        return !TextUtils.isEmpty(courseQrcodeTitle);
    }

}
