package com.nj.baijiayun.module_public.bean.response;

import com.nj.baijiayun.module_common.base.BaseResponse;

/**
 * @author chengang
 * @date 2019-12-16
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.bean.response
 * @describe
 */
public class PublicContractResponse extends BaseResponse<String> {
    @Override
    public String getData() {
        return super.getData() == null ? "" : super.getData();
    }
}
