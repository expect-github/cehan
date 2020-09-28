package com.nj.baijiayun.module_assemble.ui;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nj.baijiayun.module_assemble.R;
import com.nj.baijiayun.module_common.base.BaseAppMultipleTabFragment;
import com.nj.baijiayun.module_common.helper.FragmentCreateHelper;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.ViewHelper;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/**
 * @author chengang
 * @date 2020-02-20
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_assemble.ui
 * @describe
 */
public class AssembleMultipleTabFragment extends BaseAppMultipleTabFragment {
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
    protected void initView(View mContextView) {
        super.initView(mContextView);
        mMyAssembleFl = mContextView.findViewById(R.id.frameLayout);
        mLeftTv = mContextView.findViewById(R.id.tv_left);
        mRightTv = mContextView.findViewById(R.id.tv_right);
        mLeftTv.setText(getString(R.string.assemble_shop));
        mRightTv.setText(getString(R.string.assemble_my_assemble));
        select(true);
    }


    @Override
    public ArrayList<Fragment> addFragment() {
        fragments.add(FragmentCreateHelper.newInstance(AssembleFragment.TYPE_COURSE, AssembleFragment.class));
        fragments.add(FragmentCreateHelper.newInstance(AssembleFragment.TYPE_BOOKS, AssembleFragment.class));
        return fragments;
    }

    @Override
    public void registerListener() {
        mLeftTv.setOnClickListener(v -> {
            select(true);

        });
        mRightTv.setOnClickListener(v -> {
            if (JumpHelper.checkLogin()) {
                return;
            }
            select(false);

        });
    }

    @Override
    public void processLogic() {

    }


    private void select(boolean isSelectLeft) {

        ViewHelper.setViewVisible(getVp(), isSelectLeft);
        ViewHelper.setViewVisible(getTabLayout(), isSelectLeft);
        ViewHelper.setViewVisible(mMyAssembleFl, !isSelectLeft);
        mLeftTv.setTextColor(ContextCompat.getColor(getContext(), isSelectLeft ? R.color.white : R.color.common_main_color));
        mLeftTv.setBackgroundResource(R.drawable.assemble_btn_left_radius);
        mLeftTv.setEnabled(!isSelectLeft);

        mRightTv.setTextColor(ContextCompat.getColor(getContext(), isSelectLeft ? R.color.common_main_color : R.color.white));
        mRightTv.setBackgroundResource(R.drawable.assemble_btn_right_radius);
        mRightTv.setEnabled(isSelectLeft);

        if (!isSelectLeft && isFirstShow) {
            loadRootFragment(R.id.frameLayout, new MyAssembleFragment());
            isFirstShow = false;
        }
    }

    private boolean isFirstShow = true;


}
