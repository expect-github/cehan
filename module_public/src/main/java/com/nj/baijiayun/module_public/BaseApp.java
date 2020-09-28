package com.nj.baijiayun.module_public;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.nj.baijiayun.basic.utils.AppUtils;
import com.nj.baijiayun.basic.utils.ApplicationUtils;
import com.nj.baijiayun.imageloader.loader.ImageLoader;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.helper.AppLifecycleHandler;
import com.nj.baijiayun.module_public.bean.UserInfoBean;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.helper.AppStartHelper;
import com.nj.baijiayun.module_public.helper.ObjectToObservableHelper;
import com.nj.baijiayun.module_public.provider.FileUploadProvider;
import com.nj.baijiayun.module_public.provider.NetWorkProvider;
import com.nj.baijiayun.refresh.SmartRefreshLayout;
import com.nj.baijiayun.refresh.constant.SpinnerStyle;
import com.nj.baijiayun.refresh.footer.BallPulseFooter;
import com.nj.baijiayun.refresh.header.WaterDropHeader;
import com.nj.baijiayun.refresh.smartrv.NxRefreshConfig;
import com.nj.baijiayun.refresh.smartrv.strategy.DefaultExtra;
import com.tencent.smtt.sdk.QbSdk;
import com.yuyh.library.imgsel.ISNav;

import java.util.concurrent.TimeUnit;

import androidx.multidex.MultiDex;
import dagger.android.DaggerApplication;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * @author chengang
 * @date 2019-06-05
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common
 * @describe
 */
public abstract class BaseApp extends DaggerApplication {

    private static boolean mIsDebug;
    private static BaseApp mApplication;

    /**
     * 单一实例
     *
     * @return BaseApp
     */
    public static BaseApp getInstance() {
        return mApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        String curProcessName = ApplicationUtils.getCurProcessName(getApplicationContext());
        if (!getApplicationInfo().packageName.equals(curProcessName)) {
            return;
        }
        initSdk();

        AppStartHelper.setAppStartTime();

    }


    @SuppressLint("CheckResult")
    public void initSdk() {
        initOther();
        initLogger();
        initNetWork();
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .subscribe(integer -> initX5());
        initImageLoader();
        initARouter();
        initRefresh();
        initDelaytask();
    }

    private int count = 0;
    private static final int MAX_RETRY_COUNT = 5;
    QbSdk.PreInitCallback cb;

    private void initX5() {
        if (cb == null) {
            cb = new QbSdk.PreInitCallback() {

                @SuppressLint("CheckResult")
                @Override
                public void onViewInitFinished(boolean arg0) {
                    Log.d("onViewInitFinished", " onViewInitFinished onViewInitFinished "+arg0);

                    count++;
                    // TODO Auto-generated method stub
                    //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                    if (!arg0 && count <= MAX_RETRY_COUNT) {
                        Observable.just(1).delay(2, TimeUnit.SECONDS)
                                .subscribe(new Consumer<Integer>() {
                                    @Override
                                    public void accept(Integer integer) throws Exception {
                                        initX5();
                                    }
                                });
                    }
                }

                @Override
                public void onCoreInitFinished() {
                    Log.d("onViewInitFinished", " onCoreInitFinished onViewInitFinished ");

                    // TODO Auto-generated method stub

                }
            };
        }
        Log.d("onViewInitFinished", " onViewInitFinished start ");
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void initDelaytask() {
        new android.os.Handler().postDelayed(() -> initImageGallery(), 3000);
    }


    protected void initNetWork() {
        NetMgr.getInstance().registerProvider(new NetWorkProvider(isDebug()));
        //这里注册的upload  内部是baseUrl+upload作为key value为 FileUploadProvider，在取出retrofit为getretrofit("upload")
        NetMgr.getInstance().registerProviderByDefaultBaseUrl(NetMgr.UPLOAD_DEFAULT_KEY, new FileUploadProvider(isDebug()));

    }

    private void initImageGallery() {
        ISNav.getInstance().init((com.yuyh.library.imgsel.common.ImageLoader) (context, path, imageView) -> Glide.with(context).load(path).into(imageView));

    }

    private void initOther() {
        RxJavaPlugins.setErrorHandler(throwable -> Logger.e("RxJava catch global exception", throwable));
        AppUtils.init(this);
        registerActivityLifecycleCallbacks(AppLifecycleHandler.create());
        mApplication = this;
        ObjectToObservableHelper.init(UserInfoBean.class, AccountHelper.getInstance().getInfo());

    }

    private void initImageLoader() {
        ImageLoader.init(this);
    }


    private void initRefresh() {
        NxRefreshConfig.init(new DefaultExtra() {
            @Override
            public void setExtra(View refreshLayoutView) {
                if (refreshLayoutView instanceof SmartRefreshLayout) {
                    ((SmartRefreshLayout) (refreshLayoutView)).setRefreshHeader(new WaterDropHeader(refreshLayoutView.getContext()));
                    ((SmartRefreshLayout) (refreshLayoutView)).setRefreshFooter(new BallPulseFooter(refreshLayoutView.getContext()).setSpinnerStyle(SpinnerStyle.Scale));
                    ((SmartRefreshLayout) (refreshLayoutView)).setEnableFooterTranslationContent(true);
                    ((SmartRefreshLayout) (refreshLayoutView)).setEnableHeaderTranslationContent(true);
                    ((SmartRefreshLayout) (refreshLayoutView)).setEnableRefresh(false);
                    ((SmartRefreshLayout) (refreshLayoutView)).setEnableLoadMore(false);
                    ((SmartRefreshLayout) (refreshLayoutView)).setPrimaryColorsId(R.color.common_main_color, android.R.color.white);
                }
            }
        });
    }


    private void initARouter() {
        if (isDebug()) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }

    private void initLogger() {
        Logger.setEnable(isDebug());
        Logger.setPriority(Logger.MIN_LOG_PRIORITY);
        Logger.setTag("[zywxApp]");
        Logger.init(this);
    }


    public Context getAppContext() {
        return mApplication;
    }

    public boolean isDebug() {
        Log.d("DEBUGINFO","mIsDebug===="+mIsDebug);
        return mIsDebug;
    }

    public void setDebug(boolean isDebug) {
        mIsDebug = isDebug;
        Log.d("DEBUGINFO","mIsDebugsetDebug"+mIsDebug);

    }


    protected boolean isMainCurrentProcessName()
    {
        return getApplicationContext().getPackageName().equals(ApplicationUtils.getCurProcessName(getApplicationContext()));
    }
}
