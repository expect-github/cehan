package com.nj.baijiayun.module_public.p_set.ui;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.temple.SingleFragmentActivity;

/**
 * @author chengang
 * @date 2020-03-12
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.p_set.ui
 * @describe
 */
@Route(path = RouterConstant.PAGE_PUBLIC_SUPERVISION)
public class SupervisionActivity extends SingleFragmentActivity {
    @Override
    protected int setPageTitle() {
        return R.string.public_activity_title_supervision;
    }


    @Override
    protected BaseAppFragment createFragment() {
        return new SupervisionFragment();
    }
}
