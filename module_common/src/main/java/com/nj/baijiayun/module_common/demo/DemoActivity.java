package com.nj.baijiayun.module_common.demo;

import android.os.Bundle;

import com.nj.baijiayun.module_common.R;
import com.nj.baijiayun.module_common.base.BaseAppActivity;

//@Route(path = RouterConstant.PAGE_DEMO)
public class DemoActivity extends BaseAppActivity<DemoContract.Presenter> {
//
//    @Inject
//    DemoListFragment demoListFragment;

    @Override
    protected int bindContentViewLayoutId() {
        return  R.layout.common_activity_demo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPresenter.test();
    }

    @Override
    public boolean needMultipleStatus() {
        return true;
    }

    @Override
    protected void registerListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

}
