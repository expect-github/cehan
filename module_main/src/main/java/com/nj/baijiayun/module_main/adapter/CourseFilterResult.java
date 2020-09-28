package com.nj.baijiayun.module_main.adapter;

/**
 * @project zywx_android
 * @class name：com.baijiayun.zywx.module_main.bean
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 18/11/16 下午8:08
 * @change
 * @time
 * @describe
 */
public class CourseFilterResult {
    private int orderBy;
    private String keyWords;
    private String attrValId;
    private int courseType;
    private int isVip;

    public void setVip() {
        isVip = 1;
    }


    public void setNotVip() {
        isVip = 0;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getAttrValId() {
        return attrValId;
    }

    public void setAttrValId(String attrValId) {
        this.attrValId = attrValId;
    }

    public int getCourseType() {
        return courseType;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }
}
