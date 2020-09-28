package com.nj.baijiayun.module_public.helper.push.handler;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.push.IPushHandler;
import com.nj.baijiayun.module_public.helper.push.PushDataBean;

import androidx.annotation.Keep;

/**
 * @author chengang
 * @date 2019-12-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.push.handler
 * @describe
 */
@Keep
public class LiveOpenPushHandler implements IPushHandler {
    @Override
    public void handlerData(PushDataBean dataBean) {
        ARouter.getInstance()
                .build(RouterConstant.PAGE_COURSE_MY_LEARNED_DETAIL)
                .withInt("courseType", dataBean.getShopType())
                .withInt("courseId", Integer.parseInt(dataBean.getId()))
                .navigation();
    }
}
