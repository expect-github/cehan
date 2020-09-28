package com.nj.baijiayun.module_public.temple.js_manager;

import com.nj.baijiayun.basic.manager.AppManager;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_public.bean.PublicOauthBean;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;
import com.nj.baijiayun.module_public.p_base.dialog.ShowQrCodeDialog;

/**
 * @author chengang
 * @date 2020-03-11
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.js_manager
 * @describe
 */
public class JsActionHelper {
    public static void tryShowWeChatOfficialAccount() {
        PublicOauthBean oauthBean = ConfigManager.getInstance().getOauthBean();
        Logger.d("oauthBean" + oauthBean);
        Logger.d("oauthBean" + oauthBean.getMmpLogin());
        Logger.d("oauthBean" + (oauthBean.getMmpLogin() != null ? String.valueOf(oauthBean.getMmpLogin().isSwitch()) : ""));
        if (oauthBean != null && oauthBean.getMmpLogin() != null && oauthBean.getMmpLogin().isSwitch()) {
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new ShowQrCodeDialog(AppManager.getAppManager().currentActivity(), oauthBean.getMmpLogin().getMpPoster()).show();

                }
            },200);
        }

    }
}
