package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class PublicCouponBean {


    /**
     * id : 29
     * type : 1
     * name : 第一条优惠卷
     * discounted_price : 3
     * valid_type : 1
     * valid_day : 1
     * valid_start : 0
     * valid_end : 0
     * num : 12
     * draw_num : 0
     * receive_end_at : 2019
     * created_at : 1560217196
     * status : 0
     * is_can_get : 0
     */

    private int id;
    /**
     * 优惠卷类型 1现金券2折扣卷3.满减卷
     */
    private int type;
    private String name;
    @SerializedName("discounted_price")
    private long discountedPrice;
    /**
     * 有效期类型 1.固定天数 2.固定时间段
     */
    @SerializedName("valid_type")
    private int validType;
    @SerializedName("valid_day")
    private int validDay;
    @SerializedName("valid_start")
    private long validStart;
    @SerializedName("valid_end")
    private long validEnd;
    private int num;
    @SerializedName("draw_num")
    private int drawNum;
    @SerializedName("receive_end_at")
    private int receiveEndAt;
    /**
     * 0 未使用 1.使用 2.已过期
     */
    private int status;
    /**
     * 0不能领取 1可以领取
     */
    @SerializedName("is_can_get")
    private int isCanGet;
    @SerializedName("full_reduction")
    private int fullReduction;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(long discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public int getValidType() {
        return validType;
    }

    public void setValidType(int validType) {
        this.validType = validType;
    }

    public int getValidDay() {
        return validDay;
    }

    public void setValidDay(int validDay) {
        this.validDay = validDay;
    }

    public long getValidStart() {
        return validStart;
    }

    public void setValidStart(long validStart) {
        this.validStart = validStart;
    }

    public long getValidEnd() {
        return validEnd;
    }

    public void setValidEnd(long validEnd) {
        this.validEnd = validEnd;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getDrawNum() {
        return drawNum;
    }

    public void setDrawNum(int drawNum) {
        this.drawNum = drawNum;
    }

    public int getReceiveEndAt() {
        return receiveEndAt;
    }

    public void setReceiveEndAt(int receiveEndAt) {
        this.receiveEndAt = receiveEndAt;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsCanGet() {
        return isCanGet;
    }

    public void setIsCanGet(int isCanGet) {
        this.isCanGet = isCanGet;
    }

    public int getFullReduction() {
        return fullReduction;
    }

    public boolean isCanGet() {
        return isCanGet == 1;
    }

    //固定天数类型
    public boolean isFixedType() {
        return validType == 1;
    }
}
