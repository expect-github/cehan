package com.nj.baijiayun.module_public.temple.js_manager.js_action;

import android.app.Activity;
import android.content.Context;

import com.nj.baijiayun.basic.manager.AppManager;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.temple.JsActionDataBean;
import com.nj.baijiayun.module_public.temple.js_manager.IJsAction;
import com.nj.baijiayun.module_public.temple.js_manager.JsActionHelper;
import com.tencent.smtt.sdk.WebBackForwardList;

/**
 * @author chengang
 * @date 2020-03-03
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.js_manager.js_action
 * @describe
 */
public class PaySuccessRouterCompleteCallbackJsAction implements IJsAction {
    @Override
    public void handlerData(Context context, AppWebView appWebView, JsActionDataBean jsActionDataBean) {
        Activity activity = AppManager.getAppManager().currentActivity();
        int size = AppManager.getActivityStack().size();
        if (activity != null && size > 1 && !activity.getClass().getName().contains("MainActivity")) {
            AppManager.getAppManager().finishActivity(activity);
        } else {
            WebBackForwardList webBackForwardList = appWebView.copyBackForwardList();
            if (webBackForwardList.getSize() > 0) {
                String url = webBackForwardList.getItemAtIndex(0).getUrl();
                appWebView.loadUrl(url);
            }
        }
        JsActionHelper.tryShowWeChatOfficialAccount();



    }
}
