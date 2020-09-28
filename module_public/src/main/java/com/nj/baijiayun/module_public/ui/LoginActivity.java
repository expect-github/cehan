package com.nj.baijiayun.module_public.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.basic.utils.UiStatusBarUtil;
import com.nj.baijiayun.module_common.base.BaseAppFragment;
import com.nj.baijiayun.module_common.base.BaseAppFragmentActivity;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.bean.SystemWebConfigBean;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.fragment.LoginByCodeFragment;
import com.nj.baijiayun.module_public.fragment.LoginByPwdFragment;
import com.nj.baijiayun.module_public.fragment.LoginDelegate;
import com.nj.baijiayun.module_public.helper.ConstrainsClickHelper;
import com.nj.baijiayun.module_public.helper.ViewHelper;
import com.nj.baijiayun.module_public.helper.config.ConfigLoaderManager;
import com.nj.baijiayun.module_public.helper.config.ConfigManager;
import com.nj.baijiayun.module_public.mvp.contract.LoginContract;
import com.tencent.mm.opensdk.utils.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import me.yokeyword.fragmentation.ISupportFragment;


@Route(path = RouterConstant.PAGE_LOGIN)
public class LoginActivity extends BaseAppFragmentActivity<LoginContract.Presenter> implements LoginContract.View {


    private Group mGroupQq;
    private Group mGroupWechat;
    private ImageView mAppCoverIv;
    private Button mBtnConfirm;

    private LoginDelegate loginDelegate = new LoginDelegate();
    private View mViewQqParent;
    private View mViewWechatParent;
    //    private View mViewMidLine;
    private ConstraintLayout mOauthLoginCl;
    private Group mGroupOtherLogin;
    private String tag =getClass().getSimpleName();

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        loadAppLogo();
    }

    private void loadAppLogo() {
        SystemWebConfigBean systemWebConfigBean = ConfigLoaderManager.get().getSystemConfigLoader().get();
        if (systemWebConfigBean != null) {
            Glide.with(this).load(systemWebConfigBean.getMobileLoginLogo()).into(new CustomTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    /*  w/h= sw/sh sh=sw*h/w*/
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mAppCoverIv.getLayoutParams();
                    layoutParams.height = resource.getIntrinsicHeight() * DensityUtil.getScreenWidth() / resource.getIntrinsicWidth();
                    mAppCoverIv.setLayoutParams(layoutParams);
                    mAppCoverIv.setImageDrawable(resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            });
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mOauthLoginCl = findViewById(R.id.cl_oauth_login);
        mGroupOtherLogin = findViewById(R.id.group_other_login);
        mViewQqParent = findViewById(R.id.view_qq_parent);
        mViewWechatParent = findViewById(R.id.view_wechat_parent);
//        mViewMidLine = findViewById(R.id.view_mid_line);
        mGroupQq = findViewById(R.id.group_qq);
        mGroupWechat = findViewById(R.id.group_wechat);
        mAppCoverIv = findViewById(R.id.iv_app_cover);
        mBtnConfirm = findViewById(R.id.btn_confirm);
        mBtnConfirm.setText(getActivity().getString(R.string.common_login));
        loginDelegate.initView(mBtnConfirm.getRootView());
        initUiVisible();

    }

    private void initUiVisible() {
        boolean availableLoginByQq = ConfigManager.getInstance().isAvailableLoginByQq();
        boolean availableLoginByWeChat = ConfigManager.getInstance().isAvailableLoginByWeChat();
        ViewHelper.setViewVisible(mGroupOtherLogin, availableLoginByQq || availableLoginByWeChat);
        ViewHelper.setViewVisible(mViewQqParent, availableLoginByQq);
        ViewHelper.setViewVisible(mGroupQq, availableLoginByQq);
        ViewHelper.setViewVisible(mGroupWechat, availableLoginByWeChat);
        ViewHelper.setViewVisible(mViewWechatParent, availableLoginByWeChat);
//        ViewHelper.setViewVisible(mViewMidLine, availableLoginByWeChat && availableLoginByQq);
        ViewHelper.setViewVisible(mOauthLoginCl, mGroupOtherLogin.getVisibility() == View.VISIBLE);
    }

    @Override
    public void initAppStatusBar() {
        //透明状态栏
        setTranslucentBar();
        hideToolBar();
        UiStatusBarUtil.statusBarLightMode(this);

    }


    @Override
    protected void registerListener() {
        ConstrainsClickHelper.setOnClickListener(mGroupQq, (View) mGroupQq.getParent(), v -> mPresenter.qqLogin());
        ConstrainsClickHelper.setOnClickListener(mGroupWechat, (View) mGroupQq.getParent(), v -> mPresenter.wechatLogin());
        mBtnConfirm.setOnClickListener(v -> {
            if (!checkAgreeProtocolPass()) {
                return;
            }
            ISupportFragment topFragment = (ISupportFragment) getSupportFragmentManager().findFragmentById(R.id.frameLayout);
            ;
            if (topFragment instanceof LoginByCodeFragment) {
                ((LoginByCodeFragment) topFragment).getPresenter().login();
            } else if (topFragment instanceof LoginByPwdFragment) {
                ((LoginByPwdFragment) topFragment).getPresenter().login();
            }
        });
        loginDelegate.registerListener();
    }

    @Override
    protected BaseAppFragment createFragment() {
        return new LoginByCodeFragment();
    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_activity_login;
    }


    @Override
    public boolean checkAgreeProtocolPass() {
        return loginDelegate.checkAgreePass();
    }

    @Override
    protected void onDestroy() {
        loginDelegate.release();
        super.onDestroy();
//        Log.e(tag,"onDestroy");
    }
}
