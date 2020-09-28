package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author chengang
 * @date 2020-02-04
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class PublicOauthBean {
    /**
     * qqLogin : {"is_available":1,"appId":"101719978","callBack":"https://testwx40.baijiayun.com/login"}
     * wxLogin : {"is_available":1,"appId":"wx10f5cee9b647940b","callBack":"https://testwx40.baijiayun.com/login"}
     */

    private QqLoginBean qqLogin;
    private WxLoginBean wxLogin;
    private mpLogin mpLogin;

    public QqLoginBean getQqLogin() {
        return qqLogin;
    }

    public void setQqLogin(QqLoginBean qqLogin) {
        this.qqLogin = qqLogin;
    }

    public WxLoginBean getWxLogin() {
        return wxLogin;
    }

    public void setWxLogin(WxLoginBean wxLogin) {
        this.wxLogin = wxLogin;
    }

    public mpLogin getMmpLogin() {
        return mpLogin;
    }

    public static class QqLoginBean {
        /**
         * is_available : 1
         * appId : 101719978
         * callBack : https://testwx40.baijiayun.com/login
         */

        @SerializedName("is_available")
        private int isAvailable;
        private String appId;
        private String callBack;

        public boolean isAvailable() {
            return isAvailable == 1;
        }

        public int getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(int isAvailable) {
            this.isAvailable = isAvailable;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getCallBack() {
            return callBack;
        }

        public void setCallBack(String callBack) {
            this.callBack = callBack;
        }
    }

    public static class WxLoginBean {
        /**
         * is_available : 1
         * appId : wx10f5cee9b647940b
         * callBack : https://testwx40.baijiayun.com/login
         */

        @SerializedName("is_available")
        private int isAvailable;
        private String appId;
        private String callBack;

        public boolean isAvailable() {
            return isAvailable == 1;
        }

        public int getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(int isAvailable) {
            this.isAvailable = isAvailable;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getCallBack() {
            return callBack;
        }

        public void setCallBack(String callBack) {
            this.callBack = callBack;
        }
    }

    /**
     * app_wx_login_config : {"appId":"wx74ee606ca5e4943e","appSecret":"19217fdd41b62cc4b7a8d38a5bc50dcf"}
     * app_qq_login_config : {"appId":"1108027472","appSecret":"YVXEIt65dMgbfOUB"}
     */

    public static class mpLogin {

        /**
         * is_available : 2
         * appId :
         * callBack :
         * mp_switch : 1
         * mp_poster : https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/image/2020z9QHdFmAtW1583461112.jpeg
         */


        @SerializedName("mp_switch")
        private int mpSwitch;
        @SerializedName("mp_poster")
        private String mpPoster;

        public int getMpSwitch() {
            return mpSwitch;
        }

        public void setMpSwitch(int mpSwitch) {
            this.mpSwitch = mpSwitch;
        }

        public String getMpPoster() {
            return mpPoster;
        }

        public void setMpPoster(String mpPoster) {
            this.mpPoster = mpPoster;
        }

        public boolean isSwitch() {
            return mpSwitch == 1;
        }
    }

}
