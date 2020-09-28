package com.nj.baijiayun.module_course.bean.wx;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2019-08-03
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.bean.wx
 * @describe
 */
public class AudioVideoTokenBean {

    /**
     * video_id : 14850992
     * token : d_3aBA67SYj86B8NlvCIdKS-oUP8o2bMWlviAK2113-7NRdnBABnNDTSEzZrILF4
     */

    @SerializedName("video_id")
    private int videoId;
    private String token;

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
