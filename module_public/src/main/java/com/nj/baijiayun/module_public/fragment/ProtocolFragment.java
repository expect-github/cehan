package com.nj.baijiayun.module_public.fragment;

import android.os.Bundle;

import com.nj.baijiayun.module_common.helper.FragmentCreateHelper;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.temple.BaseAppWebViewFragment;

/**
 * @author chengang
 * @date 2020-01-06
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.fragment
 * @describe
 */
public class ProtocolFragment extends BaseAppWebViewFragment {
    private String protocol;

    public static ProtocolFragment newInstance(String protocol) {
        Bundle bundle = new Bundle();
        bundle.putString("protocol", protocol);
        return FragmentCreateHelper.newInstance(bundle, ProtocolFragment.class);
    }

    @Override
    public void initParms(Bundle params) {
        super.initParms(params);
        protocol = params.getString("protocol");
    }

    @Override
    public String configUrl() {
        return ConstsH5Url.getProtocol(protocol);
    }
}
