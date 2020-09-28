package com.nj.baijiayun.module_public.helper.config;

import com.nj.baijiayun.basic.utils.SharedPrefsUtil;
import com.nj.baijiayun.module_public.BaseApp;

/**
 * @author chengang
 * @date 2020-03-11
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.config
 * @describe
 */
public class SpManger {
    private static final String DEFAULT_NAME = "simple_config";
    private static final String KEY_PROTECT_EYES = "protect_eyes";

    public static boolean isOpenProtectEyes() {
        return SharedPrefsUtil.getValue(BaseApp.getInstance(), DEFAULT_NAME, KEY_PROTECT_EYES, false);
    }

    public static void setProtectEyes(boolean state) {
        SharedPrefsUtil.putValue(BaseApp.getInstance(), DEFAULT_NAME, KEY_PROTECT_EYES, state);
    }


}
