package com.nj.baijiayun.module_main.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-12-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe 首页的价格写成string 接受会有问题，因为首页数据用map接受的
 */
public class BookBean {

    @SerializedName("cover_img")
    private String cover;
    private String title;
    private long price;
    private int id;
    @SerializedName("is_has_coupon")
    private int hasCoupon;
    @SerializedName("is_spelled")
    private int hasAssemble;
    @SerializedName("instruction")
    private String instruction;
    @SerializedName("underlined_price")
    private long underlinedPrice;
    @SerializedName("author")
    private String author;

    public long getUnderlinedPrice() {
        return underlinedPrice;
    }

    public String getAuthor() {
        return author;
    }

    public String getInstruction() {
        return instruction;
    }

    public boolean hasCoupon() {
        return hasCoupon == 1;
    }

    public boolean hasAssemble() {
        return hasAssemble == 1;
    }

    public int getId() {
        return id;
    }

    public long getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }
}
