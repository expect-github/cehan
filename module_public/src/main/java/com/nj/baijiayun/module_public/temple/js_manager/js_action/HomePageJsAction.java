package com.nj.baijiayun.module_public.temple.js_manager.js_action;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.temple.JsActionDataBean;
import com.nj.baijiayun.module_public.temple.js_manager.IJsAction;

/**
 * @author chengang
 * @date 2019-08-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.js_manager.js_action
 * @describe 这个在h5支付完成 会调用这个
 */
public class HomePageJsAction implements IJsAction {
    @Override
    public void handlerData(Context context, AppWebView appWebView, JsActionDataBean jsActionDataBean) {
        try {
            LiveDataBus.get().with(LiveDataBusEvent.MAIN_TAB_SWITCH).postValue(jsActionDataBean.getParams().getRouter());
            ARouter.getInstance().build(RouterConstant.PAGE_MAIN).navigation();
        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }
}