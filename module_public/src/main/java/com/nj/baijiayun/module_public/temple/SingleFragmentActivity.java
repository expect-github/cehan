package com.nj.baijiayun.module_public.temple;

import android.os.Bundle;

import com.nj.baijiayun.module_common.base.BaseAppFragmentActivity;

/**
 * @author chengang
 * @date 2020-03-11
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_public.temple
 * @describe
 */
public abstract class SingleFragmentActivity extends BaseAppFragmentActivity {

    @Override
    public boolean needAutoInject() {
        return false;
    }

    @Override
    public boolean needMultipleStatus() {
        return false;
    }
    
    @Override
    protected void registerListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setPageTitle(setPageTitle());
    }

    protected abstract int setPageTitle();
}
