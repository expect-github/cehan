package com.nj.baijiayun.module_main.fragments.temple;

import com.nj.baijiayun.basic.rxbus.RxBus;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_public.event.HomeTabVisibleEvent;

/**
 * @author chengang
 * @date 2020-02-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.fragments.temple
 * @describe 这里需要自己实现 跳转新页面 假如跳到一个内部 我们需要自己显示隐藏
 */
public class WebViewAsMainPageFragment extends WebViewNormalTabFragment {
    private HomeTabVisibleEvent homeTabVisibleEvent;
    private boolean isVisibleToUser = false;

    @Override
    public void urlChange(String url) {
        super.urlChange(url);
        //这个多个回调会导致主页显示


        if (isVisibleToUser) {

            Logger.d("HomeChange1: loadUrl_normal:" + url + "是否显示tab:" + isMainPage(url));
            Logger.d("HomeChange2: loadUrl__first:" + getFirstLoadUrl());
            setToolBarInPage(isMainPage(url));
            RxBus.getInstanceBus().post(getHomeTabVisibleEvent().setShow(isMainPage(url)));
        }
    }


    protected HomeTabVisibleEvent getHomeTabVisibleEvent() {
        if (homeTabVisibleEvent == null) {
            homeTabVisibleEvent = new HomeTabVisibleEvent();
        }
        return homeTabVisibleEvent;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;

    }
}



