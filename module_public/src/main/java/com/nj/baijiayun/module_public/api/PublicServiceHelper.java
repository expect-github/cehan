package com.nj.baijiayun.module_public.api;

import com.nj.baijiayun.lib_http.retrofit.NetMgr;

/**
 * @author chengang
 * @date 2020-03-31
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.api
 * @describe
 */
public class PublicServiceHelper {

    private static volatile PublicService publicService = null;
    private static final Object OBJECT =new Object();

    public static PublicService getService(){
        if (publicService == null){
            synchronized(OBJECT){
                if(publicService == null){
                    publicService = NetMgr.getInstance().getDefaultRetrofit().create(PublicService.class);
                }
            }
        }
        return publicService;
    }
}
