package com.nj.baijiayun.module_public.helper.config;

import com.nj.baijiayun.module_public.api.PublicService;
import com.nj.baijiayun.module_public.bean.AppConfigBean;
import com.nj.baijiayun.module_public.bean.PublicOauthBean;
import com.nj.baijiayun.module_public.bean.PublicOtherSettingBean;
import com.nj.baijiayun.module_public.bean.PublicShareAvaiableBean;

/**
 * @author chengang
 * @date 2020-02-05
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.config
 * @describe
 */
public interface IConfig {
    PublicService getService();

    void saveAppCommonConfig(AppConfigBean appConfigBean);

    AppConfigBean getAppConfig();

    boolean isHasAppConfig();

    <T extends AppConfigBean> void loadAppConfig(ConfigLoadCallBack<T> configLoadCallBack);

    void saveOauthConfig(PublicOauthBean publicOauthBean);

    boolean isAvailableLoginByQq();

    boolean isAvailableLoginByWeChat();

    boolean isAvailableShareByQq();

    boolean isAvailableShareByWeChat();

    boolean isAvailableTemplateShare();

    boolean isShowShare();

    boolean needShowReminder();

    void showReminder();

    void saveShareConfig(PublicShareAvaiableBean publicShareAvaiableBean);

    void loadBaseConfig();

    void saveOtherSettingConfig(PublicOtherSettingBean bean);

    PublicOtherSettingBean getOtherSetting();

    PublicOauthBean getOauthBean();

    void saveShortcutBadgeStatus(boolean status);

    boolean isOpenShortcutBadge();

    void savePersonServiceLink(String url);

    String getPersonServiceLink();

    boolean isShowPersonService();


}
