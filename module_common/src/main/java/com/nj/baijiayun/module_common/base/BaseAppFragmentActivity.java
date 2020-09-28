package com.nj.baijiayun.module_common.base;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.nj.baijiayun.module_common.R;
import com.nj.baijiayun.module_common.mvp.BasePresenter;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * @author chengang
 * @date 2019-06-10
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_common.base
 * @describe
 */
public abstract class BaseAppFragmentActivity<T extends BasePresenter> extends BaseAppActivity<T>{

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.common_layout_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        loadRootFragment(R.id.frameLayout, (ISupportFragment) createFragment());
    }

    protected abstract BaseAppFragment createFragment();

    public FrameLayout getFrameLayout()
    {
        return findViewById(R.id.frameLayout);
    }

}
