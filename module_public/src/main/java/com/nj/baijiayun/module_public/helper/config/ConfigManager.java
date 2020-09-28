package com.nj.baijiayun.module_public.helper.config;

import android.annotation.SuppressLint;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.nj.baijiayun.basic.utils.SharedPrefsUtil;
import com.nj.baijiayun.lib_http.retrofit.NetMgr;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_public.BaseApp;
import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.AppConfigBean;
import com.nj.baijiayun.module_public.bean.PublicOauthBean;
import com.nj.baijiayun.module_public.bean.PublicOtherSettingBean;
import com.nj.baijiayun.module_public.bean.PublicShareAvaiableBean;
import com.nj.baijiayun.module_public.helper.config.tasks.AppConfigTask;
import com.nj.baijiayun.module_public.helper.config.tasks.AppMessageNoticeTask;
import com.nj.baijiayun.module_public.helper.config.tasks.BaseConfigTask;
import com.nj.baijiayun.module_public.helper.config.tasks.OauthTask;
import com.nj.baijiayun.module_public.helper.config.tasks.OtherSettingTask;
import com.nj.baijiayun.module_public.helper.config.tasks.PersonServiceTask;
import com.nj.baijiayun.module_public.helper.config.tasks.ShareTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author chengang
 * @date 2020-02-05
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.config
 * @describe
 */
public class ConfigManager implements IConfig {
    private static volatile ConfigManager singleton = null;
    //文件名称
    private static final String SIMPLE_CONFIG = "app_config";
    /**
     * 以下为key
     */
    private static final String APP_CONFIG_KEY = "config_key";
    private static final String APP_REMINDER_KEY = "reminder_key";
    private static final String APP_OAUTH_KEY = "oauth_key";
    private static final String APP_SHARE_KEY = "share_key";
    private static final String APP_OTHER_KEY = "other_key";
    private static final String APP_SHORTCUT_BADGER = "APP_SHORTCUT_BADGER";
    private static final String APP_PERSON_SERVICE = "APP_PERSON_SERVICE";
    /**
     * 请求间隔 每隔一个小时 请求一次
     */
    private static final int INTERVAL_SUCCESS = 1000 * 60 * 60;
    private static final int MSG_CONFIG = 1;
    private List<BaseConfigTask> configTasks = new ArrayList<>();
    private HashMap<String, Object> configBeanMap = new HashMap<>();
    private PublicService publicService;


    private ConfigManager() {
        //AppConfig 在启动页已经加载了
//        configTasks.add(new AppConfigTask());
        configTasks.add(new OauthTask());
        configTasks.add(new ShareTask());
        configTasks.add(new OtherSettingTask());
        configTasks.add(new AppMessageNoticeTask());
        configTasks.add(new PersonServiceTask());
    }

    public static ConfigManager getInstance() {
        if (singleton == null) {
            synchronized (ConfigManager.class) {
                if (singleton == null) {
                    singleton = new ConfigManager();
                }
            }
        }
        return singleton;
    }

    private static long lastReqTime;

    public static boolean isFastReq() {
        long time = System.currentTimeMillis();
        long timeD = time - lastReqTime;
        if (0 < timeD && timeD < 5000) {
            return true;
        }
        lastReqTime = time;
        return false;
    }

    public void initConfig() {
        if (isFastReq()) {
            return;
        }
        handler.removeMessages(MSG_CONFIG);
        handler.sendEmptyMessageDelayed(MSG_CONFIG, 0);
    }

    @SuppressLint("HandlerLeak")
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CONFIG) {
                if (configTasks != null) {
                    for (int i = 0; i < configTasks.size(); i++) {
                        configTasks.get(i).start();
                    }
                }
                //待优化
                ConfigLoaderManager.get().getSystemConfig();
                handler.removeMessages(MSG_CONFIG);
                handler.sendEmptyMessageDelayed(MSG_CONFIG, INTERVAL_SUCCESS);
            }
        }
    };

    public <T> T getConfig(Class<T> cls, String saveKey) {
        String value = SharedPrefsUtil.getValue(BaseApp.getInstance(), SIMPLE_CONFIG, saveKey, "");
        T result = null;
        try {
            result = GsonHelper.getGsonInstance().fromJson(value, cls);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (result != null) {
            configBeanMap.put(saveKey, result);
        }
        return result;
    }

    public void saveConfig(String key, Object o) {
        SharedPrefsUtil.putValue(BaseApp.getInstance(), SIMPLE_CONFIG, key, GsonHelper.getGsonInstance().toJson(o));
        configBeanMap.put(key, o);

    }


    @Override
    public PublicService getService() {
        if (publicService == null) {
            publicService = NetMgr.getInstance()
                    .getDefaultRetrofit()
                    .create(PublicService.class);
        }
        return publicService;
    }


    @Override
    public void saveAppCommonConfig(AppConfigBean appConfigBean) {
        saveConfig(APP_CONFIG_KEY, appConfigBean);
    }

    @Override
    public AppConfigBean getAppConfig() {
        if (configBeanMap.get(APP_CONFIG_KEY) != null) {
            return (AppConfigBean) configBeanMap.get(APP_CONFIG_KEY);
        }
        AppConfigBean config = getConfig(AppConfigBean.class, APP_CONFIG_KEY);
        if (config == null) {
            config = new AppConfigBean();
            configBeanMap.put(APP_CONFIG_KEY, config);
        }

        return (AppConfigBean) configBeanMap.get(APP_CONFIG_KEY);
    }

    @Override
    public boolean isHasAppConfig() {
        return !TextUtils.isEmpty(SharedPrefsUtil.getValue(BaseApp.getInstance(), SIMPLE_CONFIG, APP_CONFIG_KEY, ""));
    }

    @Override
    public <T extends AppConfigBean> void loadAppConfig(ConfigLoadCallBack<T> configLoadCallBack) {
        AppConfigTask appConfigTask = new AppConfigTask();
        appConfigTask.setConfigLoadCallBack(configLoadCallBack);
        appConfigTask.start();
    }


    @Override
    public void saveOauthConfig(PublicOauthBean publicOauthBean) {
        saveConfig(APP_OAUTH_KEY, publicOauthBean);
    }

    @Override
    public boolean isAvailableLoginByQq() {
        PublicOauthBean config = getConfig(PublicOauthBean.class, APP_OAUTH_KEY);
        return config != null && config.getQqLogin() != null && config.getQqLogin().isAvailable();
    }

    @Override
    public boolean isAvailableLoginByWeChat() {
        PublicOauthBean config = getConfig(PublicOauthBean.class, APP_OAUTH_KEY);
        return config != null && config.getWxLogin() != null && config.getWxLogin().isAvailable();

    }

    @Override
    public boolean isAvailableShareByQq() {
        PublicShareAvaiableBean config = getConfig(PublicShareAvaiableBean.class, APP_SHARE_KEY);
        return config != null && config.isQqShareAvailable();
    }

    @Override
    public boolean isAvailableShareByWeChat() {
        PublicShareAvaiableBean config = getConfig(PublicShareAvaiableBean.class, APP_SHARE_KEY);
        return config != null && config.isWeChatShareAvailable();
    }

    @Override
    public boolean isAvailableTemplateShare() {
        PublicShareAvaiableBean config = getConfig(PublicShareAvaiableBean.class, APP_SHARE_KEY);
        return config != null && config.isTemplateAvailable();
    }

    @Override
    public boolean isShowShare() {
        return isAvailableShareByQq() || isAvailableShareByWeChat();
    }

    @Override
    public boolean needShowReminder() {
        return !SharedPrefsUtil.getValue(BaseApp.getInstance(), SIMPLE_CONFIG, APP_REMINDER_KEY, false);
    }

    @Override
    public void showReminder() {
        SharedPrefsUtil.putValue(BaseApp.getInstance(), SIMPLE_CONFIG, APP_REMINDER_KEY, true);
    }

    @Override
    public void saveShareConfig(PublicShareAvaiableBean publicShareAvaiableBean) {
        saveConfig(APP_SHARE_KEY, publicShareAvaiableBean);
    }

    @Override
    public void loadBaseConfig() {
    }

    @Override
    public void saveOtherSettingConfig(PublicOtherSettingBean bean) {
        saveConfig(APP_OTHER_KEY, bean);
    }

    @Override
    public PublicOtherSettingBean getOtherSetting() {
        PublicOtherSettingBean config = getConfig(PublicOtherSettingBean.class, APP_OTHER_KEY);
        if (config == null) {
            return new PublicOtherSettingBean();
        }
        return config;
    }

    @Override
    public PublicOauthBean getOauthBean() {
        return getConfig(PublicOauthBean.class, APP_OAUTH_KEY);
    }

    @Override
    public void saveShortcutBadgeStatus(boolean status) {
        SharedPrefsUtil.putValue(BaseApp.getInstance(), SIMPLE_CONFIG, APP_SHORTCUT_BADGER, status);
    }

    @Override
    public boolean isOpenShortcutBadge() {
        return SharedPrefsUtil.getValue(BaseApp.getInstance(), SIMPLE_CONFIG, APP_SHORTCUT_BADGER, false);
    }

    @Override
    public void savePersonServiceLink(String url) {
        SharedPrefsUtil.putValue(BaseApp.getInstance(), SIMPLE_CONFIG, APP_PERSON_SERVICE, url);

    }

    @Override
    public String getPersonServiceLink() {
        return SharedPrefsUtil.getValue(BaseApp.getInstance(), SIMPLE_CONFIG, APP_PERSON_SERVICE, "");
    }

    @Override
    public boolean isShowPersonService() {
        String personServiceLink = getPersonServiceLink();
        return !TextUtils.isEmpty(personServiceLink)&&personServiceLink.startsWith("http");
    }


}
