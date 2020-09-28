package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_public.helper.AccountHelper;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class PublicCourseDetailBean extends PublicCourseBean implements ICourseStudy {

    @SerializedName("vip_price")
    private String vipPrice;
    @SerializedName("browse_base")
    private int browseBase;
    @SerializedName("sales_base")
    private int salesBase;
    @SerializedName("created_at")
    private long createdAt;
    /**
     * 原始库存 (卖出数量+剩余库存 )
     */
    @SerializedName("store_num")
    private int storeNum;
    /**
     * 分类名称
     */
    @SerializedName("classify_title")
    private String classifyTitle;
    @SerializedName("course_details")
    private String courseDetails;
    private String teacher;
    @SerializedName("chapter_count")
    private int chapterCount;
    @SerializedName("collect_id")
    private int collectId;
    /**
     * 是否免费 1是
     */
    @SerializedName("is_free")
    private int isFree;


    @SerializedName("is_join_study")
    private int isJoinStudy;
    /**
     * 1:会员课 0:非会员课
     */
    @SerializedName("is_vip_course")
    private int isVipCourse;
    /**
     * 剩余库存信息
     */
    private int stock;
    /**
     * 是否可以回放
     */
    @SerializedName("is_playback")
    private int isPlayBack;
    /**
     * 是否收藏 1:收藏
     */
    @SerializedName("is_collect")
    private int isCollect;
    /**
     * 是否是会员 1:是
     */
    @SerializedName("is_vip_user")
    private int isVipUser;
    /**
     * vip_user_end
     */
    @SerializedName("vip_user_end")
    private long vipUserEnd;


    @SerializedName("city_name")
    private String cityName;
    @SerializedName("district_name")
    private String districtName;
    @SerializedName("province_name")
    private String provinceName;
    private String address;

    @SerializedName("enter_end_date")
    private long faceCourseSignUpEndTime;


    @SerializedName("is_buy")
    private int isBuy;
    @SerializedName("limit")
    private int limit;
    @SerializedName("is_go_to_study")
    private int isCanStudyUndercarriage;
    @SerializedName("status")
    private int carriageStatus;

    public long getFaceCourseSignUpEndTime() {
        return faceCourseSignUpEndTime;
    }

    public String getAddress() {
        return provinceName + cityName + districtName + address;
    }

    public String getDetailAddress() {
        return provinceName + cityName + districtName + address;
    }

    public String getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public int getBrowseBase() {
        return browseBase;
    }

    public void setBrowseBase(int browseBase) {
        this.browseBase = browseBase;
    }

    public int getSalesBase() {
        return salesBase;
    }

    public void setSalesBase(int salesBase) {
        this.salesBase = salesBase;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getStoreNum() {
        return storeNum;
    }

    public boolean isShowStore() {
        return storeNum < 999999;
    }

    public void setStoreNum(int storeNum) {
        this.storeNum = storeNum;
    }

    public String getClassifyTitle() {
        return classifyTitle;
    }

    public void setClassifyTitle(String classifyTitle) {
        this.classifyTitle = classifyTitle;
    }

    public String getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(String courseDetails) {
        this.courseDetails = courseDetails;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(int chapterCount) {
        this.chapterCount = chapterCount;
    }

    public int getIsFree() {
        return isFree;
    }

    public void setIsFree(int isFree) {
        this.isFree = isFree;
    }

    public int getIsVipCourse() {
        return isVipCourse;
    }

    public void setIsVipCourse(int isVipCourse) {
        this.isVipCourse = isVipCourse;
    }

    public int getStock() {
        return stock;
    }

    public boolean isNoStock() {
        return stock <= 0;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIsPlayBack() {
        return isPlayBack;
    }

    public void setIsPlayBack(int isPlayBack) {
        this.isPlayBack = isPlayBack;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public int getIsVipUser() {
        return isVipUser;
    }

    public void setIsVipUser(int isVipUser) {
        this.isVipUser = isVipUser;
    }

    public long getVipUserEnd() {
        return vipUserEnd;
    }

    public void setVipUserEnd(long vipUserEnd) {
        this.vipUserEnd = vipUserEnd;
    }

    public boolean isCollect() {
        return isCollect == 1;
    }

    @Override
    public boolean isVipCourse() {
        return 1 == isVipCourse;
    }


    public boolean isVipUser() {
        return AccountHelper.getInstance().isVip();
//        return isVipUser == 1;
    }

    public boolean isBuy() {
        return isBuy == 1;
    }

    public int getCollectId() {
        return collectId;
    }

    public void setCollectId(int collectId) {
        this.collectId = collectId;
    }

    public void setJoinSuccess() {
        this.isJoinStudy = 1;
    }

    public boolean isJoinStudy() {
        return isJoinStudy == 1;
    }

    public boolean isFree() {
        return isFree == 1;
    }

    public void joinLearnedSuccess() {
        this.isJoinStudy = 1;
        changeSalesNumber();
    }


    public void changeJoinStudyStatusSuccess() {
        this.isJoinStudy = 1;
    }

    private void changeSalesNumber() {
        this.setSalesNum(getSalesNum() + 1);
        this.setStock((getStock() - 1) >= 0 ? (getStock() - 1) : 0);
    }

    public void setBuySuccess() {
        this.isBuy = 1;
        changeSalesNumber();

    }


    public boolean isBuyOrAddJoin() {
        return isBuy() || isJoinStudy();
    }

    public boolean isLimit() {
        return 1 == limit;
    }

    @Override
    public int isGoToStudy() {
        return isCanStudyUndercarriage;
    }

    @Override
    public int courseStatus() {
        return carriageStatus;
    }


}
