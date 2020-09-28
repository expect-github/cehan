package com.nj.baijiayun.module_main.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2020-03-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class DistributeApplyBean {


    /**
     * id : 20
     * uid : 1
     * parent_uid : 0
     * status : 4  状态 1待审核 2通过 3拒绝 4清退
     * refuse_reason :
     * check_success_at : 1582968797
     */

    private int id;
    private int uid;
    @SerializedName("parent_uid")
    private int parentUid;
    private int status;
    @SerializedName("refuse_reason")
    private String refuseReason;
    @SerializedName("check_success_at")
    private long checkSuccessAt;

    public boolean isNeedGoApplyPage() {
        return !(status == 2 || status == 4);
    }
}
