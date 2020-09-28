package com.nj.baijiayun.module_public.temple.js_manager.js_action;

import android.content.Context;

import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.helper.AppBarHelper;
import com.nj.baijiayun.module_public.temple.JsActionDataBean;
import com.nj.baijiayun.module_public.temple.js_manager.IJsAction;

/**
 * @author chengang
 * @date 2020-03-03
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.js_manager.js_action
 * @describe
 */
public class DistributeShareJsAction implements IJsAction {
    @Override
    public void handlerData(Context context, AppWebView appWebView, JsActionDataBean jsActionDataBean) {
        AppBarHelper.clickDistributeShare(context,appWebView.getUrl(),jsActionDataBean.getParams().getPosterUrl());
    }
}
