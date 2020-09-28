package com.nj.baijiayun.module_common.helper;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nj.baijiayun.module_common.R;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.tencent.smtt.sdk.WebSettings;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;


/**
 * @author chengang
 * @date 2019-12-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name ren.yale.android.cachewebview
 * @describe
 */
public class WebViewPool {

    private static volatile WebViewPool singleton = null;
    private List<AppWebView> available;
    private List<AppWebView> inUse;
    private static final byte[] lock = new byte[]{};
    private int maxSize = 5;
    private int initWebViewSize = 1;
    private int currentSize = 0;
    private Context context;

    private WebViewPool() {
        available = new ArrayList<>();
        inUse = new ArrayList<>();
    }

    public static WebViewPool getInstance() {
        if (singleton == null) {
            synchronized (WebViewPool.class) {
                if (singleton == null) {
                    singleton = new WebViewPool();
                }
            }
        }
        return singleton;
    }

    /**
     * Webview 初始化
     * 最好放在application oncreate里
     */
    public void init(Context context, String initUrl) {
        this.context = context;
        create(context, initUrl);
    }

    private void create(Context context, String initUrl) {

        for (int i = 0; i < initWebViewSize; i++) {
            AppWebView webView = new AppWebView(context);
            initWebView(webView);
            Log.d("create", "WebviewPoolinit");
            available.add(webView);
        }


    }


    private void initWebView(AppWebView webView) {
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");
        //设置编码格式
        //页面白屏问题
        webView.setBackgroundColor(ContextCompat.getColor(webView.getContext(), android.R.color.transparent));
        webView.setBackgroundResource(R.color.white);

    }


    /**
     * 获取webview
     */
    public AppWebView getWebView(Context context) {
        Log.d("TAG", " getWebViewavailable" + available.size());
        synchronized (lock) {
            AppWebView webView;
            if (available.size() > 0) {
                webView = available.get(0);
                available.remove(0);
                currentSize++;
                inUse.add(webView);
            } else {
                webView = new AppWebView(context);
                initWebView(webView);
                inUse.add(webView);
                currentSize++;
            }
            webView.loadUrl("about:blank");
            webView.clearHistory();
            return webView;
        }
    }

    /**
     * 回收webview ,不解绑
     *
     * @param webView 需要被回收的webview
     */
    public void removeWebView(AppWebView webView) {
        webView.stopLoading();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.clearCache(true);
        webView.clearHistory();

        synchronized (lock) {
            inUse.remove(webView);
            webView = null;

            AppWebView appWebView = new AppWebView(context);
            initWebView(appWebView);
            available.add(appWebView);
        }
    }

    /**
     * 回收webview ,解绑
     *
     * @param webView 需要被回收的webview
     */
    public void removeWebViewUnbind(AppWebView webView) {
        ((ViewGroup) webView.getParent()).removeView(webView);
        removeWebView(webView);
    }

    /**
     * 设置webview池个数
     *
     * @param size webview池个数
     */
    public void setMaxPoolSize(int size) {
        synchronized (lock) {
            maxSize = size;
        }
    }
}
