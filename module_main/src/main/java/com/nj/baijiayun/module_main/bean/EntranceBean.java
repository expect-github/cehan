package com.nj.baijiayun.module_main.bean;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.StringUtils;
import com.nj.baijiayun.module_public.helper.JumpHelper;

/**
 * @author chengang
 * @date 2019-10-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.bean
 * @describe 入口实体类 有path 跳path 有url跳h5 都没有
 */
public class EntranceBean {

    private String path;
    private String title;
    private String url;
    private int icon;
    private boolean needCheckLogin;
    private boolean webNeedTitle = false;

    public EntranceBean setWebNeedTitle(boolean webNeedTitle) {
        this.webNeedTitle = webNeedTitle;
        return this;
    }

    public boolean isWebNeedTitle() {
        return webNeedTitle;
    }

    public EntranceBean(String path, String url, String title, int icon, boolean needCheckLogin) {
        this.path = path;
        this.title = title;
        this.url = url;
        this.icon = icon;
        this.needCheckLogin = needCheckLogin;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isNeedCheckLogin() {
        return needCheckLogin;
    }

    public void setNeedCheckLogin(boolean needCheckLogin) {
        this.needCheckLogin = needCheckLogin;
    }


    public void tryJump() {
        if (jumpInterface != null) {
            jumpInterface.jump();
        } else {
            if (needCheckLogin) {
                if (JumpHelper.checkLogin()) {
                    return;
                }
            }
            if (!StringUtils.isEmpty(path)) {
                ARouter.getInstance().build(path).navigation();
            } else if (!StringUtils.isEmpty(url)) {
                if (isWebNeedTitle()) {
                    JumpHelper.jumpWebView(url);
                } else {
                    JumpHelper.jumpWebViewNoNeedAppTitle(url);

                }

            }
        }
    }

    public EntranceBean setJumpInterface(JumpInterface jumpInterface) {
        this.jumpInterface = jumpInterface;
        return this;
    }


    private JumpInterface jumpInterface;

    public interface JumpInterface {
        void jump();
    }


}
