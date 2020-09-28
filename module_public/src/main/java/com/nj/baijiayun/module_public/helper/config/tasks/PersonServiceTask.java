package com.nj.baijiayun.module_public.helper.config.tasks;

import com.nj.baijiayun.module_public.helper.config.ConfigManager;

/**
 * @author chengang
 * @date 2020-03-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.config.tasks
 * @describe
 */
public class PersonServiceTask extends BaseConfigTask {
    @Override
    public void start() {
        loadConfig("customer_chat_link");
    }

    @Override
    protected void loadDataSuccess(Object data) {
        try {
            ConfigManager.getInstance().savePersonServiceLink(String.valueOf(data));
        } catch (Exception e) {
            ConfigManager.getInstance().savePersonServiceLink("");
        }
    }

}
