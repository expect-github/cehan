package com.nj.baijiayun;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.baijiayun.BJYPlayerSDK;
import com.baijiayun.lib_compiler.GenerateEntry;
import com.baijiayun.lib_compiler.GeneratePay;
import com.baijiayun.lib_push.PushHelper;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.lzf.easyfloat.EasyFloat;
import com.nj.baijiayun.basic.manager.AppManager;
import com.nj.baijiayun.basic.network.NetType;
import com.nj.baijiayun.basic.network.NetWork;
import com.nj.baijiayun.basic.network.NetworkManager;
import com.nj.baijiayun.basic.network.NetworkUtils;
import com.nj.baijiayun.downloader.DownloadManager;
import com.nj.baijiayun.downloader.config.DownConfig;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_common.helper.MaskViewHelper;
import com.nj.baijiayun.module_main.MainActivity;
import com.nj.baijiayun.module_main.helper.HomeTabPageHelper;
import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.bean.UserInfoBean;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.helper.ObjectToObservableHelper;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;
import com.nj.baijiayun.module_public.helper.config.SpManger;
import com.nj.baijiayun.module_public.helper.push.PushRouterHelper;
import com.nj.baijiayun.module_public.helper.videoplay.VideoProxyActivity;
import com.nj.baijiayun.module_public.manager.LifecycleHandler;
import com.nj.baijiayun.module_public.manager.ShortcutBadgerManager;
import com.nj.baijiayun.module_public.manager.UploadRecordHandler;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.io.File;
import java.util.concurrent.TimeUnit;

import androidx.core.content.ContextCompat;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ren.yale.android.cachewebviewlib.WebViewCacheInterceptor;
import ren.yale.android.cachewebviewlib.WebViewCacheInterceptorInst;
import ren.yale.android.cachewebviewlib.config.CacheExtensionConfig;

/**
 * @author chengang
 * @date 2019-06-10
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.neixun
 * @describe
 */
@GenerateEntry(appId = BuildConfig.APPLICATION_ID)
@GeneratePay(appId = BuildConfig.APPLICATION_ID, needGenerate = true)
public class BjyApp extends BaseApp {


    @Override
    public void initSdk() {
        DoraemonKit.install(this);
        super.initSdk();
        initCacheWebview();
        initDownloadManager();
        EasyFloat.init(this, isDebug());
        delayInit();
        MaskViewHelper.getInstance()
                .initShowMask(SpManger.isOpenProtectEyes())
                .setMaskViewColor(ContextCompat.getColor(this, R.color.mask_protect_eyes)).initApplicationLife(this);
        ShortcutBadgerManager.getInstance().setEnable(ConfigManager.getInstance().isOpenShortcutBadge());
        registerActivityLifecycleCallbacks(LifecycleHandler.create().setApplicationRunCallback(new LifecycleHandler.Callback() {
            @Override
            public void inForeground() {

            }

            @Override
            public void inBackgound() {
                ShortcutBadgerManager.getInstance().tryUpdateNumber();
            }
        }));
        registerActivityLifecycleCallbacks(new UploadRecordHandler());
        initBugly();
    }

    private void initBugly() {
        CrashReport.initCrashReport(this,"788cdab699" , true);
    }


    private void initCacheWebview() {
        Log.d("TAG", "WebViewCacheInterceptor initCacheWebview");
        WebViewCacheInterceptor.Builder builder = new WebViewCacheInterceptor.Builder(this);
        builder.setCachePath(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "cache_path_name"))//设置缓存路径，默认getCacheDir，名称CacheWebViewCache
                .setCacheSize(1024 * 1024 * 100)//设置缓存大小，默认100M
                .setConnectTimeoutSecond(20)//设置http请求链接超时，默认20秒
                .setReadTimeoutSecond(20);//设置http请求链接读取超时，默认20秒
        CacheExtensionConfig extension = new CacheExtensionConfig();
        extension.addExtension("json").removeExtension("swf");
        builder.setCacheExtensionConfig(extension);
        builder.setAssetsDir("static");
        //builder.isAssetsSuffixMod(true);
        builder.setDebug(BuildConfig.DEBUG);

        builder.setResourceInterceptor(url -> true);

        WebViewCacheInterceptorInst.getInstance().
                init(builder);
//        WebViewPool.getInstance().init(BjyApp.this, BuildConfig.BASE_H5_URL);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        initUmengSdkDelay();

    }

    @SuppressLint("CheckResult")
    private void delayInit() {
        Observable.just(1)
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {
                    VideoProxyActivity.init(BuildConfig.BYJ_DOMAIN);
                    new BJYPlayerSDK.Builder(this)
                            //如果没有个性域名请注释
                            .setCustomDomain(BuildConfig.BYJ_DOMAIN)
                            .setEncrypt(true)
                            .build();
                    initGsonErrorListener();

                });

    }

    private void initUmengSdkDelay() {
        if (isMainCurrentProcessName()) {
            initUmengSdk();
        } else {
            initUmengSdk();
        }

    }

    public String getVideoDownLoadPath() {
        String mCacheDirPath = null;
        File cacheDir = getExternalFilesDir("FileDownload");
        if (null != cacheDir) {
            mCacheDirPath = cacheDir.getAbsolutePath();
        }
        if (TextUtils.isEmpty(mCacheDirPath)) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                mCacheDirPath = Environment.getExternalStorageDirectory().getPath() + "/Android/data/" + BuildConfig.APPLICATION_ID + "/files/";
            }
        }
        return mCacheDirPath;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    private void initDownloadManager() {
        DownConfig.Builder builder = new DownConfig
                .Builder(this);
        if (AccountHelper.getInstance().isLogin()) {
            builder.setUid(String.valueOf(AccountHelper.getInstance().getInfo().getId()));
        } else {
            builder.setUid("-1");
        }

        builder.setVideoCustomDomain(BuildConfig.BYJ_DOMAIN);
        DownConfig downConfig = builder.setFilePath(getVideoDownLoadPath()).builder();

        DownloadManager.init(downConfig);
        ObjectToObservableHelper.getInstance(UserInfoBean.class).subscribe(this,
                userInfoBeanObservableWrapper -> {
                    if (userInfoBeanObservableWrapper.getContent() != null) {
                        DownloadManager.updateUid(String.valueOf(userInfoBeanObservableWrapper.getContent().getId()));
                    }
                }, "id");
    }

    private void initUmengSdk() {
        PushHelper.getInstance().initJGShare(this, true);
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        PushHelper.getInstance().initUMengAnalytics(this, BuildConfig.DEBUG);
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDisplayNotificationNumber(10);
        mPushAgent.setResourcePackageName(BuildConfig.PACKAGE_RES_NAME);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Logger.i("umengSdk 注册成功：deviceToken：-------->  " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Logger.i("umengSdk 注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });

        mPushAgent.setMessageHandler(new UmengMessageHandler() {
            @Override
            public void handleMessage(Context context, UMessage uMessage) {
                Logger.i("umengSdk  handleMessage");
                ShortcutBadgerManager.getInstance().updateNumberFromDataSource();
                super.handleMessage(context, uMessage);
            }
        });
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage uMessage) {
                tryStartMian(context);
                PushRouterHelper.handlerPushExtraData(uMessage.extra);
                Logger.i("umengSdk  notificationClickHandler： dealWithCustomAction-------->  " + uMessage.custom);
            }

            @Override
            public void openUrl(Context context, UMessage uMessage) {
                super.openUrl(context, uMessage);
                Logger.i("umengSdk  notificationClickHandler：--------> openUrl ");

            }

            @Override
            public void launchApp(Context context, UMessage uMessage) {
                tryStartMian(context);
                PushRouterHelper.handlerPushExtraData(uMessage.extra);
                Logger.i("umengSdk  notificationClickHandler：--------> launchApp " + GsonHelper.getGsonInstance().toJson(uMessage.extra));

            }

            @Override
            public void openActivity(Context context, UMessage uMessage) {
                super.openActivity(context, uMessage);
                Logger.i("umengSdk  notificationClickHandler：-------->  openActivity");
            }

        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

    }

    private void tryStartMian(Context context) {
        if (!AppManager.getAppManager().isExist(MainActivity.class)) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void initNetWork() {
        ConstsH5Url.setBaseH5Url(BuildConfig.BASE_H5_URL);
        ConstsH5Url.setBaseApiUrl(BuildConfig.BASE_URL);
        super.initNetWork();
        HomeTabPageHelper.preLoadHomeBottomNav();
        NetworkManager.getInstance().init(this);
        if (NetworkUtils.isNetworkAvailable(this)) {
            ConfigManager.getInstance().initConfig();
        }
        NetworkManager.getInstance().registerObserver(this);

    }


    @NetWork(netType = NetType.AUTO)
    public void onNetChanged(NetType netType) {
        if (netType != NetType.NONE && NetworkUtils.isNetworkAvailable(this)) {
            ConfigManager.getInstance().initConfig();
        }
    }


    private void initGsonErrorListener() {
        GsonSyntaxErrorListener.start();
    }

    //这个debug 仅仅用于logger相关的
    @Override
    public boolean isDebug() {
        return BuildConfig.LOG_DEBUG;
    }


}

