package com.nj.baijiayun.module_public.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;

/**
 * @author chengang
 * @date 2019-08-01
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class PublicCourseTypeBean {

    /**
     * id : 1
     * name : 一对一
     * is_show : 2
     */

    private int id;
    private String name;
    @SerializedName("is_show")
    private int isShow;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {

        String courseTypeName = ConstsCouseType.getCourseTypeName(id);
        return !TextUtils.isEmpty(courseTypeName) ? courseTypeName : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return true;
    }

}
