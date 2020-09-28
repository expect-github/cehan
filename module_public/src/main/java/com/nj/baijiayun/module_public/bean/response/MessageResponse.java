package com.nj.baijiayun.module_public.bean.response;

import com.nj.baijiayun.module_common.base.BaseListResponse;
import com.nj.baijiayun.module_public.bean.PublicMessageReadBean;

import java.util.List;

/**
 * @author chengang
 * @date 2019-08-16
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean.response
 * @describe
 */
public class MessageResponse extends BaseListResponse<PublicMessageReadBean> {

    public int getUnReadCount() {
        List<PublicMessageReadBean> data = getData();
        if (data == null || data.size() == 0) {
            return 0;
        }
        int result = 0;
        for (int i = 0; i < data.size(); i++) {
            result += data.get(i).getNotReadCount();
        }
        return result;
    }
}
