package com.nj.baijiayun.module_public.helper.push;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.Keep;

/**
 * @author chengang
 * @date 2019-12-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.push
 * @describe
 */
@Keep
public class PushDataBean {

    /**
     * 模版类型
     */
    @SerializedName("template_type")
    private String templeType;
    /**
     * 唯一id
     */
    private String id;
    /**
     * 订单类型
     */
    @SerializedName("order_type")
    private String orderType;
    @SerializedName("teacher_id")
    private String teacherId;
    @SerializedName("order_status")
    private int orderStatus;
    @SerializedName("shop_type")
    private int shopType;
    @SerializedName("url")
    private String url;
    @SerializedName("jump_type")
    private int jumpType;

    public String getUrl() {
        return url;
    }

    public int getShopType() {
        return shopType;
    }

    public String getId() {
        return id;
    }

    public int getIntId() {
        try {
            return Integer.parseInt(id);
        } catch (Exception ee) {
            return 0;
        }

    }

    public String getOrderType() {
        return orderType;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public boolean isToCourse() {
        return jumpType == 1;
    }

    public boolean isToBook() {
        return jumpType == 2;
    }

    public boolean isToNews() {
        return jumpType == 3;
    }
}
