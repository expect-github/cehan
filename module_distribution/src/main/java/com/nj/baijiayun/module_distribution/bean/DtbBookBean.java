package com.nj.baijiayun.module_distribution.bean;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_public.bean.IDistrution;

/**
 * @author chengang
 * @date 2020-02-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_distribution.bean
 * @describe
 */
public class DtbBookBean implements IDistrution {
    @SerializedName("id")
    private int bookId;
    @SerializedName("commission_type")
    private int commissionType;
    @SerializedName("commission")
    private int commission;
    @SerializedName("commission_rate")
    private int commissionRate;
    @SerializedName("name")
    private String name;
    @SerializedName("cover_img")
    private String coverImg;
    @SerializedName("author")
    private String author;
    @SerializedName("price")
    private String price;
    @SerializedName("underlined_price")
    private String underlinedPrice;

    public void setCommissionType(int commissionType) {
        this.commissionType = commissionType;
    }

    public String getName() {
        return name;
    }
    @Override
    public boolean isShowRate() {
        return TYPE_SHOW_RATE == commissionType;
    }


    @Override
    public int getCommissionRate() {
        return commissionRate;
    }

    @Override
    public int getCommission() {
        return commission;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public String getAuthor() {
        return author;
    }

    public String getPrice() {
        return price;
    }

    public String getUnderlinedPrice() {
        return underlinedPrice;
    }

    public int getBookId() {
        return bookId;
    }
}
