package com.nj.baijiayun.module_distribution.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author chengang
 * @date 2020-02-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_distribution.bean
 * @describe
 */
public class DtbListWrapperBean {
    @SerializedName("course")
    private List<DtbCourseBean> courseList;
    @SerializedName("book")
    private List<DtbBookBean> bookBeanList;
    @SerializedName("commission_type")
    private int commissionType;

    public List<DtbCourseBean> getCourseList() {
        if (courseList != null) {
            for (int i = 0; i < courseList.size(); i++) {
                courseList.get(i).setCommissionType(commissionType);
            }
        }

        return courseList;
    }

    public List<DtbBookBean> getBookBeanList() {
        if (bookBeanList != null) {
            for (int i = 0; i < bookBeanList.size(); i++) {
                bookBeanList.get(i).setCommissionType(commissionType);
            }
        }
        return bookBeanList;
    }
}
