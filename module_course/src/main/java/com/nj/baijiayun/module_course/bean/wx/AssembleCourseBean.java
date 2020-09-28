package com.nj.baijiayun.module_course.bean.wx;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-11-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.bean.wx
 * @describe
 */
public class AssembleCourseBean {

    /**
     * book_id : 8
     * book : 第一本书
     * author : 侨侨
     * price : 1000
     * cover_img : https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/image/2019D2kgbuSUxk1572846406.jpg
     * id : 18
     * spell_name : 拼团图书001
     * type : 2
     * type_id : 8
     * product_type : 12
     * spell_price : 10
     * head_price : 10
     * user_num : 2
     * sales_num : 0
     * stock : 1
     * limited_buy : 1
     * total_user_num : 0
     * group_validtime : 3600
     * is_show_group : 1
     * is_auto_succ : 1
     * is_open_check_num : 1
     * is_check_validtime : 1
     * is_use_coupon : 1
     * start_time : 1573702080
     * end_time : 1573747200
     * created_at : 1573702054
     * updated_at : 1573702086
     * deleted_at : 0
     * status : 1
     * is_join_spell : null
     * join_num : 0
     * group_status : null
     * purchase_amount : 0
     */

    @SerializedName("book_id")
    private int bookId;
    private String book;
    private String author;
    private long price;
    @SerializedName("cover_img")
    private String coverImg;
    private int id;
    @SerializedName("spell_name")
    private String spellName;
    private int type;
    @SerializedName("type_id")
    private int typeId;
    @SerializedName("product_type")
    private int productType;
    @SerializedName("spell_price")
    private long spellPrice;
    @SerializedName("head_price")
    /**
     * 团长价格
     */
    private long headPrice;
    @SerializedName("user_num")
    private int userNum;
    @SerializedName("sales_num")
    private int salesNum;
    private int stock;
    @SerializedName("limited_buy")
    private int limitedBuy;
    @SerializedName("total_user_num")
    private int totalUserNum;
    @SerializedName("group_validtime")
    private int groupValidtime;
    @SerializedName("is_show_group")
    private int isShowGroup;
    @SerializedName("is_auto_succ")
    private int isAutoSucc;
    @SerializedName("is_open_check_num")
    private int isOpenCheckNum;
    @SerializedName("is_check_validtime")
    private int isCheckValidtime;
    @SerializedName("is_use_coupon")
    private int isUseCoupon;
    @SerializedName("start_time")
    private long startTime;
    @SerializedName("end_time")
    private long endTime;
    @SerializedName("created_at")
    private long createdAt;
    private int status;
    @SerializedName("is_join_spell")
    private int isJoinSpell;
    @SerializedName("join_num")
    private int joinNum;
    @SerializedName("group_status")
    private int groupStatus;
    @SerializedName("purchase_amount")
    private int purchaseAmount;
    @SerializedName("group_id")
    private int groupId;


    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return String.valueOf(price);
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpellName() {
        return spellName;
    }

    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public String getSpellPrice() {
        return String.valueOf(spellPrice);
    }


    public String getHeadPrice() {
        return String.valueOf(headPrice);
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public int getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(int salesNum) {
        this.salesNum = salesNum;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getLimitedBuy() {
        return limitedBuy;
    }

    public void setLimitedBuy(int limitedBuy) {
        this.limitedBuy = limitedBuy;
    }

    public int getTotalUserNum() {
        return totalUserNum;
    }

    public void setTotalUserNum(int totalUserNum) {
        this.totalUserNum = totalUserNum;
    }

    public int getGroupValidtime() {
        return groupValidtime;
    }

    public void setGroupValidtime(int groupValidtime) {
        this.groupValidtime = groupValidtime;
    }

    public int getIsShowGroup() {
        return isShowGroup;
    }

    public void setIsShowGroup(int isShowGroup) {
        this.isShowGroup = isShowGroup;
    }

    public int getIsAutoSucc() {
        return isAutoSucc;
    }

    public void setIsAutoSucc(int isAutoSucc) {
        this.isAutoSucc = isAutoSucc;
    }

    public int getIsOpenCheckNum() {
        return isOpenCheckNum;
    }

    public void setIsOpenCheckNum(int isOpenCheckNum) {
        this.isOpenCheckNum = isOpenCheckNum;
    }

    public int getIsCheckValidtime() {
        return isCheckValidtime;
    }

    public void setIsCheckValidtime(int isCheckValidtime) {
        this.isCheckValidtime = isCheckValidtime;
    }

    public int getIsUseCoupon() {
        return isUseCoupon;
    }

    public void setIsUseCoupon(int isUseCoupon) {
        this.isUseCoupon = isUseCoupon;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isJoinSpell() {
        return isJoinSpell > 0;
    }

    public boolean isAssembleStatusInProgress() {
        return status == 1;
    }


    public void setIsJoinSpell(int isJoinSpell) {
        this.isJoinSpell = isJoinSpell;
    }

    public int getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(int joinNum) {
        this.joinNum = joinNum;
    }

    public int getGroupStatus() {
        return groupStatus;
    }

    public  boolean isAssembleSuccess()
    {
        return isJoinSpell()&&groupStatus==1;
    }

    public void setGroupStatus(int groupStatus) {
        this.groupStatus = groupStatus;
    }

    public int getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(int purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public boolean needCheckJoinAssemble() {
        return isOpenCheckNum == 1;
    }

    public boolean needShowAssembleGroup() {
        return isShowGroup == 1;
    }


    public boolean needShowCoupon()
    {
        return  isUseCoupon==1;
    }

    public String getOpenAssemblePrice()
    {
        return headPrice>0?getHeadPrice():getSpellPrice();
    }

    public int getJoinGroupId() {
        return groupId;
    }
}
