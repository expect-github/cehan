package com.nj.baijiayun;

import com.ke.gson.sdk.ReaderTools;
import com.nj.baijiayun.logger.log.Logger;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

//import com.umeng.analytics.MobclickAgent;

/**
 * @author chengang
 * @date 2019-06-19
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper
 * @describe 监听gson 解析错误的 请不要删除
 */
public class GsonSyntaxErrorListener {
    private static HashMap<String,String>map=new HashMap<>(2);
    public static void start() {
        ReaderTools.setListener(mListener);
    }

    private static ReaderTools.JsonSyntaxErrorListener mListener = (exception, invokeStack) -> {
        map.clear();
        map.put("exception",exception);
        map.put("invokeStack",invokeStack);

        MobclickAgent.reportError(BjyApp.getInstance(),invokeStack);
//        MobclickAgent.onEvent(BaseApp.getInstance(),"JsonSyntaxError",map);
        Logger.d("onJsonSyntaxError  exception-->"+exception);
        Logger.d("onJsonSyntaxError  invokeStack-->"+invokeStack);
    };
}
