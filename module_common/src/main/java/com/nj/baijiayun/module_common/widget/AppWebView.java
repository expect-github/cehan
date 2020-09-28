package com.nj.baijiayun.module_common.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.nj.baijiayun.module_common.BuildConfig;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.sdk.WebSettings;

import java.text.MessageFormat;

/**
 * @author chengang
 * @date 2019-08-10
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.widget
 * @describe
 */
public class AppWebView extends BridgeWebView {
    public AppWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AppWebView(Context context) {
        this(context, null);
    }

    public void loadHtmlContent(String data) {
        loadData(getHtmlData(data), "text/html;charset=UTF-8", "UTF-8");
    }

    /**
     * 拼接html字符串片段
     *
     * @param bodyHtml
     * @return
     */
    private String getHtmlData(String bodyHtml) {
        String head = "<head>" +
                "<meta charset=\"UTF-8\" name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width:100% !important; width:auto; height:auto;} video  {height:auto; max-width:100% !important;width: 100%;} body{font-size:14px;color:#262626;}  </style>" +
                "</head>";

        String script = "";
        return MessageFormat.format("<html>{0}<body style=\"height:auto;max-width: 100%; width:auto;padding: 0;margin:0\">{1}<script>{2}</script></body></html>", head, bodyHtml, script);
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        WebSettings webSettings = getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setTextZoom(100);
        webSettings.setJavaScriptEnabled(true);
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        // init webview settings
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);

        //设置Web视图
//        webSettings.setBlockNetworkImage(true);
        //下面方法去掉
        IX5WebViewExtension ix5 = getX5WebViewExtension();
        if (null != ix5) {
            ix5.setScrollBarFadingEnabled(false);
        }
    }

    public void pageFinishLoadImg() {
        getSettings().setBlockNetworkImage(false);
        if (!getSettings().getLoadsImagesAutomatically()) {
            getSettings().setLoadsImagesAutomatically(true);
        }
    }

    public void resizeVideo() {
        loadUrl("javascript:var videos = document.getElementsByTagName('video');\n" +
                "    for (var i = 0; i < videos.length; i++) {\n" +
                "      if (videos[i].width !== 0 && videos[i].height !== 0)\n" +
                "        videos[i].height = videos[i].offsetWidth * videos[i].height / videos[i].width;\n" +
                "    }");
    }

    public void release() {
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
        }
        stopLoading();
        removeAllViewsInLayout();
        removeAllViews();
        setWebViewClient(null);
        destroy();
    }


}
