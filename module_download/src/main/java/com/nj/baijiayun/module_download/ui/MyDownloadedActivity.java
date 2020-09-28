package com.nj.baijiayun.module_download.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nj.baijiayun.module_common.base.BaseAppMultipleTabActivity;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_common.helper.FragmentCreateHelper;
import com.nj.baijiayun.module_common.helper.ToolBarHelper;
import com.nj.baijiayun.module_download.R;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;


/**
 * @project neixun_android
 * @class name：com.nj.baijiayun.module_download.activity
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019-06-20 13:57
 * @change
 * @time
 * @describe
 */
@Route(path = RouterConstant.PAGE_DOWNLOAD_MY_DOWNLOADED)
public class MyDownloadedActivity extends BaseAppMultipleTabActivity {

    private TextView editTv;
    private boolean inEdit = false;

    @Override
    public String[] addTabs() {
//        new String[]{
//                getString(R.string.download_course),
//                getString(R.string.download_library)
//        }
        return new String[]{getString(R.string.download_course)};
    }

    @Override
    public ArrayList<Fragment> addFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentCreateHelper.newInstance(Config.SHOW_TYPE_COURSE, MyDownloadedFragment.class));
//        fragments.add(FragmentCreateHelper.newInstance(Config.SHOW_TYPE_LIBRARY, MyDownloadedFragment.class));
        return fragments;
    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.download_activity_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        getTabLayout().setVisibility(View.GONE);
        setPageTitle(R.string.download_my_downloaded);
        editTv = (TextView) View.inflate(this, R.layout.download_layout_edit, null);
        ToolBarHelper.addRightView(getToolBar(), editTv);
    }

    @Override
    protected void registerListener() {
        editTv.setOnClickListener(v -> {
            changeEditStatus();
        });
    }

    private void changeEditStatus() {
        for (Object fragment : fragments) {
            if (fragment instanceof MyDownloadedFragment) {
                ((MyDownloadedFragment) fragment).setEditStatusChanged(!inEdit);
            }
        }
        inEdit = !inEdit;
        editTv.setText(inEdit ? R.string.common_cancel : R.string.common_edit);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onBackPressedSupport() {
        if (inEdit) {
            changeEditStatus();
        } else {
            super.onBackPressedSupport();
        }
    }


    @Override
    public boolean needAutoInject() {
        return false;
    }


}
