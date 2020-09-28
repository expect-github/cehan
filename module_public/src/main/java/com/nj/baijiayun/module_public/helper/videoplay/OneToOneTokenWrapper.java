package com.nj.baijiayun.module_public.helper.videoplay;

import com.google.gson.annotations.SerializedName;

import io.realm.internal.Keep;

/**
 * @author chengang
 * @date 2019-08-20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.videoplay
 * @describe 纯属无奈 后台不保持一致的数据结构
 */
@Keep
public class OneToOneTokenWrapper  extends BjyTokenWrapperBean.LiveRoomParams{


    /**
     * room_id : 19081281520982
     * user_number : 2
     * user_name : 15151886784
     * user_role : 0
     * user_avatar : https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/image/201916NzGSNnsN1565616850.jpg
     * sign : 4b65726fb4b772b2fc4aa09540618d01
     */
    @SerializedName("sub_type")
    private String subType;
    @SerializedName("token")
    private String token;


    public String getSubType() {
        return subType;
    }

    public String getToken() {
        return token;
    }
}
