package com.nj.baijiayun.module_public.helper.share_login;

import cn.jiguang.share.android.model.AccessTokenInfo;

/**
 * Created by Administrator on 2018/4/9.
 */

public interface JShareLoginCall {
    void getJShareLogin(AccessTokenInfo s, boolean isSuccessLogin, String toastMsg);
}
