package com.nj.baijiayun.module_course.bean.wx;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.helper.AccountHelper;

/**
 * @author chengang
 * @date 2019-08-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.bean.wx
 * @describe
 */
public class MyCourseBean {


    /**
     * course_id : 94
     * title : 大班课免费001
     * course_type : 2
     * start_play_date : 1564502400
     * end_play_date : 1564588800
     * section_num : 0
     * today_course_num : 0
     * now_course_num : 0
     * is_vip_course : 0
     * progress_rate : 0
     */

    @SerializedName("course_id")
    private int courseId;
    private String title;
    @SerializedName("course_type")
    private int courseType;
    @SerializedName("start_play_date")
    private long startPlayDate;
    @SerializedName("end_play_date")
    private long endPlayDate;
    @SerializedName("section_num")
    private int sectionNum;
    @SerializedName("today_course_num")
    private int todayCourseNum;
    @SerializedName("now_course_num")
    private int nowCourseNum;
    @SerializedName("is_vip_course")
    private int isVipCourse;
    @SerializedName("progress_rate")
    private int progressRate;
    @SerializedName("is_buy_order")
    private int isFormOrder;
    private boolean isHide;

    public void setHide(boolean hide) {
        isHide = hide;
    }

    public boolean isHide() {
        return isHide;
    }

    private int type;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }

    public long getStartPlayDate() {
        return startPlayDate;
    }

    public void setStartPlayDate(long startPlayDate) {
        this.startPlayDate = startPlayDate;
    }

    public long getEndPlayDate() {
        return endPlayDate;
    }

    public void setEndPlayDate(long endPlayDate) {
        this.endPlayDate = endPlayDate;
    }

    public int getSectionNum() {
        return sectionNum;
    }

    public void setSectionNum(int sectionNum) {
        this.sectionNum = sectionNum;
    }

    public int getTodayCourseNum() {
        return todayCourseNum;
    }

    public void setTodayCourseNum(int todayCourseNum) {
        this.todayCourseNum = todayCourseNum;
    }

    public int getNowCourseNum() {
        return nowCourseNum;
    }

    public void setNowCourseNum(int nowCourseNum) {
        this.nowCourseNum = nowCourseNum;
    }

    public int getIsVipCourse() {
        return isVipCourse;
    }

    public void setIsVipCourse(int isVipCourse) {
        this.isVipCourse = isVipCourse;
    }

    public int getProgressRate() {
        return progressRate;
    }

    public void setProgressRate(int progressRate) {
        this.progressRate = progressRate;
    }

    public boolean isInvalid() {
        return !isFromOrder()
                && (isUserNotBeVipInvalid() || isCourseNotVipInvalid());
    }

    public boolean isUserNotBeVipInvalid() {
        return (isVipCourse() && !AccountHelper.getInstance().getInfo().isVip());

    }

    public boolean isCourseNotVipInvalid() {
        return !isVipCourse() && ConstsCouseType.isVipCourse(type);
    }

    public boolean isVipCourse() {
        return 1 == isVipCourse;
    }


    public boolean isShowProgress() {
        return ConstsCouseType.isLive(courseType) || ConstsCouseType.isAudio(courseType) || ConstsCouseType.isVideo(courseType);
    }

    public boolean isFromOrder() {
        return isFormOrder == 1;
    }
}
