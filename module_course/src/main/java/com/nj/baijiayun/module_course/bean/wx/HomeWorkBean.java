package com.nj.baijiayun.module_course.bean.wx;

import com.google.gson.annotations.SerializedName;

import java.text.MessageFormat;

/**
 * @author chengang
 * @date 2019-10-23
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.bean.wx
 * @describe
 */
public class HomeWorkBean {

    /**
     * is_submit : 1
     * is_view : 0
     * homework_name : 做彦文的小可爱
     * homework_num : 2
     */

    @SerializedName("is_submit")
    private int isSubmit;
    @SerializedName("is_view")
    private int isView;
    @SerializedName("homework_name")
    private String homeworkName;
    @SerializedName("homework_num")
    private int homeworkNum;
    @SerializedName("homework_id")
    int homeworkId;

    public boolean needShowHomeWork() {
        return homeworkNum > 0;
    }

    public String getHomeworkName() {
        if (getHomeworkNum() > 0) {
            return MessageFormat.format("{0} 等{1}个作业", homeworkName, getHomeworkNum());
        }
        return homeworkName;
    }

    public int getHomeworkNum() {
        return homeworkNum;
    }

    public boolean isShowSubmit() {
        return isSubmit == 1;
    }

    public boolean isShowLook() {
        return isView == 1;
    }

    public int getHomeworkId() {
        return homeworkId;
    }
}
