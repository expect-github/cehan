package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-11-18
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class PublicShareImgBean {
    @SerializedName("share_url")
    private String shareImg;

    public String getShareImg() {
        return shareImg;
    }
}
