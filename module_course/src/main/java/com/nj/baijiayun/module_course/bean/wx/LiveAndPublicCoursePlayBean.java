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
public class LiveAndPublicCoursePlayBean {

    /**
     * user_type= : 0
     * type : 1
     * sub_type : zhibo
     * web_url : http://www.baijiayun.com/web/room/enter?room_id=18120685735865&user_number=3&user_name=15656272117&user_role=0&user_avatar=http%3A%2F%2Fwww.zywx.com%2Fuploads%2Fimages%2F20181128%2Fff5c4c7153a779220a1b4350aa56a8aa.jpg&sign=7e090140b9dbb26b3ff55c5b387631e0
     * client_url : baijiacloud://urlpath=http%3A%2F%2Fwww.baijiayun.com%2Fweb%2Froom%2Fenter%3Froom_id%3D18120685735865%26user_number%3D3%26user_name%3D15656272117%26user_role%3D0%26user_avatar%3Dhttp%253A%252F%252Fwww.zywx.com%252Fuploads%252Fimages%252F20181128%252Fff5c4c7153a779220a1b4350aa56a8aa.jpg%26sign%3D7e090140b9dbb26b3ff55c5b387631e0&token=token&ts=ts
     */

    @SerializedName("user_type=")
    private int userType;
    private int type;
    @SerializedName("sub_type")
    private String subType;
    @SerializedName("web_url")
    private String webUrl;
    @SerializedName("client_url")
    private String clientUrl;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }
}
