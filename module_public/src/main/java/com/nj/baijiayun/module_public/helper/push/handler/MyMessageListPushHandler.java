package com.nj.baijiayun.module_public.helper.push.handler;

import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.helper.JumpHelper;
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
public class MyMessageListPushHandler implements IPushHandler {
    @Override
    public void handlerData(PushDataBean dataBean) {
        JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getMsg());
    }
}
