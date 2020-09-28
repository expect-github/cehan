package com.nj.baijiayun.module_common.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nj.baijiayun.basic.ui.BaseActivity;
import com.nj.baijiayun.basic.utils.DensityUtil;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.basic.utils.UiStatusBarUtil;
import com.nj.baijiayun.basic.widget.MultipleStatusView;
import com.nj.baijiayun.logger.log.Logger;
import com.nj.baijiayun.module_common.R;
import com.nj.baijiayun.module_common.helper.BJYDialogHelper;
import com.nj.baijiayun.module_common.helper.ToolBarHelper;
import com.nj.baijiayun.module_common.mvp.BasePresenter;
import com.nj.baijiayun.module_common.mvp.MultiStateView;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * @author chengang
 * @date 2019-06-03
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.basic.demo.base
 * @describe
 */
public abstract class BaseAppActivity<T extends BasePresenter> extends BaseActivity implements MultiStateView, HasSupportFragmentInjector {
    public TextView mTitleTextView;
    private Dialog mDiaLog;
    public MultipleStatusView mStatusView;
    @Inject
    public T mPresenter;
    private ImageView mBackIv;
    private TextView mBackTv;
    private View mBackParentView;

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    /**
     * 内容view layoutId
     *
     * @return int
     */
    protected abstract int bindContentViewLayoutId();


    @Override
    protected void onResume() {
        super.onResume();
        Logger.i("onResume");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        PushAgent.getInstance(this).onAppStart();
        initInject();
//        try {
//            initInject();
//        } catch (Exception ee) {
//            Log.e("AndroidSupportInjection", "Please Check Your Presenter is Inject!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            ee.printStackTrace();
//        }

        super.onCreate(savedInstanceState);
        Logger.i("onCreate");


    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.i("onStart");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.i("onPause");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.i("onRestart");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.i("onStop");

    }


    public void initInject() {
        if (needAutoInject()) {
            AndroidInjection.inject(this);
        }
    }


    @Override
    protected void initSomethingFirstAfterContentView() {
        initToolBar();
        if (needStatusTextLight()) {
            UiStatusBarUtil.statusBarLightMode(this);
        }
        if (needMultipleStatus()) {
            mStatusView = findViewById(R.id.multiple_status_view);
            mStatusView.setContentViewResId(bindContentViewLayoutId());
        }
        takeView();

    }

    public void takeView() {
        if (getPresenter() != null) {
            getPresenter().takeView(this);
        }
    }


    @Override
    protected int bindLayoutRes() {
        if (needMultipleStatus()) {
            return R.layout.layout_multstatus;
        } else {
            return bindContentViewLayoutId();

        }
    }

    public boolean needStatusTextLight() {
        return true;
    }

    public boolean needMultipleStatus() {
        return true;
    }

    /**
     * 需要注入的一定要注入一个Presenter
     *
     * @return 默认注入
     */
    public boolean needAutoInject() {
        return true;
    }


    public void hideToolBar() {
        //不需要状态栏的情况下一半
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    public void setToolBarVisible(boolean visible) {
        //不需要状态栏的情况下一半
        if (visible) {
            Objects.requireNonNull(getSupportActionBar()).show();
        } else {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }
    }

    public void hideBackBtn() {
        if (getSupportActionBar() == null) {
            return;
        }
        //设置返回
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //是否显示
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        if (mBackParentView != null) {
            mBackParentView.setVisibility(View.GONE);
        }
    }

    public void setPageTitle(String title) {

        if (getSupportActionBar() == null) {
            return;
        }
        if (mTitleTextView != null) {
            mTitleTextView.setText(title);
        }
    }

    public void setPageTitle(int titleRes) {
        setPageTitle(getString(titleRes));
    }


    public Toolbar getToolBar() {
        return findViewById(R.id.action_bar);
    }

    public void initToolBar() {
        if (getSupportActionBar() == null) {
            return;
        }
        Toolbar toolbar = getToolBar();
        getSupportActionBar().setElevation(DensityUtil.dip2px(1));
        if (toolbar == null) {
            return;
        }
        
        toolbar.setNavigationIcon(R.drawable.common_back_icon);

        ToolBarHelper.setToolBarWhite(this, getSupportActionBar(), toolbar);
        //设置返回不显示
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //是否显示
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        mBackParentView = ToolBarHelper.addBack(getToolBar(), v -> onBackPressedSupport());
        mTitleTextView = ToolBarHelper.setToolBarTextCenter(toolbar);
        mBackTv = mBackParentView.findViewById(R.id.tv_back);
        mBackIv = mBackParentView.findViewById(R.id.iv_back);
//        mTitleTextView.setSelected(true);
 
        toolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }


    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    @Override
    public void showLoadView() {
        if (mStatusView != null) {
            mStatusView.setVisibility(View.VISIBLE);
            mStatusView.showLoading();

        }
    }

    @Override
    public void showNoNetWorkView() {
        if (mStatusView != null) {
            mStatusView.showNoNetwork();
        }
    }

    @Override
    public void showNoDataView() {
        if (mStatusView != null) {
            mStatusView.showEmpty();
        }

    }

    @Override
    public void showErrorDataView() {
        if (mStatusView != null) {
            mStatusView.showError();
        }
    }

    @Override
    public void showNoLogin() {
        if (mStatusView != null) {
            mStatusView.showNoLogin();
        }
    }

    @Override
    public void showToastMsg(String msg) {
        ToastUtil.shortShow(this, msg);
    }

    @Override
    public void showToastMsg(int strIds) {
        ToastUtil.shortShow(this, getString(strIds));
    }

    @Override
    public void showLoadV(String msg) {
        mDiaLog = BJYDialogHelper.buildLoadingDialog(this).setLoadingTxt(msg);

        mDiaLog.show();
    }

    @Override
    public void showLoadV() {
        showLoadV("");
    }

    @Override
    public void closeLoadV() {
        if (mDiaLog != null) {
            mDiaLog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dropView();
        Logger.i("onDestroy");
    }

    public void dropView() {
        if (getPresenter() != null) {
            getPresenter().dropView();
        }
    }

    @Override
    public void showContentView() {
        if (mStatusView != null) {
            mStatusView.showContent();
        }
    }

    public T getPresenter() {
        return mPresenter;
    }

    public ImageView getBackIconView() {
        return mBackIv;
    }

    public View getBackView() {
        return mBackParentView;
    }

    public void setBackText(String text) {
        mBackTv.setText(text);
    }
}
