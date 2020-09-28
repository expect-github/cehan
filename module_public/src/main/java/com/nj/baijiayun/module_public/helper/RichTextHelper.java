package com.nj.baijiayun.module_public.helper;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.RichTextConfig;
import com.zzhoujay.richtext.callback.OnImageClickListener;

public class RichTextHelper {
    public static  RichTextConfig.RichTextConfigBuild getDefault(String resource)
    {
        return RichText.from(resource)
                .imageClick(getDefaultImageClickListener())
                .urlClick(url -> {
                    ARouter.getInstance().build(RouterConstant.PAGE_WEB_VIEW).withString("url",url).navigation();
                    return true;
                });

    }


    public static OnImageClickListener getDefaultImageClickListener() {
        return (imageUrls, position) -> ARouter.getInstance().build(RouterConstant.PAGE_PUBLIC_IMAGE_PREVIEW).withString("path", imageUrls.get(position)).navigation();
    }
}
