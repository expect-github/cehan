package com.nj.baijiayun.module_public.temple.js_manager.js_action;

import android.content.Context;

import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.temple.JsActionDataBean;
import com.nj.baijiayun.module_public.temple.js_manager.IJsAction;

/**
 * @author chengang
 * @date 2019-08-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.js_manager
 * @describe
 */
public class LoginJsAction implements IJsAction {
    @Override
    public void handlerData(Context context, AppWebView appWebView, JsActionDataBean jsActionDataBean) {
        AccountHelper.getInstance().logout();
    }
}
