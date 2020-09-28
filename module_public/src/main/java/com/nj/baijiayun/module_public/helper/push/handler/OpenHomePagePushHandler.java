package com.nj.baijiayun.module_public.helper.push.handler;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.push.IPushHandler;
import com.nj.baijiayun.module_public.helper.push.PushDataBean;

/**
 * @author chengang
 * @date 2019-12-28
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.push.handler
 * @describe
 */
public class OpenHomePagePushHandler implements IPushHandler {
    @Override
    public void handlerData(PushDataBean dataBean) {
        ARouter.getInstance().build(RouterConstant.PAGE_MAIN).navigation();
    }
}
