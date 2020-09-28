package com.nj.baijiayun.module_main.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-12-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class NewsBean {

    private int id;
    private String title;
    @SerializedName("thumb_img")
    private String cover;
    @SerializedName("created_at")
    private int updatedAt;
    @SerializedName("description")
    private String abstractX;
    @SerializedName("click_rate")
    private int browseNumber;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }

    public String getAbstractX() {
        return abstractX;
    }

    public int getBrowseNumber() {
        return browseNumber;
    }
}
