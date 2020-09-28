package com.nj.baijiayun.module_public.temple.js_manager;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.temple.JsActionDataBean;

/**
 * @author chengang
 * @date 2019-11-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.js_manager
 * @describe
 */
public class VideoPlayJsAction implements IJsAction {
    @Override
    public void handlerData(Context context, AppWebView appWebView, JsActionDataBean jsActionDataBean) {
        ARouter.getInstance()
                .build(RouterConstant.PAGE_VIDEO_PROXY)
                .withString("type", "video")
                .withBoolean("isOffline", true)
                .withString("videoUrl", jsActionDataBean.getParams().getVideoUrl())
                .navigation();


    }
}
