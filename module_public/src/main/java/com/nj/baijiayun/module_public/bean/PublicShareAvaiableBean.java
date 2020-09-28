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
public class PublicShareAvaiableBean {


    /**
     * qqShare : {"is_available":2,"appId":"","callBack":""}
     * wxShare : {"is_available":2,"appId":"","callBack":""}
     */

    private QqShareBean qqShare;
    private WxShareBean wxShare;
    @SerializedName("share_template")
    private shareTemplate shareTemplateBean;

    public boolean isTemplateAvailable() {
        return (isQqShareAvailable() || isWeChatShareAvailable()) && (shareTemplateBean != null && shareTemplateBean.isAvailable == 1);
    }

    public boolean isQqShareAvailable() {
        return qqShare != null && qqShare.isAvailable == 1;
    }

    public boolean isWeChatShareAvailable() {
        return wxShare != null && wxShare.isAvailable == 1;
    }

    public QqShareBean getQqShare() {
        return qqShare;
    }

    public void setQqShare(QqShareBean qqShare) {
        this.qqShare = qqShare;
    }

    public WxShareBean getWxShare() {
        return wxShare;
    }

    public void setWxShare(WxShareBean wxShare) {
        this.wxShare = wxShare;
    }

    public static class QqShareBean {
        /**
         * is_available : 2
         * appId :
         * callBack :
         */

        @SerializedName("is_available")
        private int isAvailable;
        private String appId;

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
    }

    public static class WxShareBean {
        /**
         * is_available : 2
         * appId :
         * callBack :
         */

        @SerializedName("is_available")
        private int isAvailable;
        private String appId;

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
    }

    public static class shareTemplate {
        @SerializedName("is_available")
        private int isAvailable;

    }
}
