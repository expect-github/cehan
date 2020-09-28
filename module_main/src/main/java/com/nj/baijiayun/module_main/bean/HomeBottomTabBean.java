package com.nj.baijiayun.module_main.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.nj.baijiayun.module_main.helper.HomeTabPageHelper;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;

/**
 * @author chengang
 * @date 2020-02-15
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class HomeBottomTabBean {
//    课程  Course
//    一对一约课  Oto
//    约课记录  Record
//    嗨拼团  Group
//    讲师  ShowTeachers
//    图书  BookList
//    资讯  News
//    文库  Library
//    社区  Community
//    练习  Practise
//    学习日历  StudyCalendar
//    积分商城  IntegralStore
//    签到  Sign
//    作业  Homework
//    消息  Message

//    {
//        "name": "首页",
//            "url": "Index",
//            "nav_img": "https://testwx41.baijiayun.com/nav/index_2x.png.https://testwx41.baijiayun.com/nav/index_3x.png",
//            "nav_img_checked": "https://testwx41.baijiayun.com/nav/index_checked_2x.png,https://testwx41.baijiayun.com/nav/index_checked_3x.png",
//            "is_outer": 0,
//            "sort": 1,
//            "type": 1,
//            "status": 1,
//            "id": 81
//    }

    @SerializedName("name")
    private String title;
    @SerializedName("nav_img_checked")
    private String selectIcon;
    @SerializedName("nav_img")
    private String unSelectIcon;
    @SerializedName("url")
    private String url;

    @SerializedName("status")
    private int isVisible;
    @SerializedName("is_outer")
    private int isWebUrlLink;

    private boolean noNeedShowTitle;

    public String getSelectIcon() {
        if (TextUtils.isEmpty(selectIcon) || !selectIcon.contains(",")) {
            return selectIcon;
        }

        return selectIcon.split(",")[1];
    }

    public String getUnSelectIcon() {
        if (TextUtils.isEmpty(unSelectIcon) || !unSelectIcon.contains(",")) {
            return unSelectIcon;
        }
        return unSelectIcon.split(",")[1];
    }

    public String getRouter() {
        return url == null ? "" : url;
    }

    public String getWebUrl() {
        return url;
    }

    public boolean isAppInnerRouter() {
        return HomeTabPageHelper.APP_INNER_FRAGMENT_MAP.containsKey(url);
    }

    public String getTitle() {
        return noNeedShowTitle ? "" : title;
    }

    public String getH5Path() {
        return ConstsH5Url.getUrlByRouterKey(url);
    }

    public boolean isWebUrlLink() {
        return isWebUrlLink == 1 || TextUtils.isEmpty(getH5Path());
    }

    boolean isVisible() {
        return isVisible == 1;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setNoNeedShowTitle(boolean noNeedShowTitle) {
        this.noNeedShowTitle = noNeedShowTitle;
    }

    public boolean isNoNeedShowTitle() {
        return noNeedShowTitle;
    }
}
