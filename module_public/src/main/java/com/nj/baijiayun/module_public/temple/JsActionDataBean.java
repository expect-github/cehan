package com.nj.baijiayun.module_public.temple;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.helper.share_login.ShareInfo;

import androidx.annotation.Keep;

/**
 * @author chengang
 * @date 2019-08-10
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple
 * @describe
 */
@Keep
public class JsActionDataBean {

    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    @SerializedName("params")
    private Params params;

    public boolean isPay() {
        return "pay".equals(name);
    }

    public static class Params {

        //selectPhoto用到
        @SerializedName("num")
        private int num;
        @SerializedName("id")
        private int id;
        //跳转首页用到
        @SerializedName("index")
        private int index;
        @SerializedName("color")
        private String color;
        @SerializedName("orderNumber")
        private String orderNumber;
        @SerializedName("payType")
        private String payType;
        @SerializedName("photoType")
        private int photoType;
        @SerializedName("shop_id")
        private int shopId;
        @SerializedName("redirct_path")
        private String redirctPath;
        @SerializedName("course_id")
        private String courseId;
        @SerializedName("file_url")
        private String fileUrl;
        @SerializedName("visible")
        private int visible;
        @SerializedName("show_app_title")
        private int showAppTitle;
        //分享用到的
        @SerializedName("api_url")
        private String apiUrl;
        @SerializedName("share_url")
        private String shareUrl;
        @SerializedName("share_title")
        private String shareTitle;
        @SerializedName("share_abstract")
        private String shareAbstract;
        @SerializedName("share_img")
        private String shareImg;
        @SerializedName("video_url")
        private String videoUrl;
        @SerializedName("router")
        private String router;
        @SerializedName("path")
        private String path;
        @SerializedName("poster_url")
        private String posterUrl;
        @SerializedName("show_wx")
        private String showWx;

        public boolean needShowWx() {
            return !TextUtils.isEmpty(showWx);
        }

        public String getPosterUrl() {
            return posterUrl;
        }

        public String getPath() {
            return path;
        }

        public String getShareImg() {
            return shareImg;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public String getShareTitle() {
            return shareTitle;
        }

        public String getShareAbstract() {
            return shareAbstract;
        }

        public boolean isShowHomeTab() {
            return visible == 1;
        }

        public String getRouter() {
            return router;
        }

        @SerializedName("name")
        private String fileName;

        public String getFileUrl() {
            return fileUrl;
        }

        public String getFileName() {
            return fileName;
        }

        public String getRedirctPath() {
            return redirctPath;
        }

        public int getShopId() {
            return shopId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isTakePhoto() {
            return 1 == photoType;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getNum() {
            if (num < 1) {
                return 1;
            }
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getApiUrl() {
            return apiUrl;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public boolean needShowAppTitle() {
            return showAppTitle == 1;
        }


        public ShareInfo createShareInfo() {
            ShareInfo shareInfo = new ShareInfo();
            shareInfo.setUrl(ConstsH5Url.getUrl(getShareUrl()));
            shareInfo.setAbstract(getShareAbstract());
            shareInfo.setTitle(getShareTitle());
            shareInfo.setImage(getShareImg());
            return shareInfo;
        }
    }


    public Params getParams() {
        if (params == null) {
            return new Params();
        }
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public boolean needShowWeChatOfficialAccount() {
        return (url != null && url.contains("show_wx=1")) || getParams().needShowWx();
    }


}
