package com.nj.baijiayun.module_public.helper.push.handler;

import com.nj.baijiayun.module_public.helper.JumpHelper;
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
public class DetailPushHandler implements IPushHandler {
    @Override
    public void handlerData(PushDataBean dataBean) {
        if (dataBean.isToBook()) {
            JumpHelper.jumpBookDetail(dataBean.getIntId());
        } else if (dataBean.isToCourse()) {
            JumpHelper.jumpCourseDetail(dataBean.getIntId());
        } else if (dataBean.isToNews()) {
            JumpHelper.jumpNewsDetail(dataBean.getIntId());
        }
    }
}
