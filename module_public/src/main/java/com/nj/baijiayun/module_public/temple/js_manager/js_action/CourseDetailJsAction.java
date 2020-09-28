package com.nj.baijiayun.module_public.temple.js_manager.js_action;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.module_common.widget.AppWebView;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.temple.JsActionDataBean;
import com.nj.baijiayun.module_public.temple.js_manager.IJsAction;

/**
 * @author chengang
 * @date 2019-08-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple.js_manager
 * @describe 课程详情 之后可以优化
 */
public class CourseDetailJsAction implements IJsAction {
    @Override
    public void handlerData(Context context, AppWebView appWebView, JsActionDataBean jsActionDataBean) {
        ARouter.getInstance().build(RouterConstant.PAGE_COURSE_DETAIL).withInt("courseId", jsActionDataBean.getParams().getId()).navigation();
    }
}
