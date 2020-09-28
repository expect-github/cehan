package com.nj.baijiayun.module_public.helper.config;

/**
 * @author chengang
 * @date 2018/10/25
 * @describe
 */
public abstract class AbstractConfigLoader<T extends BaseConfigBean> {
    private ConfigLoader<T> sLoader;

    public void updateConfig(T baseConfigBean, String json) {
        init();
        sLoader.updateConfig(baseConfigBean,json);
    }


    /**
     * 获取配置
     */
    public T get() {
        init();
        return sLoader.getConfig();
    }

    public ConfigLoader init() {
        if (sLoader == null) {
            sLoader = new ConfigLoader<>(createConfigBean(), getAssestJsonName());
            sLoader.loadConfig();
        }
        return sLoader;
    }

    public void clear() {
        sLoader = null;

    }

    protected abstract T createConfigBean();

    protected abstract String getAssestJsonName();
}
