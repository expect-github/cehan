package com.nj.baijiayun.module_distribution.bean;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_public.bean.IDistrution;
import com.nj.baijiayun.module_public.bean.PublicCourseBean;


/**
 * @author chengang
 * @date 2020-02-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_distribution.bean
 * @describe
 */
public class DtbCourseBean extends PublicCourseBean  implements IDistrution {

    @SerializedName("periods_num")
    private int periodsNum;
    @SerializedName("commission_type")
    private int commissionType;
    @SerializedName("commission")
    private int commission;
    @SerializedName("commission_rate")
    private int commissionRate;

    public int getPeriodsNum() {
        return periodsNum;
    }


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

    public void setCommissionType(int commissionType) {
        this.commissionType = commissionType;
    }
}
