package com.nj.baijiayun.module_public.helper;

import com.nj.baijiayun.downloader.utils.MD5Util;
import com.nj.baijiayun.module_public.consts.ConstsIntegral;

/**
 * @author chengang
 * @date 2019-12-20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe
 */
public class IntegralHelper {

    public static void getIntegral(int eventType) {
        getIntegral(eventType, "");
    }
    public static void getShareIntegral(String shareUrl) {
        getIntegral(ConstsIntegral.INTEGRAL_SHARE, MD5Util.encrypt(shareUrl));
    }
    public static void getCollectIntegral(int id)
    {
        getIntegral(ConstsIntegral.INTEGRAL_COLLECT, String.valueOf(id));

    }

    public static void getIntegral(int eventType, String eventMark) {
//        NetMgr.getInstance()
//                .getDefaultRetrofit()
//                .create(PublicService.class)
//                .getIntegral(eventType, eventMark)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe();
    }
}
