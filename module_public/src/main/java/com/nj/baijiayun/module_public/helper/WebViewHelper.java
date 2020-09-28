package com.nj.baijiayun.module_public.helper;

import android.webkit.JavascriptInterface;

import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_common.widget.AppWebView;

import java.util.ArrayList;

import androidx.annotation.Keep;

/**
 * @author chengang
 * @date 2019-11-05
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
@Keep
public class WebViewHelper {

    public static void addImageClickListener(AppWebView appWebView) {
        appWebView.loadUrl("javascript:" +
                "alert(666);   " +
                "var imgs = document.getElementsByTagName('img');\n" +
                "    var imgArr = [imgs.length];\n" +
                "    for (var i = 0; i < imgs.length; i++) {\n" +
                "      imgArr[i] = imgs[i].src;\n" +
                "    }\n" +
                "    for (var j = 0; j < imgs.length; j++) {\n" +
                "      imgs[j].setAttribute(\"index\",j);\n" +
                "      imgs[j].onclick = function () {\n" +
                "        const obj = {}; \n" +
                "        obj.index=this.getAttribute(\"index\");\n" +
                "        obj.imgArr=imgArr;\n" +
                "        window." + BjyJavascriptInterface.FUNCTIONNAME + ".previewImg(JSON.stringify(obj));\n" +
                "      }\n" +
                "    } ");
    }

    @Keep
    private class ImageBean {
        public int index;
        ArrayList<String> imgArr;

    }


    @Keep
    public static class BjyJavascriptInterface {
        public static final String FUNCTIONNAME = "bjy";
        @Keep
        @JavascriptInterface
        public void previewImg(String json) {

            ImageBean imageBean = GsonHelper.getGsonInstance().fromJson(json, ImageBean.class);
            JumpHelper.jumpPreViewMultiImgView(imageBean.index, imageBean.imgArr);
        }

    }

}
