package com.nj.baijiayun.module_public.helper;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.nj.baijiayun.basic.utils.SharedPrefsUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.base.BaseSimpleObserver;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.AppConfigBean;
import com.nj.baijiayun.module_public.bean.PublicOauthBean;
import com.nj.baijiayun.module_public.bean.response.AppConfigResponse;

import io.reactivex.schedulers.Schedulers;

/**
 * @author chengang
 * @date 2019-09-09
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe ConfigManager
 */
@Deprecated
public class AppConfigManager {
    private AppConfigBean configBean;
    private AppConfigBean defalutConfigBean = new AppConfigBean();
    //文件名称
    private static final String SIMPLE_CONFIG = "app_config";
    /**
     * 以下为key
     */
    private static final String APP_CONFIG_KEY = "key";
    private static final String APP_REMINDER_KEY = "reminder_key";
    private static final String APP_OAUTH_KEY = "oauth_key";
    private static final String APP_SHARE_KEY = "share_key";

    /**
     * 请求间隔 每隔一个小时 请求一次
     */
    private static final int INTERVAL_SUCCESS = 1000 * 60 * 60;
    private static final int MSG_CONFIG = 1;

    @SuppressLint("HandlerLeak")
    private android.os.Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CONFIG) {
                getAppConfigFormServer();
                handler.sendEmptyMessageDelayed(MSG_CONFIG, INTERVAL_SUCCESS);
            }
        }
    };


    public static AppConfigManager getInstance() {
        return AppConfigManager.AppConfigManagerCreate.instance;
    }

    private static class AppConfigManagerCreate {
        static AppConfigManager instance = new AppConfigManager();
    }

    private static void saveAppConfig(AppConfigBean appConfigBean) {
        SharedPrefsUtil.putValue(BaseApp.getInstance(), SIMPLE_CONFIG, APP_CONFIG_KEY, GsonHelper.getGsonInstance().toJson(appConfigBean));

    }

    public static void showReminder() {
        SharedPrefsUtil.putValue(BaseApp.getInstance(), SIMPLE_CONFIG, APP_REMINDER_KEY, true);
    }

    public static boolean needShowReminder() {
        return !SharedPrefsUtil.getValue(BaseApp.getInstance(), SIMPLE_CONFIG, APP_REMINDER_KEY, false);
    }


    public static AppConfigBean getAppConfig() {
        return getInstance().getConfig();
    }

    private AppConfigBean getConfig() {
        if (configBean != null) {
            return configBean;
        }
        String value = SharedPrefsUtil.getValue(BaseApp.getInstance(), SIMPLE_CONFIG, APP_CONFIG_KEY, "");
        AppConfigBean appConfigBean = null;
        try {
            appConfigBean = GsonHelper.getGsonInstance().fromJson(value, AppConfigBean.class);
            if (appConfigBean != null) {
                configBean = appConfigBean;
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        if (appConfigBean == null) {
            return defalutConfigBean;
        }
        return appConfigBean;
    }

    public static void saveOauthConfig(PublicOauthBean publicOauthBean) {
        saveConfig(APP_OAUTH_KEY, publicOauthBean);
    }

    private static void saveConfig(String key, Object o) {
        SharedPrefsUtil.putValue(BaseApp.getInstance(), SIMPLE_CONFIG, key, GsonHelper.getGsonInstance().toJson(o));

    }

    private static <T> T getConfig(Class<T> cls, String saveKey) {
        String value = SharedPrefsUtil.getValue(BaseApp.getInstance(), SIMPLE_CONFIG, saveKey, "");
        return GsonHelper.getGsonInstance().fromJson(value, cls);
    }


    public void loadAppConfig() {
        handler.sendEmptyMessageDelayed(MSG_CONFIG, 0);
    }


    private void getAppConfigFormServer() {
        NetMgr.getInstance()
                .getDefaultRetrofit()
                .create(PublicService.class)
                .getAppConfig()
                .retryWhen(new RetryDefaultWithDelay())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSimpleObserver<AppConfigResponse>() {
                    @Override
                    public void onPreRequest() {

                    }

                    @Override
                    public void onNext(AppConfigResponse appConfigResponse) {
                        if (appConfigResponse.isSuccess()) {
                            onSuccess(appConfigResponse);
                        }
                    }

                    @Override
                    public void onSuccess(AppConfigResponse appConfigResponse) {
                        saveAppConfig(appConfigResponse.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                    }
                });

    }


}
