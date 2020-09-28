package com.nj.baijiayun.module_public.temple.js_manager.js_action;

import android.content.Context;

import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.temple.JsActionDataBean;
import com.nj.baijiayun.module_public.temple.js_manager.IJsAction;

/**
 * @author chengang
 * @date 2019-08-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.js_manager.js_action
 * @describe
 */
public class SetAppStatusBarColorJsAction implements IJsAction {
    @Override
    public void handlerData(Context context, AppWebView appWebView, JsActionDataBean jsActionDataBean) {
        //不去处理
//        if (context instanceof BaseAppActivity) {
//            StatusBarUtil.setColor((Activity) context, Color.parseColor(jsActionDataBean.getParams().getColor()), 0);
//        }
    }
}
