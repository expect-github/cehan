package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-12-16
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class PublicContractBean {

    @SerializedName("app_reminder")
    private String appReminder;

    public String getAppReminder() {
        if (appReminder == null) {
            return "";
        }
        return appReminder;
    }
}
