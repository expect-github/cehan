package com.nj.baijiayun.module_public.helper.config;

import com.nj.baijiayun.module_public.bean.SystemWebConfigBean;

/**
 * @author chengang
 * @date 2018/10/25
 * @describe
 */
public abstract class AbstractConfigLoaderCreate {
    public abstract AbstractConfigLoader<SystemWebConfigBean> getSystemConfigLoader();


}
