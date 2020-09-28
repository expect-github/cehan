package com.nj.baijiayun.module_public.utlis;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;
import cmb.pb.util.CMBKeyboardFunc;


/**
 * Created by ycw on 2017/3/17.
 */
public class YWTWebViewClient extends WebViewClient{



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        // 使用当前的WebView加载页面
        CMBKeyboardFunc kbFunc = new CMBKeyboardFunc((Activity) view.getContext());
        if(kbFunc.HandleUrlCall(view, request.getUrl().toString()) == false) {
            return super.shouldOverrideUrlLoading(view, view.getUrl());
        }else {
            return true;
        }
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.e("网页地址：",url);
        // 使用当前的WebView加载页面
        CMBKeyboardFunc kbFunc = new CMBKeyboardFunc((Activity) view.getContext());
        if(kbFunc.HandleUrlCall(view, url) == false) {
            return super.shouldOverrideUrlLoading(view, url);
        }else{
            return true;
        }
    }




}
