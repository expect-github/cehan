package com.nj.baijiayun.module_assemble.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nj.baijiayun.module_assemble.R;
import com.nj.baijiayun.module_common.base.BaseAppMultipleTabActivity;
import com.nj.baijiayun.module_common.helper.FragmentCreateHelper;
import com.nj.baijiayun.module_public.helper.JumpHelper;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

/**
 * @author chengang
 * @date 2019-11-13
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_assemble
 * @describe
 */
//@Route(path = RouterConstant.PAGE_ASSEMBLE)
@Deprecated
public class AssembleShopActivity extends BaseAppMultipleTabActivity {

    private FrameLayout mMyAssembleFl;
    private TextView mLeftTv;
    private TextView mRightTv;

    @Override
    public boolean needAutoInject() {
        return false;
    }

    @Override
    public String[] addTabs() {
        return new String[]{getString(R.string.assemble_course), getString(R.string.assemble_book)};
    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.assemble_activity_assemble;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mMyAssembleFl = findViewById(R.id.frameLayout);
        needTopLine();
        setPageTitle(R.string.assemble_activity_assemble_list);
        mLeftTv = findViewById(R.id.tv_left);
        mRightTv = findViewById(R.id.tv_right);
        mLeftTv.setText(getString(R.string.assemble_shop));
        mRightTv.setText(getString(R.string.assemble_my_assemble));
        checkSelect();

    }

    private void checkSelect() {
        boolean needSelectMyAssemble = getIntent().getBooleanExtra("needSelectMyAssemble", false);
        if (needSelectMyAssemble) {
            rightSelect();
        } else {
            leftSelect();
        }

    }

    @Override
    public ArrayList<Fragment> addFragment() {
        fragments.add(FragmentCreateHelper.newInstance(AssembleFragment.TYPE_COURSE, AssembleFragment.class));
        fragments.add(FragmentCreateHelper.newInstance(AssembleFragment.TYPE_BOOKS, AssembleFragment.class));
        return fragments;
    }

    @Override
    protected void registerListener() {
        mLeftTv.setOnClickListener(v -> {
            leftSelect();

        });
        mRightTv.setOnClickListener(v -> {
            if(JumpHelper.checkLogin()) {
               return;
            }
            rightSelect();

        });
    }

    private void leftSelect() {
        mLeftTv.setEnabled(false);
        mRightTv.setEnabled(true);
        getVp().setVisibility(View.VISIBLE);
        getTabLayout().setVisibility(View.VISIBLE);
        mMyAssembleFl.setVisibility(View.GONE);

    }

    private void rightSelect() {
        mLeftTv.setEnabled(true);
        mRightTv.setEnabled(false);
        getVp().setVisibility(View.GONE);
        getTabLayout().setVisibility(View.GONE);
        mMyAssembleFl.setVisibility(View.VISIBLE);
        if (isFirstShow) {
            loadRootFragment(R.id.frameLayout, new MyAssembleFragment());
            isFirstShow = false;
        }
    }

    private boolean isFirstShow = true;

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
