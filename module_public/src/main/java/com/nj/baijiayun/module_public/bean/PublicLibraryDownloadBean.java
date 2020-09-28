package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-11-19
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class PublicLibraryDownloadBean {
    @SerializedName("download_url")
    private String downloadUrl;

    public String getDownloadUrl() {
        return downloadUrl;
    }
}
