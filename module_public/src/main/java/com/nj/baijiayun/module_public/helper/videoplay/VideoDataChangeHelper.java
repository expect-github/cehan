package com.nj.baijiayun.module_public.helper.videoplay;

import com.nj.baijiayun.basic.utils.StringUtils;

/**
 * @author chengang
 * @date 2019-08-20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.videoplay
 * @describe
 */
public class VideoDataChangeHelper {

    public static BjyTokenWrapperBean OneToOneDataToBjyToken(OneToOneTokenWrapper data) {
        BjyTokenWrapperBean result = new BjyTokenWrapperBean();
        BjyTokenData e = new BjyTokenData();
        e.setSub_type(StringUtils.isEmpty(data.getSubType())?VideoPlayHelper.TYPE_ZHIBO:data.getSubType());

        e.setToken(data.getToken());
        e.setTypeLive();
        e.setRoom_id(data.getRoom_id());
        result.getTokenListData().add(e);
        result.getRoomParams().add(data);
        return result;
    }
}
