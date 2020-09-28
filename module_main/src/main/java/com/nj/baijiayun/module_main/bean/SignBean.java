package com.nj.baijiayun.module_main.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-12-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class SignBean {
    @SerializedName("is_sign")
    private int isSign;
    @SerializedName("total_integral")
    private int totalIntegral;
    public boolean isSign()
    {
        return isSign==1;
    }

    public int getTotalIntegral() {
        return totalIntegral;
    }
}
