package com.nj.baijiayun.module_public.temple.js_manager.js_action;

import android.content.Context;

import com.nj.baijiayun.basic.manager.AppManager;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.helper.SelectPhotoHelper;
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
public class SelectPhotoJsAction implements IJsAction {
    @Override
    public void handlerData(Context context, AppWebView appWebView, JsActionDataBean jsActionDataBean) {
        if (jsActionDataBean.getParams().isTakePhoto()) {
            SelectPhotoHelper.takePhoto(AppManager.getAppManager().currentActivity(), false);
        } else {
            SelectPhotoHelper.openAlbumn(AppManager.getAppManager().currentActivity(), jsActionDataBean.getParams().getNum(), false);
        }
    }
}
