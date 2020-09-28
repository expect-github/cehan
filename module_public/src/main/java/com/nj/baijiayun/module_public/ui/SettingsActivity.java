package com.nj.baijiayun.module_public.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.nj.baijiayun.basic.utils.ApplicationUtils;
import com.nj.baijiayun.basic.utils.CleanDataUtils;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.basic.widget.BadgeView;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_public.R;
import com.nj.baijiayun.module_public.consts.ConstsH5Url;
import com.nj.baijiayun.module_public.consts.ConstsProtocol;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.AccountHelper;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.ViewHelper;
import com.nj.baijiayun.module_public.helper.update.UpdateHelper;
import com.nj.baijiayun.module_public.widget.UserItemView;

import androidx.core.content.ContextCompat;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 这个页面的列表待有时间重构，不需要这么麻烦
 */
@Route(path = RouterConstant.PAGE_PUBLIC_SETTINGS)
public class SettingsActivity extends BaseAppActivity {


    private UserItemView mItemViewAccountSafe;
    private UserItemView mItemViewCurrentVersion;
    private UserItemView mItemViewClearCache;
    private UserItemView mItemViewFeedBack;
    private Button mBtnExitLogin;
    private UserItemView mItemViewRegisterProtocol;
    private UserItemView mItemViewProtectProtocol;
    private UserItemView mItemViewLogOff;
    private UserItemView mItemViewCertify;
    private UserItemView mItemViewSupervision;
    private UserItemView mItemViewAbout;
    private ImageView mCoverIv;
    private TextView mContentTv;


    @Override
    public boolean needAutoInject() {
        return false;


    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setPageTitle(R.string.public_activity_title_settings);
        mItemViewCertify = findViewById(R.id.item_view_certifie);
        mItemViewSupervision = findViewById(R.id.item_view_supervision);
        mItemViewAbout = findViewById(R.id.item_view_about);
        mItemViewAccountSafe = findViewById(R.id.item_view_account_safe);
        mItemViewCurrentVersion = findViewById(R.id.item_view_current_version);
        mItemViewClearCache = findViewById(R.id.item_view_clear_cache);
        mItemViewFeedBack = findViewById(R.id.item_view_feedback);
        mBtnExitLogin = findViewById(R.id.btn_exit_login);
        mItemViewRegisterProtocol = findViewById(R.id.item_view_register_protocol);
        mItemViewProtectProtocol = findViewById(R.id.item_view_protect_protocol);
        mItemViewLogOff = findViewById(R.id.item_view_logoff);
        mItemViewRegisterProtocol.setUrl(ConstsH5Url.getProtocol(ConstsProtocol.REGISTER));
        mItemViewProtectProtocol.setUrl(ConstsH5Url.getProtocol(ConstsProtocol.PROTECT));
        mItemViewFeedBack.setUrl(ConstsH5Url.getFeedback());
        mItemViewAbout.setUrl(ConstsH5Url.getAboutUrl());
        mItemViewCertify.setRoutePath(RouterConstant.PAGE_PUBLIC_CERTIFY);
        mItemViewSupervision.setUrl(ConstsH5Url.getUrl("options/parent-control"));
        View inflate = LayoutInflater.from(this).inflate(R.layout.p_set_layout_certified, mItemViewCertify, false);
        mItemViewCertify.addRightContentView(inflate);
        mCoverIv = inflate.findViewById(R.id.iv_cover);
        mContentTv = inflate.findViewById(R.id.tv_content);
    }


    private void initCertifyView() {
        boolean isCertify = AccountHelper.getInstance().getInfo() != null && AccountHelper.getInstance().getInfo().isCertify();
        ViewHelper.setViewVisible(mCoverIv, isCertify);
        mContentTv.setText(isCertify ? R.string.public_has_certified : R.string.public_go_certified);
        mContentTv.setTextColor(ContextCompat.getColor(this, isCertify ? R.color.common_item_right_content : R.color.common_main_color));
    }

    @Override
    protected void registerListener() {
        mItemViewAccountSafe.setOnItemClickListener((path, v) -> {
            ARouter.getInstance().build(RouterConstant.PAGE_PUBLIC_SET_PWD).navigation();
        });
        mItemViewCurrentVersion.setOnItemClickListener((path, v) -> {
            startActivity(new Intent(getActivity(), AboutActivity.class));

        });
        mItemViewFeedBack.setOnItemClickListener(((path, v) -> {
            JumpHelper.jumpWebViewNoNeedAppTitle(ConstsH5Url.getFeedback());
        }));
        mItemViewClearCache.setOnItemClickListener((path, v) -> {
            clearCache();
        });
        mBtnExitLogin.setOnClickListener(v -> {
            AccountHelper.getInstance().logoutOnlyRemoveInfo();
            finish();
        });
        mItemViewLogOff.setOnItemClickListener((path, v) -> {
            ARouter.getInstance().build(RouterConstant.PAGE_PUBLIC_LOGOFF).navigation();
        });

    }

    private void clearCache() {
        Observable.create(emitter -> {
            CleanDataUtils.clearAllCache(getActivity());
            emitter.onNext(emitter);
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        ToastUtil.shortShow(getActivity(), "已清除缓存");
                        mItemViewClearCache.setContent("");

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        try {

            if (AccountHelper.getInstance().getInfo() != null) {
                mItemViewAccountSafe.setContent(AccountHelper.getInstance().getInfo().isSetPwd() ? "您尚未设置密码" : "");
            }
            //空格给红点占位
            mItemViewCurrentVersion.setContent("   V" + ApplicationUtils.getVersionName(this));
            mItemViewClearCache.setContent(CleanDataUtils.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (UpdateHelper.isNeedUpgrade()) {
            BadgeView mBadgeView = new BadgeView(SettingsActivity.this);
            mBadgeView.setTargetView(mItemViewCurrentVersion.getContentTv());
            mBadgeView.showRedPoint(true);
            mBadgeView.setBadgeGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        }
        initCertifyView();


    }

    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.public_activity_settings;
    }
}
