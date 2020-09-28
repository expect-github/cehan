package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2020-02-28
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class PublicDistributionBean implements IDistrution {

    /**
     * commission : 0
     * commission_rate : 29
     * commission_type : 0
     */

    private int commission;
    @SerializedName("commission_rate")
    private int commissionRate;
    @SerializedName("commission_type")
    private int commissionType;

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


}
