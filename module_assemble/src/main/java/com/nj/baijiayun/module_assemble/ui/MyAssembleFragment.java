package com.nj.baijiayun.module_assemble.ui;

import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.temple.BaseAppWebViewFragment;

/**
 * @author chengang
 * @date 2019-11-20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_assemble.ui
 * @describe
 */
public class MyAssembleFragment extends BaseAppWebViewFragment {
    private int count = 0;

    @Override
    public String configUrl() {
        return ConstsH5Url.getUrl(ConstsH5Url.getMyAssemble());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (count > 0) {
            reloadUrl();
        }
        count++;
    }
}
