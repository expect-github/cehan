package com.nj.baijiayun.module_main.bean;

import com.nj.baijiayun.module_public.consts.ConstsH5Url;

/**
 * @author chengang
 * @date 2019-08-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe
 */
public class UserItemBean {
    private String path;
    private String title;
    private String url;
    private String icon;
    private int group;
    private boolean isUnRead = false;
    private boolean needCheckLogin = true;
    private int unReadCount = 0;

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public boolean isNeedCheckLogin() {
        return needCheckLogin;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isUnRead() {
        return isUnRead;
    }

    public void setUnRead(boolean unRead) {
        isUnRead = unRead;
    }

    public boolean isUrlNotEmpty() {
        return url != null && url.length() > 0;
    }

    public String getUrl() {
        return ConstsH5Url.getUrl(url);
    }

    private boolean needBottomLine = false;
    private boolean needBottomMargin = false;

    public boolean isNeedBottomMargin() {
        return needBottomMargin;
    }

    public void setNeedBottomMargin(boolean needBottomMargin) {
        this.needBottomMargin = needBottomMargin;
    }

    public boolean isNeedBottomLine() {
        return needBottomLine;
    }

    public void setNeedBottomLine(boolean needBottomLine) {
        this.needBottomLine = needBottomLine;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGroup() {
        return group;
    }

    public String getUrlPath() {
        return url;
    }

    public boolean isProtectEyes() {
        return "main_ic_protect_eyes".equals(icon);
    }

    public boolean isPersonService() {
        return "main_ic_service".equals(icon);
    }


}

