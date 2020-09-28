package com.nj.baijiayun.module_public.helper.push.handler;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.LiveDataBus;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.consts.ConstsHomeTab;
import com.nj.baijiayun.module_public.event.LiveDataBusEvent;
import com.nj.baijiayun.module_public.helper.push.IPushHandler;
import com.nj.baijiayun.module_public.helper.push.PushDataBean;

/**
 * @author chengang
 * @date 2019-12-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.push.handler
 * @describe
 */
public class MyAppointCoursePushHandler implements IPushHandler {
    @Override
    public void handlerData(PushDataBean dataBean) {
        //我的约课在tab2
        LiveDataBus.get().with(LiveDataBusEvent.MAIN_TAB_SWITCH).postValue(ConstsHomeTab.TAB_APPOINT_COURSE);
        ARouter.getInstance().build(RouterConstant.PAGE_MAIN).navigation();
    }
}
