package ren.yale.android.cachewebviewlib;

import android.content.Context;

import java.io.File;

import ren.yale.android.cachewebviewlib.config.CacheExtensionConfig;

/**
 * @author chengang
 * @date 2020-03-03
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name ren.yale.android.cachewebviewlib
 * @describe
 */
public class WebViewCacheManager {

    private static volatile WebViewCacheManager singleton = null;

    private WebViewCacheManager() {
    }

    public static WebViewCacheManager getInstance() {
        if (singleton == null) {
            synchronized (WebViewCacheManager.class) {
                if (singleton == null) {
                    singleton = new WebViewCacheManager();
                }
            }
        }
        return singleton;
    }

    public void initDefaultConfig(Context context) {
        WebViewCacheInterceptor.Builder builder = new WebViewCacheInterceptor.Builder(context);

        //设置缓存路径，默认getCacheDir，名称CacheWebViewCache
        builder.setCachePath(new File(context.getCacheDir(), "cache_path_name"))
                .setCacheSize(1024 * 1024 * 100)//设置缓存大小，默认100M
                .setConnectTimeoutSecond(20)//设置http请求链接超时，默认20秒
                .setReadTimeoutSecond(20);//设置http请求链接读取超时，默认20秒
        CacheExtensionConfig extension = new CacheExtensionConfig();
        extension.addExtension("json").removeExtension("swf");
        builder.setCacheExtensionConfig(extension);
        builder.setAssetsDir("static");
        builder.isAssetsSuffixMod(true);
        builder.setDebug(true);
        builder.setResourceInterceptor(new ResourceInterceptor() {
            @Override
            public boolean interceptor(String url) {
                return true;
            }
        });

        WebViewCacheInterceptorInst.getInstance().
                init(builder);
    }


}
