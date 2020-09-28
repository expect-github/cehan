package com.nj.baijiayun.module_common.helper;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * @author chengang
 * @date 2019-11-29
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class SoftKeyInputHelper {
   public static void hideSoftInput(Context context)
   {
       InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
       if (imm == null) {
           return;
       }
       //如果window上view获取焦点 && view不为空
       if (imm.isActive() && ((Activity) context).getCurrentFocus() != null) {
           //拿到view的token 不为空
           if (((Activity) context).getCurrentFocus().getWindowToken() != null) {
               //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
               imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
           }
       }
   }
}

