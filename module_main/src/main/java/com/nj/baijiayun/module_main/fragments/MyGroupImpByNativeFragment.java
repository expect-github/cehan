package com.nj.baijiayun.module_main.fragments;

import android.view.View;

import com.nj.baijiayun.module_assemble.ui.AssembleMultipleTabFragment;
import com.nj.baijiayun.module_common.helper.ToolBarHelper;
import com.nj.baijiayun.module_main.R;

/**
 * @author chengang
 * @date 2020-02-20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.fragments
 * @describe
 */
public class MyGroupImpByNativeFragment extends AssembleMultipleTabFragment {
    @Override
    protected void initView(View mContextView) {
        super.initView(mContextView);
        ToolBarHelper.setToolBarTextCenter(mContextView.findViewById(R.id.toolbar), mContextView.getContext().getString(R.string.assemble_activity_assemble_list));
    }

    @Override
    protected int bindLayout() {
        return R.layout.main_fragment_with_toolbar;
    }
}
