package com.nj.baijiayun.module_distribution.bean;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_public.temple.BaseListPageWrapperBean;

/**
 * @author chengang
 * @date 2020-02-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_distribution.bean
 * @describe
 */
public class DtbWrapperListPageWrapperBean<T> extends BaseListPageWrapperBean<T> {
    @SerializedName("commission_type")
    private int commissionType;

    public int getCommissionType() {
        return commissionType;
    }
}
