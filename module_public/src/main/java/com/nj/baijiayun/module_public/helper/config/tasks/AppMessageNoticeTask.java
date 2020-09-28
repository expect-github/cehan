package com.nj.baijiayun.module_public.helper.config.tasks;

import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_public.manager.ShortcutBadgerManager;

/**
 * @author chengang
 * @date 2020-03-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.config.tasks
 * @describe
 */
public class AppMessageNoticeTask extends BaseConfigTask {
    @Override
    public void start() {
        loadConfig("app_message_notice");
    }

    @Override
    protected void loadDataSuccess(Object data) {
        try {

            Logger.d(  "loadDataSuccess----->"+ data);
//
            boolean notice = 1== Double.valueOf(String.valueOf(data)).intValue();
            Logger.d(  "loadDataSuccess----->"+ notice);

            //1 开启 2 关闭
            ShortcutBadgerManager.getInstance().switchEnable(notice);
        } catch (Exception e) {
            Logger.e(e.getMessage() + "----->");
        }
    }
}
