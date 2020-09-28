package com.nj.baijiayun.module_public.temple.js_manager.js_action;

import android.content.Context;

import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.temple.JsActionDataBean;
import com.nj.baijiayun.module_public.temple.js_manager.IJsAction;

/**
 * @author chengang
 * @date 2020-01-14
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.js_manager.js_action
 * @describe
 */
public class CourseBuySuccessAction implements IJsAction {
    @Override
    public void handlerData(Context context, AppWebView appWebView, JsActionDataBean jsActionDataBean) {

        try {
            LiveDataBus.get().with(LiveDataBusEvent.COURSE_HAS_BUY_SUCCESS_BY_PAY).postValue(Integer.parseInt(jsActionDataBean.getParams().getCourseId()));
        } catch (NumberFormatException e) {
            Logger.e("courseId is error!!! courseId is"+jsActionDataBean.getParams().getCourseId());
        }
    }
}
