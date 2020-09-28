package com.nj.baijiayun.module_public.helper;

/**
 * @author chengang
 * @date 2019-10-11
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class AppStartHelper {
    private static long appStartTime;

    public static void setAppStartTime()
    {
        appStartTime=System.currentTimeMillis();
    }

    public static boolean isFirstStart() {
        long time = System.currentTimeMillis();
        long timeD = time - appStartTime;
        if (0 < timeD && timeD < 2*1000) {
            return true;
        }
        appStartTime = time;
        return false;
    }

}
