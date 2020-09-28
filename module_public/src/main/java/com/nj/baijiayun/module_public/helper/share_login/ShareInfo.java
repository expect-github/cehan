package com.nj.baijiayun.module_public.helper.share_login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import cn.jiguang.share.android.api.Platform;

public class ShareInfo implements Parcelable, Cloneable {

    /**
     * title : 小学教育课程
     * abstract : 小学教育课程
     * image : http://www.zywx.com/uploads/images/20181128/f46020aec7a5dfa9cb2fa557b8be7a48.jpg
     * url : http://www.zywx.com/
     */
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String abstractX;
    @SerializedName("detail")
    private String detail;
    @SerializedName("cover_img")
    private String image;
    @SerializedName("url")
    private String url;
    private String shareTip;
    private String localImgPath;
    private int shareType = Platform.SHARE_WEBPAGE;

    public void openShareImg() {
        shareType = Platform.SHARE_IMAGE;
    }

    public int getShareType() {
        return shareType;
    }

    public void setLocalImgPath(String localImgPath) {
        this.localImgPath = localImgPath;
        openShareImg();
    }

    public String getLocalImgPath() {
        return localImgPath;
    }

    public ShareInfo() {
    }

    public ShareInfo(String title, String abstractX, String image, String url) {
        this.title = title;
        this.abstractX = abstractX;
        this.image = image;
        this.url = url;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ShareInfo shareInfo = null;
        try {
            shareInfo = (ShareInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return shareInfo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstract() {
        if (detail != null && detail.length() > 0) {
            return detail;
        }
        return abstractX;
    }

    public void setAbstract(String abstractX) {
        this.abstractX = abstractX;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public String getShareTip() {
        return shareTip;
    }

    public void setShareTip(String shareTip) {
        this.shareTip = shareTip;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.abstractX);
        dest.writeString(this.detail);
        dest.writeString(this.image);
        dest.writeString(this.url);
        dest.writeString(this.shareTip);
        dest.writeString(this.localImgPath);
        dest.writeInt(this.shareType);
    }

    protected ShareInfo(Parcel in) {
        this.title = in.readString();
        this.abstractX = in.readString();
        this.detail = in.readString();
        this.image = in.readString();
        this.url = in.readString();
        this.shareTip = in.readString();
        this.localImgPath = in.readString();
        this.shareType = in.readInt();
    }

    public static final Creator<ShareInfo> CREATOR = new Creator<ShareInfo>() {
        @Override
        public ShareInfo createFromParcel(Parcel source) {
            return new ShareInfo(source);
        }

        @Override
        public ShareInfo[] newArray(int size) {
            return new ShareInfo[size];
        }
    };
}
