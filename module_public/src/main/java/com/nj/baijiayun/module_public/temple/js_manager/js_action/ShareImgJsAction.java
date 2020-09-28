package com.nj.baijiayun.module_public.temple.js_manager.js_action;

import android.content.Context;

import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.helper.ShareHelper;
import com.nj.baijiayun.module_public.temple.JsActionDataBean;
import com.nj.baijiayun.module_public.temple.js_manager.IJsAction;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author chengang
 * @date 2019-11-18
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.js_manager.js_action
 * @describe
 */
public class ShareImgJsAction implements IJsAction {

    private ShareHelper shareHelper;

    @Override
    public void handlerData(Context context, AppWebView appWebView, JsActionDataBean jsActionDataBean) {

        if (context instanceof BaseAppActivity) {
            shareHelper = new ShareHelper();
            try {
                shareHelper.showDialog(((BaseAppActivity) context),
                        ((BaseAppActivity) context),
                        jsActionDataBean.getParams().createShareInfo(),
                        jsActionDataBean.getParams().getApiUrl());
            } catch (Exception e) {
                Logger.e(e.getMessage());
            }

        }

    }

    private String urlDecode(String url) {
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
