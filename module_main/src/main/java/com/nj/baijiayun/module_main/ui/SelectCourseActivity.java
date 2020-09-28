package com.nj.baijiayun.module_main.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_common.base.BaseAppFragmentActivity;
import com.nj.baijiayun.module_common.base.BaseEmptyPresenter;
import com.nj.baijiayun.module_common.helper.FragmentCreateHelper;
import com.nj.baijiayun.module_common.helper.ToolBarHelper;
import com.nj.baijiayun.module_main.R;
import com.nj.baijiayun.module_main.fragments.SelectCourseFragment;
import com.nj.baijiayun.module_public.consts.RouterConstant;

/**
 * @author chengang
 * @date 2020-02-20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_main.ui
 * @describe
 */
@Route(path = RouterConstant.PAGE_SELECT_COURSE)
public class SelectCourseActivity extends BaseAppFragmentActivity<BaseEmptyPresenter> {

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setPageTitle(getString(R.string.main_course_list));
//        getActionBar().se
        ToolBarHelper.addRightImageView(getToolBar(), R.drawable.public_ic_search, v -> ARouter.getInstance().build(RouterConstant.PAGE_COURSE_SEARCH).navigation());
    }

    @Override
    protected BaseAppFragment createFragment() {
        Bundle bundle = new Bundle();
        if (getIntent() != null) {
            bundle.putInt("courseType", getIntent().getIntExtra("courseType", 0));
        }
        bundle.putBoolean("needAppBar", false);
        return FragmentCreateHelper.newInstance(bundle, SelectCourseFragment.class);
    }

    @Override
    protected void registerListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
