package com.nj.baijiayun.module_main.bean.wrapper;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_main.bean.CourseTypeBean;

import java.util.List;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class CourseTypeWrapper {

    @SerializedName("course_type")
    private List<CourseTypeBean> courseType;

    public List<CourseTypeBean> getCourseType() {
        return courseType;
    }

    public void setCourseType(List<CourseTypeBean> courseType) {
        this.courseType = courseType;
    }
}
