package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * @author chengang
 * @date 2020-02-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class PublicOtherSettingBean {
    @SerializedName("price_svg")
    private String priceIcon;
    @SerializedName("course_name")
    private HashMap<String,String> courseNameMap;

    public HashMap<String, String> getCourseNameMap() {
        return courseNameMap;
    }

    public String getPriceIcon() {
        return priceIcon;
    }
}
