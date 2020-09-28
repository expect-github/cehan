package com.nj.baijiayun.module_public.p_set.helper;

import android.text.TextUtils;

/**
 * @author chengang
 * @date 2020-03-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.p_set.helper
 * @describe
 */
public class NameValidate {
    public static boolean isLegalName(String name) {
        if (TextUtils.isEmpty(name) || name.length() < 2) {
            return false;
        }
        if (name.contains("·") || name.contains("•")) {
            return name.matches("^[\\u4e00-\\u9fa5]+[·•][\\u4e00-\\u9fa5]+$");
        } else {
            return name.matches("^[\\u4e00-\\u9fa5]+$");
        }
    }

}
