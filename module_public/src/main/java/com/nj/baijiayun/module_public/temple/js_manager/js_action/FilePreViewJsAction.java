package com.nj.baijiayun.module_public.temple.js_manager.js_action;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.temple.JsActionDataBean;
import com.nj.baijiayun.module_public.temple.js_manager.IJsAction;

/**
 * @author chengang
 * @date 2019-09-11
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.js_manager.js_action
 * @describe
 */
public class FilePreViewJsAction implements IJsAction {
    @Override
    public void handlerData(Context context, AppWebView appWebView, JsActionDataBean jsActionDataBean) {


        ARouter.getInstance().build(RouterConstant.PAGE_PUBLIC_FILE_PREVIEW)
                .withString("fileUrl", jsActionDataBean.getParams().getFileUrl())
                .withString("fileName", jsActionDataBean.getParams().getFileName())
                .navigation();
    }
}
