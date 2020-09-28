package com.nj.baijiayun.module_assemble.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nj.baijiayun.module_assemble.R;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_common.base.BaseAppFragmentActivity;
import com.nj.baijiayun.module_public.consts.RouterConstant;

/**
 * @author chengang
 * @date 2020-02-20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_assemble.ui
 * @describe
 */
@Route(path = RouterConstant.PAGE_ASSEMBLE)
public class AssembleShopNewActivity extends BaseAppFragmentActivity {


    @Override
    public boolean needAutoInject() {
        return false;
    }
    @Override
    protected BaseAppFragment createFragment() {
        return new AssembleMultipleTabFragment();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setPageTitle(R.string.assemble_activity_assemble_list);
    }

    @Override
    protected void registerListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
