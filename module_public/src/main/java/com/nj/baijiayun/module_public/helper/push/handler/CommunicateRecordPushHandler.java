package com.nj.baijiayun.module_public.helper.push.handler;

import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.push.IPushHandler;
import com.nj.baijiayun.module_public.helper.push.PushDataBean;

import java.text.MessageFormat;

/**
 * @author chengang
 * @date 2019-12-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.helper.push.handler
 * @describe
 */
public class CommunicateRecordPushHandler implements IPushHandler {
    @Override
    public void handlerData(PushDataBean dataBean) {
        JumpHelper.jumpWebViewNoNeedAppTitle(MessageFormat.format("communicate?id={0}&t_id={1}", dataBean.getId(), dataBean.getTeacherId()));
    }
}
