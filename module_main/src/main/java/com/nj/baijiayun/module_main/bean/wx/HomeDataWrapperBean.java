package com.nj.baijiayun.module_main.bean.wx;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author chengang
 * @date 2019-08-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean.wx
 * @describe
 */
public class HomeDataWrapperBean {

    @SerializedName("channel_info")
    private ChannelInfoBean channelInfo;
    private List<Object>list;

//    Map<String, String> jsonMap = GsonHelper.getGsonInstance().fromJson(json, new TypeToken<Map<String, String>>() {
//    }.getType());

    public ChannelInfoBean getChannelInfo() {
        return channelInfo;
    }

    public void setChannelInfo(ChannelInfoBean channelInfo) {
        this.channelInfo = channelInfo;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }
}
