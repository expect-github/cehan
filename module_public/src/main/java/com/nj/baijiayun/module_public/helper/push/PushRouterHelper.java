package com.nj.baijiayun.module_public.helper.push;

import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_public.helper.push.handler.CommunicateRecordPushHandler;
import com.nj.baijiayun.module_public.helper.push.handler.DetailPushHandler;
import com.nj.baijiayun.module_public.helper.push.handler.LiveOpenPushHandler;
import com.nj.baijiayun.module_public.helper.push.handler.MyAppointCoursePushHandler;
import com.nj.baijiayun.module_public.helper.push.handler.MyMessageListPushHandler;
import com.nj.baijiayun.module_public.helper.push.handler.OpenHomePagePushHandler;
import com.nj.baijiayun.module_public.helper.push.handler.OpenWebUrlPushHandler;
import com.nj.baijiayun.module_public.helper.push.handler.OrderPushHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chengang
 * @date 2019-12-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.push
 * @describe
 */
public class PushRouterHelper {

    private static HashMap<String, Class<? extends IPushHandler>> stringClassHashMap = new HashMap<>();
    private static Map<String, IPushHandler> iPushHandlerMap = new HashMap<>();

    static {
        stringClassHashMap.put("live_open_course", LiveOpenPushHandler.class);
        stringClassHashMap.put("order_no_pay", OrderPushHandler.class);
        stringClassHashMap.put("user_cancel_course_order", MyMessageListPushHandler.class);
        stringClassHashMap.put("cancel_order", MyMessageListPushHandler.class);
        stringClassHashMap.put("course_refund_success", MyMessageListPushHandler.class);
        stringClassHashMap.put("course_refund_fail", MyMessageListPushHandler.class);
        stringClassHashMap.put("refund_fail", MyMessageListPushHandler.class);
        stringClassHashMap.put("refund_success", MyMessageListPushHandler.class);
        stringClassHashMap.put("system_message", MyMessageListPushHandler.class);
        stringClassHashMap.put("oto_pay_success", MyAppointCoursePushHandler.class);
        stringClassHashMap.put("oto_before_start",  MyAppointCoursePushHandler.class);
        stringClassHashMap.put("oto_connect_new_msg", CommunicateRecordPushHandler.class);
        stringClassHashMap.put("oto_open_next_week", MyAppointCoursePushHandler.class);
        stringClassHashMap.put("oto_order_refund_success", MyAppointCoursePushHandler.class);
        stringClassHashMap.put("oto_teacher_cancle", MyMessageListPushHandler.class);
        stringClassHashMap.put("open_web_url", OpenWebUrlPushHandler.class);
        stringClassHashMap.put("open_message_list", MyMessageListPushHandler.class);
        stringClassHashMap.put("open_index", OpenHomePagePushHandler.class);
        stringClassHashMap.put("detail", DetailPushHandler.class);



    }

    public static void handlerPushExtraData(Map<String, String> data) {
        if (data == null) {
            return;
        }
        String templateType = data.get("template_type");
        if (templateType == null) {
            return;
        }
        IPushHandler pushAction = getPushAction(templateType);
        if (pushAction == null) {
            return;
        }
        try {
            pushAction.handlerData(GsonHelper.getGsonInstance().fromJson(GsonHelper.getGsonInstance().toJson(data), PushDataBean.class));
        } catch (Exception ee) {
            Logger.d(ee.getMessage());
        }


    }

    private static IPushHandler getPushAction(String key) {
        IPushHandler iPushHandler = iPushHandlerMap.get(key);
        if (iPushHandler != null) {
            return iPushHandler;
        }
        Class<? extends IPushHandler> aClass = stringClassHashMap.get(key);
        if (aClass == null) {
            return null;
        }
        try {
            IPushHandler newAction = aClass.newInstance();
            iPushHandlerMap.put(key, newAction);
            return newAction;
        } catch (Exception e) {
            return null;
        }
    }


}
