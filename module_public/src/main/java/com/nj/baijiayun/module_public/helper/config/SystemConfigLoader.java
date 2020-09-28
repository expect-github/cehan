package com.nj.baijiayun.module_public.helper.config;

import com.nj.baijiayun.module_public.bean.SystemWebConfigBean;

/**
 * @author chengang
 * @date 2019-09-25
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.config
 * @describe
 */
public class SystemConfigLoader extends AbstractConfigLoader<SystemWebConfigBean> {
    @Override
    protected SystemWebConfigBean createConfigBean() {
        return new SystemWebConfigBean();
    }

    @Override
    protected String getAssestJsonName() {
        return "system_web_config.json";
    }
}
