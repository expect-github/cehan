package com.nj.baijiayun.module_public.bean;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_public.helper.config.BaseConfigBean;

/**
 * @author chengang
 * @date 2019-09-25
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean
 * @describe
 */
public class SystemWebConfigBean implements BaseConfigBean {

    /**
     * web_title : 百家云网校题库
     * web_logo : https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/image/20193kClmoIV791568710856.png
     * web_description : 网站描述
     * web_keywords : 网站关键字
     * copyright : 版权
     * record_no : 网站备案号
     * record_no_link : http://www.baidu.com
     * pc_address : https://testwx31.baijiayun.com
     * H5_address : https://testwxwap31.baijiayun.com
     * contact : 888888
     * download_banner : https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/image/2019ca4C4G3ra91568971496.jpg
     * download_ios_code : https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/image/2019LKJXNFVKKG1568971522.png
     * download_android_code : https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/image/2019AZBoJSyWeG1568971547.png
     * download_xcx_code : https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/image/201921eSbAG7S31568971555.png
     * download_title : SZS客户端宣传标题1
     * download_description : SZS客户端宣传说明1
     * price_ico : https://baijiayun-wangxiao.oss-cn-beijing.aliyuncs.com/uploads/image/20194KNelDn8Tf1568879756.png
     */

    @SerializedName("web_title")
    private String webTitle;
    @SerializedName("web_logo")
    private String webLogo;
    @SerializedName("web_description")
    private String webDescription;
    @SerializedName("web_keywords")
    private String webKeywords;
    private String copyright;
    @SerializedName("record_no")
    private String recordNo;
    @SerializedName("record_no_link")
    private String recordNoLink;
    @SerializedName("pc_address")
    private String pcAddress;
    @SerializedName("H5_address")
    private String H5Address;
    private String contact;
    @SerializedName("download_banner")
    private String downloadBanner;
    @SerializedName("download_ios_code")
    private String downloadIosCode;
    @SerializedName("download_android_code")
    private String downloadAndroidCode;
    @SerializedName("download_xcx_code")
    private String downloadXcxCode;
    @SerializedName("download_title")
    private String downloadTitle;
    @SerializedName("download_description")
    private String downloadDescription;
    @SerializedName("price_ico")
    private String priceIco;
    @SerializedName("mobile_logo")
    private String mobileLogo;
    @SerializedName("mobile_login_logo")
    private String mobileLoginLogo;

    public String getMobileLoginLogo() {
        return mobileLoginLogo;
    }

    public String getMobileLogo() {
        return mobileLogo;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWebLogo() {
        return webLogo;
    }

    public void setWebLogo(String webLogo) {
        this.webLogo = webLogo;
    }

    public String getWebDescription() {
        return webDescription;
    }

    public void setWebDescription(String webDescription) {
        this.webDescription = webDescription;
    }

    public String getWebKeywords() {
        return webKeywords;
    }

    public void setWebKeywords(String webKeywords) {
        this.webKeywords = webKeywords;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getRecordNoLink() {
        return recordNoLink;
    }

    public void setRecordNoLink(String recordNoLink) {
        this.recordNoLink = recordNoLink;
    }

    public String getPcAddress() {
        return pcAddress;
    }

    public void setPcAddress(String pcAddress) {
        this.pcAddress = pcAddress;
    }

    public String getH5Address() {
        return H5Address;
    }

    public void setH5Address(String H5Address) {
        this.H5Address = H5Address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDownloadBanner() {
        return downloadBanner;
    }

    public void setDownloadBanner(String downloadBanner) {
        this.downloadBanner = downloadBanner;
    }

    public String getDownloadIosCode() {
        return downloadIosCode;
    }

    public void setDownloadIosCode(String downloadIosCode) {
        this.downloadIosCode = downloadIosCode;
    }

    public String getDownloadAndroidCode() {
        return downloadAndroidCode;
    }

    public void setDownloadAndroidCode(String downloadAndroidCode) {
        this.downloadAndroidCode = downloadAndroidCode;
    }

    public String getDownloadXcxCode() {
        return downloadXcxCode;
    }

    public void setDownloadXcxCode(String downloadXcxCode) {
        this.downloadXcxCode = downloadXcxCode;
    }

    public String getDownloadTitle() {
        return downloadTitle;
    }

    public void setDownloadTitle(String downloadTitle) {
        this.downloadTitle = downloadTitle;
    }

    public String getDownloadDescription() {
        return downloadDescription;
    }

    public void setDownloadDescription(String downloadDescription) {
        this.downloadDescription = downloadDescription;
    }

    public String getPriceIco() {
        return priceIco;
    }

    public void setPriceIco(String priceIco) {
        this.priceIco = priceIco;
    }

    @Override
    public long getVersion() {
        return 0;
    }
}
