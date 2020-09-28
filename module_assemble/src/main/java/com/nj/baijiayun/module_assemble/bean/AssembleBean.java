package com.nj.baijiayun.module_assemble.bean;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.basic.utils.StringUtils;
import com.nj.baijiayun.module_public.bean.PublicTeacherBean;

import java.util.Arrays;
import java.util.List;

/**
 * @author chengang
 * @date 2019-11-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_assemble.bean
 * @describe
 */
public class AssembleBean {

    @SerializedName("spell_id")
    private int assembleId;
    @SerializedName("spell_price")
    private String assemblePrice;
    @SerializedName("user_num")
    private int userNum;
    @SerializedName("already_join_number")
    private int alreadyJoinNumber;
    @SerializedName("group_user_avatars")
    private String groupUserAvatar;
    @SerializedName("start_time")
    private long assembleStartTime;
    @SerializedName("end_time")
    private long assembleEndTime;
    @SerializedName("book_id")
    private int bookId;
    @SerializedName("book")
    private String bookName;
    @SerializedName("author")
    private String author;
    @SerializedName("price")
    private String price;
    @SerializedName("course")
    private String courseName;
    @SerializedName("cover_img")
    private String coverImg;
    @SerializedName("course_basis_id")
    private int courseBasisId;
    @SerializedName("period_number")
    private int periodNumber;
    @SerializedName("teachers")
    private List<PublicTeacherBean> teacherList;
    @SerializedName("start_play_date")
    private long startPlayDate;
    @SerializedName("end_play_date")
    private long endPlayDate;
    @SerializedName("course_type")
    private int courseType;
    @SerializedName("has_coupon")
    private int hasCoupon;
    private List<String> joinHeads;
    @SerializedName("instruction")
    private String instruction;

    public int getAssembleId() {
        return assembleId;
    }

    public void setAssembleId(int assembleId) {
        this.assembleId = assembleId;
    }

    public String getAssemblePrice() {
        return assemblePrice;
    }

    public void setAssemblePrice(String assemblePrice) {
        this.assemblePrice = assemblePrice;
    }

    public int getUserNum() {

        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public int getAlreadyJoinNumber() {
        return alreadyJoinNumber;
    }

    public void setAlreadyJoinNumber(int alreadyJoinNumber) {
        this.alreadyJoinNumber = alreadyJoinNumber;
    }

    public String getGroupUserAvatar() {
        return groupUserAvatar;
    }

    public void setGroupUserAvatar(String groupUserAvatar) {
        this.groupUserAvatar = groupUserAvatar;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public int getCourseBasisId() {
        return courseBasisId;
    }

    public void setCourseBasisId(int courseBasisId) {
        this.courseBasisId = courseBasisId;
    }

    public int getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(int periodNumber) {
        this.periodNumber = periodNumber;
    }

    public List<PublicTeacherBean> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<PublicTeacherBean> teacherList) {
        this.teacherList = teacherList;
    }

    public long getStartPlayDate() {
        return assembleStartTime;
    }

    public long getEndPlayDate() {
        return assembleEndTime;
    }


    public List<String> getJoinHeads() {
        if (joinHeads == null) {
            if (StringUtils.isEmpty(groupUserAvatar)) {
                return null;
            }
            joinHeads = Arrays.asList(groupUserAvatar.split(","));
        }
        return joinHeads;
    }

    public int getCourseType() {
        return courseType;
    }

    public boolean hasCoupon() {
        return 1 == hasCoupon;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getTitle() {
        return getBookId() != 0 ? getBookName() : getCourseName();
    }

}
