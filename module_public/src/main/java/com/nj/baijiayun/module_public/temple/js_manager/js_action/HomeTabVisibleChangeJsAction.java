package com.nj.baijiayun.module_public.temple.js_manager.js_action;

import android.content.Context;

import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.temple.JsActionDataBean;
import com.nj.baijiayun.module_public.temple.js_manager.IJsAction;

/**
 * @author chengang
 * @date 2019-09-17
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.js_manager.js_action
 * @describe 这个已经被废弃,app内已经实现
 */
@Deprecated
public class HomeTabVisibleChangeJsAction implements IJsAction {
    @Override
    public void handlerData(Context context, AppWebView appWebView, JsActionDataBean jsActionDataBean) {
//        RxBus.getInstanceBus().post(new HomeTabVisibleEvent().setShow(jsActionDataBean.getParams().isShowHomeTab()));
    }
}
