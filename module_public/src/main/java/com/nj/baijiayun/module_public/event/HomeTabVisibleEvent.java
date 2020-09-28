package com.nj.baijiayun.module_public.event;

/**
 * @author chengang
 * @date 2019-09-17
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.event
 * @describe
 */
public class HomeTabVisibleEvent {
    private boolean isShow;

    public boolean isShow() {
        return isShow;
    }

    public HomeTabVisibleEvent setShow(boolean show) {
        isShow = show;
        return this;
    }

}
