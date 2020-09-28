package com.nj.baijiayun.module_distribution.ui.list;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_common.base.BaseAppFragmentActivity;
import com.nj.baijiayun.module_distribution.R;
import com.nj.baijiayun.module_public.consts.RouterConstant;

/**
 * @author chengang
 * @date 2020-02-27
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_distribution
 * @describe
 */
@Route(path = RouterConstant.PAGE_DISTRIBUTION_LIST)
public class DistributionListActivity extends BaseAppFragmentActivity
{
    @Override
    public boolean needAutoInject() {
        return false;
    }

    @Override
    protected BaseAppFragment createFragment() {
        return new DistributionSimpleListFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setPageTitle(R.string.distribution_activity_title);
    }

    @Override
    protected void registerListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
