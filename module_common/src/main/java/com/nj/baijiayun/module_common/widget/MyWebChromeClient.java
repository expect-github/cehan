package com.nj.baijiayun.module_common.widget;

import android.net.Uri;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;


/**
 * @author chengang
 * @date 2019-09-07
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.widget
 * @describe
 */
public class MyWebChromeClient extends WebChromeClient {

    private WebFileChoseListener webFileChoseListener;
    // 3.0 + 调用这个方法
    public void openFileChooser(ValueCallback filePathCallback, String acceptType) {
        if (webFileChoseListener!= null){
            webFileChoseListener.getFile(filePathCallback);
        }
    }

    // Android > 4.1.1 调用这个方法
    @Override
    public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture) {
        if (webFileChoseListener!= null){
            webFileChoseListener.getFile(filePathCallback);
        }
    }

    // Android > 5.0 调用这个方法
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        if (webFileChoseListener!= null){
            webFileChoseListener.getFile(filePathCallback);
        }
        return true;
    }

    public MyWebChromeClient setBnWebFileChoseListener(WebFileChoseListener webFileChoseListener) {
        this.webFileChoseListener= webFileChoseListener;
        return this;
    }
}

