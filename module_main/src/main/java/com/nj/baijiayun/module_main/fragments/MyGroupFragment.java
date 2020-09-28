package com.nj.baijiayun.module_main.fragments;

import com.nj.baijiayun.basic.rxbus.RxBus;
import com.nj.baijiayun.module_main.fragments.temple.URLCacheManager;
import com.nj.baijiayun.module_main.fragments.temple.WebViewAsMainPageFragment;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;

/**
 * @author chengang
 * @date 2020-02-20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.fragments
 * @describe
 */
public class MyGroupFragment extends WebViewAsMainPageFragment {

    @Override
    public void urlChange(String url) {
        if (isInMyGroup(url)) {
            RxBus.getInstanceBus().post(getHomeTabVisibleEvent().setShow(true));
            return;
        }
        super.urlChange(url);
    }

    private boolean isInMyGroup(String url) {
        return URLCacheManager.getInstance().isUrlInPathList(url, ConstsH5Url.getMyGroupPath());
    }
}
