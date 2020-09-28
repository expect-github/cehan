package com.nj.baijiayun.module_main.ui;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.helper.GsonHelper;
import com.nj.baijiayun.module_common.helper.MaskViewHelper;
import com.nj.baijiayun.module_main.adapter.holder.UserItemHolder;
import com.nj.baijiayun.module_main.adapter.holder.UserItemNewHolder;
import com.nj.baijiayun.module_main.bean.UserItemBean;
import com.nj.baijiayun.module_main.bean.UserItemListBean;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.bean.AppConfigBean;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;
import com.nj.baijiayun.module_public.helper.config.SpManger;
import com.nj.baijiayun.processor.Module_mainAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeRvAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Kai
 * @date 2020/8/8
 * @time 9:39 护眼模式
 */
@Route(path = RouterConstant.PAGE_PUBLIC_EYECARE)
public class EyeCareActivity extends BaseAppActivity {
    RecyclerView mRv;
    private BaseMultipleTypeRvAdapter userAdapter;

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_activity_eye_care;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setPageTitle(R.string.public_activity_title_eye);
        mRv = findViewById(R.id.eye_rv);
        setmRv();

    }

    @Override
    protected void registerListener() {

    }


    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public boolean needAutoInject() {
        return false;

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkUiVisible();
    }

    private void setmRv() {
        mRv.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        userAdapter = Module_mainAdapterHelper.getUser_newAdapter(getActivity());
        userAdapter.setTag(UserItemHolder.TAG_NEED_GO_APPLY, null);
        mRv.setAdapter(userAdapter);
        UserItemListBean item = GsonHelper.getGsonInstance().fromJson(getJson(), UserItemListBean.class);
        userAdapter.addAll(item.getUserItemList());

    }



    private void checkUiVisible() {
        if (AccountHelper.getInstance().getInfo() == null) {
            clearAdapterApplyInfoTag();
        } else {
        }

    }

    private void clearAdapterApplyInfoTag() {
        if (userAdapter != null) {
            /**
             * userAdapter.setTag(UserItemNewHolder.TAG_NEED_GO_APPLY, null);以前的
             * 下面是修改后的2020/8/7
             */
            userAdapter.setTag(UserItemHolder.TAG_NEED_GO_APPLY, null);
        }

    }

    /**
     * 旧的
     * @return
     */
    private String getJson() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assets = getBaseContext().getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assets.open("userInfoEye.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
