package com.nj.baijiayun.module_main.bean;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_public.widget.banner.IBannerImage;

/**
 * @author chengang
 * @date 2019-07-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class NewBannerBean implements IBannerImage {

    @SerializedName("banner_img")
    private String bannerImg;
    @SerializedName("link_type")
    private int linkType;
    @SerializedName("link_content")
    private String linkContent;

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public int getLinkType() {
        return linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
    }

    public String getLinkContent() {
        return linkContent;
    }

    public void setLinkContent(String linkContent) {
        this.linkContent = linkContent;
    }

    @Override
    public String getBannerCover() {
        return bannerImg;
    }
}
