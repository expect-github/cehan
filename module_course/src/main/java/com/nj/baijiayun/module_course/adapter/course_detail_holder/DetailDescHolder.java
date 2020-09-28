package com.nj.baijiayun.module_course.adapter.course_detail_holder;

import android.view.View;
import android.view.ViewGroup;

import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_course.BuildConfig;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.WebViewHelper;
import com.nj.baijiayun.module_public.manager.LifecycleManager;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeViewHolder;
import com.nj.baijiayun.refresh.recycleview.BaseRecyclerAdapter;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import androidx.lifecycle.LifecycleOwner;

/**
 *
 * @author chengang
 * @date 2019-07-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.adapter.course_detail_holder
 * @describe
 */
public class DetailDescHolder extends BaseMultipleTypeViewHolder<String> {
    public DetailDescHolder(ViewGroup parent) {
        super(parent);
        getView(R.id.webView).setOnLongClickListener(v -> true);
        ((AppWebView) getView(R.id.webView)).addJavascriptInterface(new WebViewHelper.BjyJavascriptInterface(), WebViewHelper.BjyJavascriptInterface.FUNCTIONNAME);

        ((AppWebView) getView(R.id.webView)).setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                WebViewHelper.addImageClickListener((AppWebView) webView);
                ((AppWebView) getView(R.id.webView)).pageFinishLoadImg();
//                ((AppWebView) getView(R.id.webView)).resizeVideo();
                Logger.d("AppWebView onPageFinished");
                webView.loadUrl("javascript:document.body.style.marginTop=\"" + 10 + "px\"; void 0");

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                JumpHelper.jumpWebView(s);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
                JumpHelper.jumpWebView(webResourceRequest.getUrl().toString());
                return true;
            }
        });
        itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                //这里是因为进页面的时候没有在屏幕显示，导致滑动到webview之后发现高度计算错误，所以等webview可见的时候显示
                if (!isLoadSuccess) {
                    if (htmlData != null) {
                        ((AppWebView) getView(R.id.webView)).loadHtmlContent(htmlData);
                        isLoadSuccess = true;
                    }
                }
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });
        LifecycleManager.create((LifecycleOwner) getContext()).addLifecycleCallBack(new LifecycleManager.BaseObserver() {
            @Override
            public void onDestory() {
                super.onDestory();
                if (getView(R.id.webView) != null) {
                    ((AppWebView) getView(R.id.webView)).release();
                }
            }
        });
    }


    private boolean isLoadSuccess = false;
    private String htmlData;

    @Override
    public int bindLayout() {
        return R.layout.course_layout_detail_wx_desc;
    }

    @Override
    public void bindData(String model, int position, BaseRecyclerAdapter adapter) {
        this.htmlData = model;
        if(BuildConfig.DEBUG)
        {
            this.htmlData="<!DOCTYPE HTML>\n" +
                    "<html>\n" +
                    "<body style=\"background:red\">\n" +
                    "\n" +
                    "\n" +
                    "Your browser does not support the video tag.\n" +
                    "</video>\n" +
                    "\n" +
                    "<img width=\"100px\" style=\"clear:both\" src=\"https://weilinjiaoyu.oss-cn-hangzhou.aliyuncs.com/uploads/image/30d46ebd3c20fcbbcbd13c6b2a6c3c9b.jpg\" title=\"1570873787275196.jpg\" alt=\"3dd840d917382847539232f01d1d1b69.jpg\"/>\n" +
                    "<img width=\"100px\" src=\"https://weilinjiaoyu.oss-cn-hangzhou.aliyuncs.com/uploads/image/30d46ebd3c20fcbbcbd13c6b2a6c3c9b.jpg\" title=\"1570873787275196.jpg\" alt=\"3dd840d917382847539232f01d1d1b69.jpg\"/>\n" +
                    "   <img src=\"https://weilinjiaoyu.oss-cn-hangzhou.aliyuncs.com/uploads/image/30d46ebd3c20fcbbcbd13c6b2a6c3c9b.jpg\" title=\"1570873787275196.jpg\" alt=\"3dd840d917382847539232f01d1d1b69.jpg\"/>\n" +
                    "   \n" +
                    "   <img width=\"100px\" src=\"https://weilinjiaoyu.oss-cn-hangzhou.aliyuncs.com/uploads/image/30d46ebd3c20fcbbcbd13c6b2a6c3c9b.jpg\" title=\"1570873787275196.jpg\" alt=\"3dd840d917382847539232f01d1d1b69.jpg\"/>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";
        }

    }


}
