package com.nj.baijiayun.module_public.temple.js_manager.js_action;

import android.content.Context;
import android.widget.ImageView;

import com.nj.baijiayun.module_common.helper.ToolBarHelper;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.temple.BaseAppWebViewActivity;
import com.nj.baijiayun.module_public.temple.JsActionDataBean;
import com.nj.baijiayun.module_public.temple.js_manager.IJsAction;

import androidx.appcompat.widget.Toolbar;

/**
 * @author chengang
 * @date 2019-12-24
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.js_manager.js_action
 * @describe
 */
public class ShowShareJsAction implements IJsAction {
    @Override
    public void handlerData(Context context, AppWebView appWebView, JsActionDataBean jsActionDataBean) {
        if (context instanceof BaseAppWebViewActivity) {
            Toolbar toolBar = ((BaseAppWebViewActivity) context).getToolBar();
            if (toolBar.getChildAt(toolBar.getChildCount()-1) instanceof ImageView) {
                return;
            }
            ToolBarHelper.addRightImageView(((BaseAppWebViewActivity) context).getToolBar(),
                    R.drawable.public_ic_share, v -> appWebView.callHandler("getShareInfo", "", null));

        }
    }
}
