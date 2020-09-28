package com.nj.baijiayun.module_common.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ValueCallback;

import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.R;
import com.nj.baijiayun.module_common.adapter.WebResourceRequestAdapter;
import com.nj.baijiayun.module_common.adapter.WebResourceResponseAdapter;
import com.nj.baijiayun.module_common.helper.SoftKeyInputHelper;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_common.widget.MyWebChromeClient;
import com.nj.baijiayun.module_common.widget.WebUtils;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;

import java.util.Objects;

import androidx.annotation.Nullable;
import ren.yale.android.cachewebviewlib.WebViewCacheInterceptorInst;

/**
 * @author chengang
 * @date 2019-06-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.base
 * @describe
 */
public class BaseWebViewFragment extends BaseAppFragment {
    private AppWebView webView;
    public String url, data;
    private TitleCallBack titleCallBack;
    protected ValueCallback valueCallback;
    private static int INTENT_PHONE = 1111;
    private boolean needClearHistory = false;
    private boolean isLoadError = false;


    @Override
    public void initParms(Bundle params) {
        super.initParms(params);
        if (params == null) {
            return;
        }
        this.url = params.getString("url");
        this.data = params.getString("data");
    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.common_activity_app_web_view;
    }


    @Override
    protected void initView(View mContextView) {
        this.webView = mContextView.findViewById(R.id.appWebView);
//        ((FrameLayout) mContextView.findViewById(R.id.frameLayout)).addView(webView);
        Objects.requireNonNull(getActivity()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        loadUrl();
    }

    @Override
    public void registerListener() {

        if (multipleStatusView != null) {
            multipleStatusView.setOnRetryClickListener(v -> {
                webView.onResume();
                webView.reload();
            });
        }
        webView.setWebViewClient(new BridgeWebViewClient(webView) {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isLoadError = false;
                showLoadView();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                webView.getSettings().setBlockNetworkImage(false);
//                if (!webView.getSettings().getLoadsImagesAutomatically()) {
//                    webView.getSettings().setLoadsImagesAutomatically(true);
//                }
                super.onPageFinished(view, url);

            }

            @Override
            public void onLoadResource(WebView webView, String s) {
                super.onLoadResource(webView, s);

            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                onLoadError();

            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);

            }

            @Override
            public void doUpdateVisitedHistory(WebView webView, String s, boolean b) {
                super.doUpdateVisitedHistory(webView, s, b);
                Logger.d("doUpdateVisitedHistory--->" + s);
                if (s.startsWith("http")) {
                    urlChange(s);
                }
                if (needClearHistory) {
                    needClearHistory = false;
                    webView.clearHistory();//清除历史记录
                }


            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {


                WebView.HitTestResult hitTestResult = webView.getHitTestResult();
                //hitTestResult==null解决重定向问题(刷新后不能退出的bug)
                if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                    return true;
                }

                return handlerOverrideUrl(super.shouldOverrideUrlLoading(view, url), view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return handlerOverrideUrl(super.shouldOverrideUrlLoading(view, request), view, request.getUrl().toString());
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
                return WebResourceResponseAdapter.adapter(WebViewCacheInterceptorInst.getInstance().
                        interceptRequest(s));
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {

                return WebResourceResponseAdapter.adapter(WebViewCacheInterceptorInst.getInstance().
                        interceptRequest(WebResourceRequestAdapter.adapter(webResourceRequest)));
            }
        });
        webView.setWebChromeClient(new MyWebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (titleCallBack != null && !"about:blank".equals(title)) {
                    titleCallBack.call(title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Logger.d("onProgressChanged" + newProgress);
                if (newProgress == 100) {
                    onLoadComplete();
                }
            }
        }.setBnWebFileChoseListener(valueCallback -> {
            BaseWebViewFragment.this.valueCallback = valueCallback;
            fileChoseListener(valueCallback);
        }));
    }

    protected void fileChoseListener(ValueCallback valueCallback) {
        SoftKeyInputHelper.hideSoftInput(getActivity());
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BaseWebViewFragment.this.valueCallback = valueCallback;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, INTENT_PHONE);
            }
        }, 200);
    }

    public boolean handlerOverrideUrl(boolean shouldOverrideUrlLoading, WebView view, String url) {
        return shouldOverrideUrlLoading;
    }


    @Override
    public void processLogic() {

    }

    public void urlChange(String url) {

    }

    public void loadUrl() {
        if (url != null) {
            webView.loadUrl(url);
            WebViewCacheInterceptorInst.getInstance().loadUrl(url, webView.getSettings().getUserAgentString());

        } else {
            webView.loadHtmlContent(data);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_PHONE) {
            if (resultCode == Activity.RESULT_OK) {
                WebUtils.seleteH5File(data, valueCallback);
            } else {
                clearValueCallBack();
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d("onDestroyView");
        try {
            webView.release();
            webView = null;
        } catch (Exception ee) {
            Logger.e("X5onDestroyView"+ee.getMessage());
        }
    }

    public AppWebView getWebView() {
        return webView;
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }

    public void onLoadError() {
        Logger.d("webviewLoad onReceivedError");

        isLoadError = true;
        showErrorDataView();
    }

    public void onLoadComplete() {
        Logger.d("webviewLoad onLoadComplete" + isLoadError);
        if (!isLoadError) {
            showContentView();
        }
    }

    public void setTitleCallBack(TitleCallBack titleCallBack) {
        this.titleCallBack = titleCallBack;
    }

    public interface TitleCallBack {
        void call(String title);
    }

    private boolean isLoadError() {
        return isLoadError;
    }

    public void setNeedClearHistory() {
        this.needClearHistory = true;
    }

    public String getFirstLoadUrl() {
        return this.url;
    }

    protected void clearValueCallBack() {
        if (valueCallback != null) {
            valueCallback.onReceiveValue(null);
            valueCallback = null;
        }
    }

}
