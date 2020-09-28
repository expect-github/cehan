package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author chengang
 * @date 2019-07-23
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class PublicCourseBean implements Serializable {

    private int id;
    @SerializedName("cover_img")
    private String cover;
    private String title;
    @SerializedName("course_type")
    private int courseType;
    @SerializedName("course_classify_id")
    private int courseClassifyId;

    @SerializedName("start_play_date")
    private long startPlayDate;
    @SerializedName("end_play_date")
    private long endPlayDate;

    @SerializedName("underlined_price")
    private long underlinedPrice;
    private long price;

    @SerializedName("teachers_list")
    private List<PublicTeacherBean> teacherList;

    @SerializedName("is_has_coupon")
    private int isHasCoupon;

    private List<PublicTeacherBean> teachers;


    //仅仅公开课有
    @SerializedName("periods_id")
    private int periodsId;

    public int getPlayId() {
        return periodsId;
    }

    /**
     * 1 会员课
     */
    @SerializedName("is_vip")
    private int isVip;

    @SerializedName("has_buy")
    private int hasBuy;

    @SerializedName("total_periods")
    private int totalPeriods;


    @SerializedName("browse_num")
    private int browseNum;
    @SerializedName("sales_num")
    private int salesNum;
    @SerializedName("is_join_spell")
    private int isJoinSpell;
    @SerializedName("subject_name")
    private String subject_name;

    private List<PublicCourseServerBean> service;


    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCourseClassifyId() {
        return courseClassifyId;
    }

    public void setCourseClassifyId(int courseClassifyId) {
        this.courseClassifyId = courseClassifyId;
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

    public String getUnderlinedPrice() {
        return String.valueOf(underlinedPrice);
    }

    public String getPrice() {
        return String.valueOf(price);
    }

    public List<PublicTeacherBean> getTeacherList() {
        if (teacherList == null || teacherList.size() == 0) {
            return teachers;
        }
        return teacherList;
    }

    public void setTeacherList(List<PublicTeacherBean> teacherList) {
        this.teacherList = teacherList;
    }


    public int getIsHasCoupon() {
        return isHasCoupon;
    }

    public void setIsHasCoupon(int isHasCoupon) {
        this.isHasCoupon = isHasCoupon;
    }

    public boolean isHasCoupon() {
        return isHasCoupon == 1;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public int getHasBuy() {
        return hasBuy;
    }

    public void setHasBuy(int hasBuy) {
        this.hasBuy = hasBuy;
    }

    public void setHasBuy() {
        this.hasBuy = 1;
    }

    public boolean isHasBuy() {
        return hasBuy == 1;
    }


    public int getTotalPeriods() {
        return totalPeriods;
    }

    public void setTotalPeriods(int totalPeriods) {
        this.totalPeriods = totalPeriods;
    }

    public int getBrowseNum() {
        return browseNum;
    }

    public boolean isSignUp() {
        return hasBuy == 1;
    }

    public void setBrowseNum(int browseNum) {
        this.browseNum = browseNum;
    }

    public int getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(int salesNum) {
        this.salesNum = salesNum;
    }

    public boolean isVipCourse() {
        return 1 == isVip;
    }

    public List<PublicTeacherBean> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<PublicTeacherBean> teachers) {
        this.teachers = teachers;
    }

    public String getCover() {
        return cover;
    }

    public List<PublicCourseServerBean> getService() {
        return service;
    }

    public boolean isServiceEmpty() {
        return service == null || service.size() == 0;
    }

    public boolean isJoinSpell() {
        return isJoinSpell == 1;
    }



}
