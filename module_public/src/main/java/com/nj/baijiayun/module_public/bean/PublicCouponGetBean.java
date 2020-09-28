package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-08-03
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class PublicCouponGetBean {

    /**
     * id : 178
     * is_continue_get : 1
     */

    private int id;
    @SerializedName("is_continue_get")
    private int isContinueGet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsContinueGet() {
        return isContinueGet;
    }

    public void setIsContinueGet(int isContinueGet) {
        this.isContinueGet = isContinueGet;
    }

    public boolean isContinueGet() {
        return 1 == isContinueGet;
    }

}
