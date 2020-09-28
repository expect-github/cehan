package com.nj.baijiayun.module_main.helper;

/**
 * @author chengang
 * @date 2019-09-17
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.helper
 * @describe
 */
public class IntervalHelper {
    private static long lastTime;

    public static boolean isNotAllowStart(long interval) {
        long time = System.currentTimeMillis();
        long timeD = time - lastTime;
        if (0 < timeD && timeD < interval) {
            return true;
        }
        lastTime = time;
        return false;
    }
}
