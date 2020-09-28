package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.Nullable;

/**
 * @author chengang
 * @date 2019-08-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class PublicVipBean {


    /**
     * id : 8
     * vip_created_at : 1565608475
     * vip_end_at : 1573557275
     */

    private int id;
    @SerializedName("vip_created_at")
    private long vipCreatedAt;
    @SerializedName("vip_end_at")
    private long vipEndAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getVipCreatedAt() {
        return vipCreatedAt;
    }

    public void setVipCreatedAt(long vipCreatedAt) {
        this.vipCreatedAt = vipCreatedAt;
    }

    public long getVipEndAt() {
        return vipEndAt;
    }

    public void setVipEndAt(long vipEndAt) {
        this.vipEndAt = vipEndAt;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof PublicVipBean) {
            return id == ((PublicVipBean) obj).id
                    && vipCreatedAt == ((PublicVipBean) obj).vipCreatedAt
                    && vipEndAt == ((PublicVipBean) obj).vipEndAt;
        }
        return super.equals(obj);
    }

}
