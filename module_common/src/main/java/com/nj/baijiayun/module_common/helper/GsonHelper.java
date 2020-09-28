package com.nj.baijiayun.module_common.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

/**
 * @author chengang
 * @date 2019-08-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.helper
 * @describe
 */
public class GsonHelper {


    private static class GsonHolder{
        private static final Gson INSTANCE =  new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .create();

    }

    /**
     * 获取Gson实例，由于Gson是线程安全的，这里共同使用同一个Gson实例
     */
    public static Gson getGsonInstance()
    {
        return GsonHolder.INSTANCE;
    }


}
