package com.nj.baijiayun.module_public.temple.js_manager.js_action;

import android.content.Context;

import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.helper.ShareHelper;
import com.nj.baijiayun.module_public.temple.JsActionDataBean;
import com.nj.baijiayun.module_public.temple.js_manager.IJsAction;

/**
 * @author chengang
 * @date 2019-12-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.js_manager.js_action
 * @describe
 */
public class ShareByShareInfoJsAction implements IJsAction {
    @Override
    public void handlerData(Context context, AppWebView appWebView, JsActionDataBean jsActionDataBean) {
        new ShareHelper().showDialog(((BaseAppActivity) context),((BaseAppActivity) context),jsActionDataBean.getParams().createShareInfo());
    }
}
