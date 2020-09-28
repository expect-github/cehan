package com.nj.baijiayun.module_public.temple.js_manager;

import android.content.Context;

import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.temple.JsActionDataBean;

import io.realm.internal.Keep;

/**
 * @author chengang
 * @date 2019-08-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple
 * @describe
 */
@Keep
public interface IJsAction {
    void handlerData(Context context, AppWebView appWebView, JsActionDataBean jsActionDataBean);

}
