package com.nj.baijiayun.module_main.bean.wrapper;


import com.nj.baijiayun.module_main.bean.CourseTypeBean;

import java.util.List;

public class CourseTypeWrapBean {

    /**
     * course_type : [{"id":0,"name":"全部","is_show":1},{"id":1,"name":"一对一","is_show":2},{"id":2,"name":"大班课","is_show":1}]
     * price_ico : https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/image/20193hWkiG11F21566388779.png
     */

    private String price_ico;
    private List<CourseTypeBean> course_type;

    public String getPrice_ico() {
        return price_ico;
    }

    public void setPrice_ico(String price_ico) {
        this.price_ico = price_ico;
    }

    public List<CourseTypeBean> getCourse_type() {
        return course_type;
    }

    public void setCourse_type(List<CourseTypeBean> course_type) {
        this.course_type = course_type;
    }
}
