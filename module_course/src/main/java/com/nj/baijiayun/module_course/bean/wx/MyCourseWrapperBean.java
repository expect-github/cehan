package com.nj.baijiayun.module_course.bean.wx;

import java.util.List;

/**
 * @author chengang
 * @date 2019-08-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.bean.wx
 * @describe
 */
public class MyCourseWrapperBean {

    private List<MyCourseTypeBean> typeNum;
    private List<MyCourseBean> courseList;

    public List<MyCourseTypeBean> getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(List<MyCourseTypeBean> typeNum) {
        this.typeNum = typeNum;
    }

    public List<MyCourseBean> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<MyCourseBean> courseList) {
        this.courseList = courseList;
    }

    public void setCourseListHide() {
        if (courseList == null) {
            return;
        }
        for (int i = 0; i < courseList.size(); i++) {
            courseList.get(i).setHide(true);
        }
    }
}
