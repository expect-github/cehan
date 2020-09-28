package com.nj.baijiayun.module_public.helper.config;

/**
 * @author chengang
 * @date 2020-02-17
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.config
 * @describe
 */
public interface ConfigLoadCallBack<T> {
    void preLoad();

    void loadSuccess(T bean);

    void loadFail(Exception e);
}
